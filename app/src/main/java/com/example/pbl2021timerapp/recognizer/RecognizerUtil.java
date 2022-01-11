package com.example.pbl2021timerapp.recognizer;

import android.speech.SpeechRecognizer;

public class RecognizerUtil {
    public static String getErrorMessage(int errorCode) {
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                return "Audio recording error";
            case SpeechRecognizer.ERROR_CLIENT:
                return "Other client side errors";
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                return "Insufficient permissions";
            case SpeechRecognizer.ERROR_NETWORK:
                return "Network related errors";
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                return "Network operation timed out";
            case SpeechRecognizer.ERROR_NO_MATCH:
                return "No recognition result matched";
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                return "RecognitionService busy";
            case SpeechRecognizer.ERROR_SERVER:
                return "Server sends error status";
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                return "No speech input";
        }
        return "Unknown error";
    }
}
