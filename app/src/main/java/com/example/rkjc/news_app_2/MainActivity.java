package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
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

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
//    private TextView mSearchResultsTextView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> news = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private static final String SEARCH_QUERY_URL_EXTRA = "searchQuery";
    private static final String SEARCH_QUERY_RESULTS = "searchResults";
    private String newsApiSearchResults;
    private static final int LOADER_ID = 1;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mSearchResultsTextView = (TextView) findViewById(R.id.newsapi_search_results_json);
        mRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        mAdapter = new NewsRecyclerViewAdapter(this, news);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState != null && savedInstanceState.containsKey(SEARCH_QUERY_RESULTS)){
            String searchResults = savedInstanceState.getString(SEARCH_QUERY_RESULTS);
            populateRecyclerView(searchResults);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_QUERY_RESULTS, newsApiSearchResults);

    }

//    private void makeNewsApiSearchQuery() {
//        URL newsApiSearchUrl = NetworkUtils.buildUrl();
//        new NewsQueryTask().execute(newsApiSearchUrl);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }

    private URL makeNewsApiSearchQuery() {
        URL newsApiSearchUrl = NetworkUtils.buildUrl();
//        new NewsQueryTask().execute(newsApiSearchUrl);
        return newsApiSearchUrl;
    }

    protected void onResume() {
        super.onResume();
    }

    public void populateRecyclerView(String searchResults){
        Log.d("mycode", searchResults);
        news = JsonUtils.parseNews(searchResults);
        mAdapter.mNews.addAll(news);
        mAdapter.notifyDataSetChanged();
    }

    private void showJsonDataView() {
//        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            URL url = makeNewsApiSearchQuery();
            Bundle bundle = new Bundle();
            bundle.putString(SEARCH_QUERY_URL_EXTRA, url.toString());
            new NewsQueryTask().execute(url);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String newsApiSearchResults = null;
            try {
                newsApiSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                Log.e(TAG, "io error");
                e.printStackTrace();
            }
            return newsApiSearchResults;
        }

        @Override
        protected void onPostExecute(String newsApiSearchResults) {
            if (newsApiSearchResults != null && !newsApiSearchResults.equals("")) {
                //showJsonDataView();
//                mSearchResultsTextView.setText(newsApiSearchResults);
                populateRecyclerView(newsApiSearchResults);
            } else {
                Log.d(TAG, "null");
                System.out.println("error");
            }
        }
    }
}
