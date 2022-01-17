package com.example.pbl2021timerapp.japan_news_manager;

import com.example.pbl2021timerapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JapanNewsManager {
    private JapanNewsManagerCallbacks _japanNewsManagerCallbacks;
    private String randomSpeechText;

    public JapanNewsManager(JapanNewsManagerCallbacks japanNewsManagerCallbacks) {
        _japanNewsManagerCallbacks = japanNewsManagerCallbacks;
    }

    public void start() {
        GetJapanNewsBackgroundTask getJapanNewsBackgroundTask = new GetJapanNewsBackgroundTask();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(getJapanNewsBackgroundTask);
    }

    /**
     * 日本のトップニュースを取得するインナークラス
     */
    private class GetJapanNewsBackgroundTask implements Runnable {
        private Gson gson = new Gson();
        private String newsApiUrl = "https://newsapi.org/v2/top-headlines";
        private String country = "jp";
        private String[] category = {"business", "entertainment", "health", "science", "sports", "technology"};
        private String apiKey = BuildConfig.NEWS_API_KEY;

        @Override
        public void run() {
            Random r = new Random();
            String targetCategory = category[r.nextInt(category.length)];
            String url = String.format("%s?country=%s&category=%s&apiKey=%s", newsApiUrl, country, targetCategory, apiKey);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String originalResponseBody = response.body().string();

                JsonObject japanNewsJson = gson.fromJson(originalResponseBody, JsonObject.class);
                randomSpeechText = japanNewsJson.getAsJsonArray("articles").get(0).getAsJsonObject().get("title").toString();
                _japanNewsManagerCallbacks.onReceivedNewsData(randomSpeechText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
