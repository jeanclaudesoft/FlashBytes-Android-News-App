package com.claudylab.flashbytes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.claudylab.flashbytes.Constant;


import com.claudylab.flashbytes.adapters.SourcesAdapter;
import com.claudylab.flashbytes.adapters.TopNewsAdapter;
import com.claudylab.flashbytes.http.ApiBuilder;
import com.claudylab.flashbytes.http.EndPoints;

import com.claudylab.flashbytes.models.sources.Sources;
import com.claudylab.flashbytes.models.topnews.Datum;
import com.claudylab.flashbytes.models.topnews.TopNews;
import com.claudylab.flashbytes.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNewsActivity extends AppCompatActivity {

    RecyclerView allNewsView, sourceView;
    private boolean mUserScrolled = true;
    private int page = 1, mPastVisibleItems, mVisibleItemCount, mTotalItemCount;
    LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);

        allNewsView = findViewById(R.id.rvAllNews);
        sourceView = findViewById(R.id.rvTopCat);
        linearLayoutManager = new LinearLayoutManager(AllNewsActivity.this);
        sourceView.setLayoutManager(new LinearLayoutManager(AllNewsActivity.this));

        sharedPreferences = getSharedPreferences("flash",MODE_PRIVATE);
        allNewsView.setLayoutManager(linearLayoutManager);

        //getSources();
        getAllNews(page);
        implementScrollListener();

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void getAllNews(int page) {

        String lang = sharedPreferences.getString("lang", "");
        EndPoints endPoints = ApiBuilder.retrofit.create(EndPoints.class);
        Call<TopNews> call = endPoints.getAllNews(Constant.TOKEN, lang,"", page);
        call.enqueue(new Callback<TopNews>() {
            @Override
            public void onResponse(Call<TopNews> call, Response<TopNews> response) {

                if (response.isSuccessful()) {


                    TopNews topNews = response.body();
                    List<Datum> newsData = topNews.getData();

                    TopNewsAdapter topNewsAdapter = new TopNewsAdapter(AllNewsActivity.this, newsData);
                    allNewsView.setAdapter(topNewsAdapter);


                } else {
                    Log.d("ERROR TOP NEWS", response.toString());
                }

            }

            @Override
            public void onFailure(Call<TopNews> call, Throwable t) {

            }
        });
    }




    private void implementScrollListener() {
        allNewsView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mUserScrolled = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx,
                                   int dy) {

                super.onScrolled(recyclerView, dx, dy);

                mVisibleItemCount = linearLayoutManager.getChildCount();
                mTotalItemCount = linearLayoutManager.getItemCount();
                mPastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (mUserScrolled && (mVisibleItemCount + mPastVisibleItems) == mTotalItemCount) {
                    mUserScrolled = false;

                    updateRecyclerView();
                }
//
            }
        });

    }

    private void updateRecyclerView() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(AllNewsActivity.this, "Chargement de nouvelles actualités", Toast.LENGTH_SHORT).show();

                page++;
                getAllNews(page);
            }
        }, 5000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}