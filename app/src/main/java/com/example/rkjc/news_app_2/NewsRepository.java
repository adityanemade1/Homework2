package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsRepository {
    private static NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNews;

    public NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mAllNews = mNewsItemDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> loadAllNews() {
        mAllNews = mNewsItemDao.loadAllNewsItems();
        return mAllNews;
    }

    public void syncNewsDatabase() {
        new SyncNewsDatabase(mNewsItemDao).execute();
    }

    private static class SyncNewsDatabase extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskDao;

        SyncNewsDatabase(NewsItemDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.clearAll();
            URL newsSearchUrl = NetworkUtils.buildUrl();
            String newsSearchResult = null;
            try {
                newsSearchResult = NetworkUtils.getResponseFromHttpUrl(newsSearchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<NewsItem> newsItems = JsonUtils.parseNews(newsSearchResult);
            mAsyncTaskDao.insert(newsItems);
            return null;
        }
    }

    public void clearDatabase() {
        new SyncClearDatabase(mNewsItemDao).execute();
    }

    private static class SyncClearDatabase extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskDao;

        SyncClearDatabase(NewsItemDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.clearAll();
            return null;
        }
    }

}
