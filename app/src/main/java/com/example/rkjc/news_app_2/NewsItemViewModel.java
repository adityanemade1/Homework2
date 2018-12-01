package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsRepository mRepository;
    private LiveData<List<NewsItem>> mAllNews;

    public NewsItemViewModel (Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        mAllNews = mRepository.loadAllNews();
    }

    public LiveData<List<NewsItem>> loadAllNews() {
        return mAllNews;
    }

    public void syncNewsDatabase() {
        mRepository.syncNewsDatabase();
    }

    public void clearDatabase() {
        mRepository.clearDatabase();
    }
}
