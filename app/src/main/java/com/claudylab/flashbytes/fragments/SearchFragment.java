package com.claudylab.flashbytes.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.claudylab.flashbytes.Constant;

import com.claudylab.flashbytes.adapters.TopNewsAdapter;
import com.claudylab.flashbytes.http.ApiBuilder;
import com.claudylab.flashbytes.http.EndPoints;
import com.claudylab.flashbytes.models.topnews.Datum;
import com.claudylab.flashbytes.models.topnews.TopNews;
import com.claudylab.flashbytes.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    RecyclerView rvSearch;
    SearchView textInputEditText;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sharedPreferences = getActivity().getSharedPreferences("flash", Context.MODE_PRIVATE);
        textInputEditText = view.findViewById(R.id.search);
        rvSearch = view.findViewById(R.id.rvAllNews);
        progressBar = view.findViewById(R.id.progress);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));

        textInputEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getNews(String text) {
        String lang = sharedPreferences.getString("lang", "");
        progressBar.setVisibility(View.VISIBLE);
        rvSearch.setVisibility(View.GONE);

        EndPoints endPoints = ApiBuilder.retrofit.create(EndPoints.class);
        Call<TopNews> call = endPoints.searchNews(Constant.TOKEN, lang,text ,1);
        call.enqueue(new Callback<TopNews>() {
            @Override
            public void onResponse(Call<TopNews> call, Response<TopNews> response) {

                if (response.isSuccessful()) {


                    TopNews topNews = response.body();
                    List<Datum> newsData = topNews.getData();

                    TopNewsAdapter topNewsAdapter = new TopNewsAdapter(getContext(), newsData);
                    rvSearch.setAdapter(topNewsAdapter);
                    progressBar.setVisibility(View.GONE);
                    rvSearch.setVisibility(View.VISIBLE);

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