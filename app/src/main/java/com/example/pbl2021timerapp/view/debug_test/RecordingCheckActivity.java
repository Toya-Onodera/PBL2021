package com.example.pbl2021timerapp.view.debug_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import com.example.pbl2021timerapp.R;
import com.example.pbl2021timerapp.recognizer.RecognizerUtil;

import java.util.ArrayList;

public class RecordingCheckActivity extends AppCompatActivity {
    private SpeechRecognizer speechRecognizer;
    private SpeechRecognizer mRecognizer;
    private TextView recordingCheckTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_check);

        recordingCheckTextView = findViewById(R.id.recordingCheckTextView);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        speechRecognizer.setRecognitionListener(mRecognitionListener);
        startRecognition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        }

        @Override
        public void onResults(Bundle results) {
            String str = "";
            ArrayList<String> values = results.getStringArrayList(
                    SpeechRecognizer.RESULTS_RECOGNITION);
            for (String val : values) {
                Log.d("TAG", val);
                str += val;

            }
            recordingCheckTextView.setText(str);

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