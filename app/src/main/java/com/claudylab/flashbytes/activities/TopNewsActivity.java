package com.claudylab.flashbytes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.claudylab.flashbytes.Constant;
import com.claudylab.flashbytes.adapters.TopNewsAdapter;
import com.claudylab.flashbytes.http.ApiBuilder;
import com.claudylab.flashbytes.http.EndPoints;
import com.claudylab.flashbytes.models.topnews.Datum;
import com.claudylab.flashbytes.models.topnews.TopNews;
import com.claudylab.flashbytes.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopNewsActivity extends AppCompatActivity {

    RecyclerView topNews;
    private int mItemCount = 5, mPageNo = 1;
    private boolean mUserScrolled = true;
    private int page = 1, mPastVisibleItems, mVisibleItemCount, mTotalItemCount;
    LinearLayoutManager linearLayoutManager;
    boolean isLoading = false;
    List<Datum> topNewsList = new ArrayList<>();
    TopNewsAdapter adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news);

        topNews = findViewById(R.id.rvAllNews);
        sharedPreferences = getSharedPreferences("flash",MODE_PRIVATE);
        linearLayoutManager = new LinearLayoutManager(TopNewsActivity.this);
        topNews.setLayoutManager(linearLayoutManager);

        getNews(page);
        implementScrollListener();

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void getNews(int page) {
        String lang = sharedPreferences.getString("lang","");
        EndPoints endPoints = ApiBuilder.retrofit.create(EndPoints.class);
        Call<TopNews> call = endPoints.getTopNews(Constant.TOKEN,lang,page);
        call.enqueue(new Callback<TopNews>() {
            @Override
            public void onResponse(Call<TopNews> call, Response<TopNews> response) {

                if (response.isSuccessful()){

                    TopNews topNew = response.body();
                    topNewsList = topNew.getData();

                     adapter = new TopNewsAdapter(TopNewsActivity.this,topNewsList);
                    topNews.setAdapter(adapter);

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
        topNews.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mUserScrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,
                                   int dy) {

                super.onScrolled(recyclerView, dx, dy);

                mVisibleItemCount = linearLayoutManager.getChildCount();
                mTotalItemCount = linearLayoutManager.getItemCount();
                mPastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (mUserScrolled && (mVisibleItemCount + mPastVisibleItems) == mTotalItemCount) {
                    mUserScrolled = false;

                    updateRecyclerView();
                }//
            }
        });

    }

    private void updateRecyclerView() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(TopNewsActivity.this, "Chargement de nouvelles actualités", Toast.LENGTH_SHORT).show();

                page++;
                getNews(page);
            }
        }, 5000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}