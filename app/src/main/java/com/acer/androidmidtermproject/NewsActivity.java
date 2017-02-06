package com.acer.androidmidtermproject;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Article>> {

    private static final String REQUEST_URL = "https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=4d2cc68c14074640bf96ae8f6f3d87e3";
    private static final int NEWS_LOADER_ID = 1;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //newsAdapter = new NewsAdapter(this, new ArrayList<Article>());

        //recyclerView.setAdapter(newsAdapter);
        Log.d("charles","ni sud sa una");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            Log.d("charles","ni sud sa network info");
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            Log.d("charles","ni sud sa error network info");

            // Update empty state with no connection error message
        }
    }

    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(REQUEST_URL);
        Log.d("charles","ni sud sa loader");
        return new NewsLoader(this,baseUri.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        //newsAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        Log.d("charles","onloadfinished: " + data.size());
        if (data != null && !data.isEmpty()) {
            newsAdapter = new NewsAdapter(this,data);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(newsAdapter);
            Log.d("charles","ni pasa sa news adapter");
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {

    }
}
