package com.example.pbl2021timerapp.cotoha;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;

import com.example.pbl2021timerapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CotohaApiManeger {
    private String cotohaBearerToken;
    private String accessTokenUrl = "https://api.ce-cotoha.com/v1/oauth/accesstokens";
    private String similarityUrl = "https://api.ce-cotoha.com/api/dev/nlp/v1/similarity";
    private String clientId = BuildConfig.COTOHA_API_CLIENT_ID;
    private String clientSecret = BuildConfig.COTOHA_API_CLIENT_SECRET;

    public CotohaApiManeger() {
        // Bearer Token が必要なので、始めに取得する
        GetBearerTokenBackgroundTask cotohaBearerTokenGetBackgroundReceiver = new GetBearerTokenBackgroundTask();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(cotohaBearerTokenGetBackgroundReceiver);
    }

    @UiThread
    public void getSimilarity(String s1, String s2, Context context) {
        GetSimilarityBackgroundTask getBearerTokenBackgroundTask = new GetSimilarityBackgroundTask(s1, s2, context);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(getBearerTokenBackgroundTask);
    }

    /**
     * Bearer Token を非同期通信で取得するインナークラス
     */
    private class GetBearerTokenBackgroundTask implements Runnable {
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

                JsonObject cotohaBearerJson = gson.fromJson(originalResponseBody, JsonObject.class);
                cotohaBearerToken = cotohaBearerJson.get("access_token").toString().replace("\"", "");
                Log.d("cotohaBearerTokenStr", cotohaBearerToken);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 類似度を非同期通信で取得するインナークラス
     */
    private class GetSimilarityBackgroundTask implements Runnable {
        private String s1;
        private String s2;
        private Context context;

        public GetSimilarityBackgroundTask(String s1, String s2, Context context) {
            this.s1 = s1;
            this.s2 = s2;
            this.context = context;
        }

        @WorkerThread
        @Override
        public void run() {
            Gson gson = new Gson();
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("s1", s1);
            jsonObj.addProperty("s2", s2);

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObj.toString());
            Request request = new Request.Builder()
                    .url(similarityUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + cotohaBearerToken)
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

                Toast.makeText(context, score, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
