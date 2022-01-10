package com.example.pbl2021timerapp.recognizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import com.example.pbl2021timerapp.RecognizerUtil;

import java.util.List;

public class SpeechRecognizerManager {
    private Context context;
    private SpeechRecognizer speechRecognizer;
    private TextView textView;

    public SpeechRecognizerManager(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(mRecognitionListener);
    }

    public void start() {
        // 日本語で音声入力
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        String lang = "ja_JP.utf8";
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, lang);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        speechRecognizer.startListening(intent);
    }

    public void destory() {
        speechRecognizer.destroy();
    }

    public void changeText(String str) {
        textView.setText(str);
    }

    private RecognitionListener mRecognitionListener = new RecognitionListener() {
        /**
         * Called when the endpointer is ready for the user to start speaking.
         *
         * @param params parameters set by the recognition service. Reserved for future use.
         */
        @Override
        public void onReadyForSpeech(Bundle params) {
            changeText("声を発してください。");
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
         * @param error code is defined in {@link android.speech.SpeechRecognizer}. Implementations need to handle any
         *              integer error constant to be passed here beyond constants prefixed with ERROR_.
         */
        @Override
        public void onError(int error) {
            Log.e("TAG", "onError: " + RecognizerUtil.getErrorMessage(error));

            if (error == android.speech.SpeechRecognizer.ERROR_NO_MATCH) {
                start();
            }
        }

        @Override
        public void onResults(Bundle results) {
            List<String> recData = results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);

            String getData = "";
            for (String s : recData) {
                getData += s;
            }

            Log.v("onResults", getData);
            changeText(getData);

            if (getData.equals("こんにちは")) {
                ((Activity) context).finish();
            }
        }

        /**
         * Called when partial recognition results are available. The callback might be called at any
         * time between {@link #onBeginningOfSpeech()} and {@link #onResults(Bundle)} when partial
         * results are ready. This method may be called zero, one or multiple times for each call to
         * {@link android.speech.SpeechRecognizer#startListening(Intent)}, depending on the speech recognition
         * service implementation.  To request partial results, use
         * {@link RecognizerIntent#EXTRA_PARTIAL_RESULTS}
         *
         * @param partialResults the returned results. To retrieve the results in
         *                       ArrayList&lt;String&gt; format use {@link Bundle#getStringArrayList(String)} with
         *                       {@link android.speech.SpeechRecognizer#RESULTS_RECOGNITION} as a parameter
         */
        @Override
        public void onPartialResults(Bundle partialResults) {
            List<String> recData = partialResults.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION);

            String getData = "";
            for (String s : recData) {
                getData += s;
            }

            Log.v("onPartialResults", getData);
            changeText(getData);

            if (getData.equals("こんにちは")) {
                ((Activity) context).finish();
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
