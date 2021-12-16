package com.example.pbl2021timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GoTimerActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button timerStopButton;

    private SpeechRecognizer speechRecognizer;
    private SpeechRecognizer mRecognizer;

    private TextView speechPreviewText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_timer);

        timerStopButton = findViewById(R.id.timetStopTextButton);
        speechPreviewText = findViewById(R.id.speechPreviewText);

        // MediaPlayer のインスタンス生成
        mediaPlayer = new MediaPlayer();

        try {
            AssetFileDescriptor afdescripter = getAssets().openFd("01.mp3");

            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());

            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);

            // 音声再生準備と再生
            mediaPlayer.prepare();


        } catch (IOException e1) {
            e1.printStackTrace();
        }

        timerStopButton.setOnClickListener(v -> {
            finish();
        });

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        speechRecognizer.setRecognitionListener(mRecognitionListener);
        startRecognition();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        speechRecognizer.destroy();
    }

    private void startRecognition() {
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(mRecognitionListener);

        // 英語で音声入力
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        String lang = "ja_JP.utf8";
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        mRecognizer.startListening(intent);
    }

    private RecognitionListener mRecognitionListener = new RecognitionListener() {
        /**
         * Called when the endpointer is ready for the user to start speaking.
         *
         * @param params parameters set by the recognition service. Reserved for future use.
         */
        @Override
        public void onReadyForSpeech(Bundle params) {
            speechPreviewText.setText("声を発してください。");
            mediaPlayer.start();
        }

        /**
         * The user has started to speak.
         */
        @Override
        public void onBeginningOfSpeech() {

        }

        /**
         * The sound level in the audio stream has changed. There is no guarantee that this method will
         * be called.
         *
         * @param rmsdB the new RMS dB value
         */
        @Override
        public void onRmsChanged(float rmsdB) {

        }

        /**
         * More sound has been received. The purpose of this function is to allow giving feedback to the
         * user regarding the captured audio. There is no guarantee that this method will be called.
         *
         * @param buffer a buffer containing a sequence of big-endian 16-bit integers representing a
         *               single channel audio stream. The sample rate is implementation dependent.
         */
        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        /**
         * Called after the user stops speaking.
         */
        @Override
        public void onEndOfSpeech() {

        }

        /**
         * A network or recognition error occurred.
         *
         * @param error code is defined in {@link SpeechRecognizer}. Implementations need to handle any
         *              integer error constant to be passed here beyond constants prefixed with ERROR_.
         */
        @Override
        public void onError(int error) {
            Log.e("TAG", "onError: " + RecognizerUtil.getErrorMessage(error));

            if (error == SpeechRecognizer.ERROR_NO_MATCH) {
                startRecognition();
            }
        }

        @Override
        public void onResults(Bundle results) {

            //
            List<String> recData = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            String getData = "";
            for (String s : recData) {
                getData += s ;
            }

            speechPreviewText.setText(getData);
            Log.v("onResults",getData);

            if(getData.equals("こんにちは")) {
                finish();
            }
        }

        /**
         * Called when partial recognition results are available. The callback might be called at any
         * time between {@link #onBeginningOfSpeech()} and {@link #onResults(Bundle)} when partial
         * results are ready. This method may be called zero, one or multiple times for each call to
         * {@link SpeechRecognizer#startListening(Intent)}, depending on the speech recognition
         * service implementation.  To request partial results, use
         * {@link RecognizerIntent#EXTRA_PARTIAL_RESULTS}
         *
         * @param partialResults the returned results. To retrieve the results in
         *                       ArrayList&lt;String&gt; format use {@link Bundle#getStringArrayList(String)} with
         *                       {@link SpeechRecognizer#RESULTS_RECOGNITION} as a parameter
         */
        @Override
        public void onPartialResults(Bundle partialResults) {

            List<String> recData = partialResults
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            String getData = "";
            for (String s : recData) {
                getData += s ;
            }

            speechPreviewText.setText(getData);
            Log.v("onPartialResults",getData);

            if(getData.equals("こんにちは")) {
                finish();
            }

        }

        /**
         * Reserved for adding future events.
         *
         * @param eventType the type of the occurred event
         * @param params    a Bundle containing the passed parameters
         */
        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };
}

