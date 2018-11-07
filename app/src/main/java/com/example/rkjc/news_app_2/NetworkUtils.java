package com.example.rkjc.news_app_2;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = "NetworkUtils";
    final static String NEWS_API_URL = "https://newsapi.org/v1/articles";
    final static String PARAM_QUERY = "source";
    final static String source = "the-next-web";
    final static String PARAM_SORT = "sortBy";
    final static String sortBy = "latest";
    final static String PARAM_API = "apiKey";
    final static String apiKey = "28dc06b20e504c6ea30162cbd0111f77";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(NEWS_API_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, source)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(PARAM_API, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "url error");
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
