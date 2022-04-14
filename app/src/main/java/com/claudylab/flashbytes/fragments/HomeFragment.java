package com.claudylab.flashbytes.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.claudylab.flashbytes.Constant;

import com.claudylab.flashbytes.activities.AllNewsActivity;
import com.claudylab.flashbytes.activities.TopNewsActivity;
import com.claudylab.flashbytes.adapters.HomeNewsAdapter;
import com.claudylab.flashbytes.adapters.ScaleTransformer;
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


public class HomeFragment extends Fragment {

    RecyclerView allNewsView;
    List<Datum> topNewsList;
    ViewPager topNewsView;
    TextView moreTop, moreAll;
    LinearLayout container;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("flash", Context.MODE_PRIVATE);
        topNewsView = view.findViewById(R.id.rvTopNews);
        allNewsView = view.findViewById(R.id.rvAllNews);
        moreTop = view.findViewById(R.id.seeMoreTop);
        moreAll = view.findViewById(R.id.seeMoreAll);
        container = view.findViewById(R.id.lytContainer);
        progressBar = view.findViewById(R.id.progress);
        allNewsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // sourceView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        topNewsView.setOffscreenPageLimit(5);


        moreAll.setOnClickListener(views -> {
            startActivity(new Intent(getContext(), AllNewsActivity.class));
        });
        moreTop.setOnClickListener(views -> {
            startActivity(new Intent(getContext(), TopNewsActivity.class));
        });


        getTopNews();
        // getSources();
        // getAllNews();

    }


    private void getAllNews() {
        String lang = sharedPreferences.getString("lang", "");
        EndPoints endPoints = ApiBuilder.retrofit.create(EndPoints.class);
        Call<TopNews> call = endPoints.getAllNews(Constant.TOKEN, lang,"", 1);
        call.enqueue(new Callback<TopNews>() {
            @Override
            public void onResponse(Call<TopNews> call, Response<TopNews> response) {

                if (response.isSuccessful()) {


                    TopNews topNews = response.body();
                    List<Datum> newsData = topNews.getData();

                    TopNewsAdapter topNewsAdapter = new TopNewsAdapter(getContext(), newsData);
                    allNewsView.setAdapter(topNewsAdapter);
                    progressBar.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);

                } else {
                    Log.d("ERROR TOP NEWS", response.toString());
                }

            }

            @Override
            public void onFailure(Call<TopNews> call, Throwable t) {

            }
        });
    }

    private void getTopNews() {

        String lang = sharedPreferences.getString("lang", "");
        EndPoints endPoints = ApiBuilder.retrofit.create(EndPoints.class);
        Call<TopNews> call = endPoints.getTopNews(Constant.TOKEN, lang, 1);
        call.enqueue(new Callback<TopNews>() {
            @Override
            public void onResponse(Call<TopNews> call, Response<TopNews> response) {

                if (response.isSuccessful()) {

                    topNewsList = new ArrayList<>();
                    TopNews topNews = response.body();
                    topNewsList = topNews.getData();

                    HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(getContext(), topNewsList);
                    topNewsView.setAdapter(homeNewsAdapter);
                    topNewsView.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
                    topNewsView.setPageTransformer(false, new ScaleTransformer(getContext()));

                    getAllNews();
                } else {
                    Log.d("ERROR TOP NEWS", response.toString());
                }

            }

            @Override
            public void onFailure(Call<TopNews> call, Throwable t) {

            }
        });

    }

}