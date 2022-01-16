package com.example.pbl2021timerapp.cotoha_manager;

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

public class CotohaApiManager {
    private CotohaApiManagerCallbacks cotohaApiManagerCallbacks;

    private Gson gson = new Gson();
    private String cotohaBearerToken;
    private String accessTokenUrl = "https://api.ce-cotoha.com/v1/oauth/accesstokens";
    private String similarityUrl = "https://api.ce-cotoha.com/api/dev/nlp/v1/similarity";
    private String clientId = BuildConfig.COTOHA_API_CLIENT_ID;
    private String clientSecret = BuildConfig.COTOHA_API_CLIENT_SECRET;

    public CotohaApiManager(CotohaApiManagerCallbacks cotohaApiManagerCallbacks) {
        // コールバックを登録する
        this.cotohaApiManagerCallbacks = cotohaApiManagerCallbacks;

        // Bearer Token が必要なので、始めに取得する
        GetBearerTokenBackgroundTask cotohaBearerTokenGetBackgroundReceiver = new GetBearerTokenBackgroundTask();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(cotohaBearerTokenGetBackgroundReceiver);
    }

    @UiThread
    public void getSimilarity(String s1, String s2) {
        GetSimilarityBackgroundTask getSimilarityBackgroundTask = new GetSimilarityBackgroundTask(s1, s2);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(getSimilarityBackgroundTask);
    }

    /**
     * Bearer Token を非同期通信で取得するインナークラスxzwx
     */
    private class GetBearerTokenBackgroundTask implements Runnable {
        @Override
        public void run() {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("grantType", "client_credentials");
            jsonObj.addProperty("clientId", clientId);
            jsonObj.addProperty("clientSecret", clientSecret);

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(jsonObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(accessTokenUrl)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String originalResponseBody = response.body().string();

                JsonObject cotohaBearerJson = gson.fromJson(originalResponseBody, JsonObject.class);
                cotohaBearerToken = cotohaBearerJson.get("access_token").toString().replace("\"", "");
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

        public GetSimilarityBackgroundTask(String s1, String s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        @WorkerThread
        @Override
        public void run() {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("s1", s1);
            jsonObj.addProperty("s2", s2);

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(jsonObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(similarityUrl)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + cotohaBearerToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String originalResponseBody = response.body().string();
                JsonObject similarityObject = gson.fromJson(originalResponseBody, JsonObject.class);
                String score = similarityObject.getAsJsonObject("result").get("score").toString();
                cotohaApiManagerCallbacks.onSimilarityTaskFinished(Float.parseFloat(score));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
