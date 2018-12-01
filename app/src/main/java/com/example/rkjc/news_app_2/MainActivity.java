package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecylcerView;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private ArrayList<NewsItem> mNewsList = new ArrayList<>();
    private NewsItemViewModel mNewsItemViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecylcerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(this, mNewsList);
        mRecylcerView.setAdapter(mNewsRecyclerViewAdapter);
        mRecylcerView.setLayoutManager(new LinearLayoutManager(this));

        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mNewsItemViewModel.loadAllNews().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                mNewsRecyclerViewAdapter.setNews(newsItems);

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            mNewsItemViewModel.syncNewsDatabase();
            return true;
        }if(itemThatWasClickedId == R.id.action_clear){
            mNewsItemViewModel.clearDatabase();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
