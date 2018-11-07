package com.example.rkjc.news_app_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static ArrayList<NewsItem> parseNews(String jsonResult){
        ArrayList<NewsItem> newsList = new ArrayList<>();
        try {
            JSONObject mainJSONObject = new JSONObject(jsonResult);
            JSONArray articles = mainJSONObject.getJSONArray("articles");

            for(int i = 0; i < articles.length(); i++){
                JSONObject article = articles.getJSONObject(i);
                newsList.add(new NewsItem(article.getString("title"), article.getString("url"), article.getString("publishedAt"), article.getString("description")));
            }
        } catch (JSONException e) {
            Log.e(TAG, "json error");
            e.printStackTrace();
        }
        return newsList;
    }
}


