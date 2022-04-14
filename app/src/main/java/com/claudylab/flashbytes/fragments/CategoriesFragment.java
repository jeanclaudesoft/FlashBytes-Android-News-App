package com.claudylab.flashbytes.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.claudylab.flashbytes.Constant;

import com.claudylab.flashbytes.adapters.CategoriesAdapter;

import com.claudylab.flashbytes.adapters.TopNewsAdapter;
import com.claudylab.flashbytes.http.ApiBuilder;
import com.claudylab.flashbytes.http.EndPoints;

import com.claudylab.flashbytes.models.Categories;
import com.claudylab.flashbytes.models.topnews.Datum;
import com.claudylab.flashbytes.models.topnews.TopNews;
import com.claudylab.flashbytes.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesFragment extends Fragment implements CategoriesAdapter.itemClicked {


    CategoriesAdapter categoriesAdapter;
    RecyclerView rvCategories, resultView;
    List<Categories> categoriesList;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;


    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("flash", Context.MODE_PRIVATE);
        categoriesList = new ArrayList<>();
        rvCategories = view.findViewById(R.id.rvTopCat);
        resultView = view.findViewById(R.id.rvAllNews);
        progressBar = view.findViewById(R.id.progress);
        resultView.setLayoutManager(new LinearLayoutManager(getContext()));

        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesList.add(new Categories("General", "general"));
        categoriesList.add(new Categories("Politique", "politics"));
        categoriesList.add(new Categories("Foof", "food"));
        categoriesList.add(new Categories("Voyage", "travel"));
        categoriesList.add(new Categories("Business", "business"));
        categoriesList.add(new Categories("Divertissement", "entertainment"));
        categoriesList.add(new Categories("Sante", "health"));
        categoriesList.add(new Categories("Science", "science"));
        categoriesList.add(new Categories("Sports", "sports"));
        categoriesList.add(new Categories("Technologie", "technology"));

        categoriesAdapter = new CategoriesAdapter(getContext(), categoriesList, this);
        rvCategories.setAdapter(categoriesAdapter);
        getNewws("general");
    }

    @Override
    public void sourceClick(int position, int selectItem) {

        String category = categoriesList.get(position).getCode();

        getNewws(category);
    }

    private void getNewws(String category) {

        String lang = sharedPreferences.getString("lang","");
        progressBar.setVisibility(View.VISIBLE);
        resultView.setVisibility(View.GONE);

        EndPoints endPoints = ApiBuilder.retrofit.create(EndPoints.class);
        Call<TopNews> call = endPoints.getAllNews(Constant.TOKEN, lang ,category,1);
        call.enqueue(new Callback<TopNews>() {
            @Override
            public void onResponse(Call<TopNews> call, Response<TopNews> response) {

                if (response.isSuccessful()) {


                    TopNews topNews = response.body();
                    List<Datum> newsData = topNews.getData();

                    TopNewsAdapter topNewsAdapter = new TopNewsAdapter(getContext(), newsData);
                    resultView.setAdapter(topNewsAdapter);
                    progressBar.setVisibility(View.GONE);
                    resultView.setVisibility(View.VISIBLE);

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