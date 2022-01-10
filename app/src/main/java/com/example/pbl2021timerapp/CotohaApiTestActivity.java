package com.example.pbl2021timerapp;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CotohaApiTestActivity extends AppCompatActivity {
    private Button cotohaSendButton;
    private String cotohaBearerTokenStr;
    private TextView answerCotohaText;
    private TextView inputCotohaText;

    private String accessTokenUrl = "https://api.ce-cotoha.com/v1/oauth/accesstokens";
    private String similarityUrl = "https://api.ce-cotoha.com/api/dev/nlp/v1/similarity";
    private String clientId = BuildConfig.COTOHA_API_CLIENT_ID;
    private String clientSecret = BuildConfig.COTOHA_API_CLIENT_SECRET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotoha_api_test);

        CotohaBearerTokenGetBackgroundReceiver cotohaBearerTokenGetBackgroundReceiver = new CotohaBearerTokenGetBackgroundReceiver();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(cotohaBearerTokenGetBackgroundReceiver);

        answerCotohaText = findViewById(R.id.answerCotohaText);
        inputCotohaText = findViewById(R.id.inputCotohaText);

        cotohaSendButton = findViewById(R.id.cotohaSendButton);
        cotohaSendButton.setOnClickListener(view -> {
            CotohaSimilarityReceiver cotohaSimilarityReceiver = new CotohaSimilarityReceiver();
            executorService.submit(cotohaSimilarityReceiver);
        });
    }

    private class CotohaBearerTokenGetBackgroundReceiver implements Runnable {
        @Override
        public void run() {
            Gson gson = new Gson();
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("grantType", "client_credentials");
            jsonObj.addProperty("clientId", clientId);
            jsonObj.addProperty("clientSecret", clientSecret);

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObj.toString());
            Request request = new Request.Builder()
                    .url(accessTokenUrl)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String originalResponseBody = response.body().string();

                JsonObject cotohaBearerToken = gson.fromJson(originalResponseBody, JsonObject.class);
                cotohaBearerTokenStr = cotohaBearerToken.get("access_token").toString().replace("\"", "");
                Log.d("cotohaBearerTokenStr", cotohaBearerTokenStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class CotohaSimilarityReceiver implements Runnable {
        @WorkerThread
        @Override
        public void run() {
            Gson gson = new Gson();
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("s1", answerCotohaText.getText().toString());
            jsonObj.addProperty("s2", inputCotohaText.getText().toString());

            System.out.println(answerCotohaText.getText().toString());
            System.out.println(inputCotohaText.getText().toString());

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObj.toString());
            Request request = new Request.Builder()
                    .url(similarityUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + cotohaBearerTokenStr)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String originalResponseBody = response.body().string();
                JsonObject cotohaSimilarity = gson.fromJson(originalResponseBody, JsonObject.class);

                Log.d("aaa", originalResponseBody);
                String resultObjectStr = cotohaSimilarity.get("result").toString();
                Log.d("ccc", resultObjectStr);
                JsonObject result = gson.fromJson(resultObjectStr, JsonObject.class);
                System.out.println(result);
                String score = result.get("score").toString();
                Log.d("bbb", score);

                Toast.makeText(getApplicationContext() , score, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}