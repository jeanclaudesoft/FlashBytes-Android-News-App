package com.claudylab.flashbytes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.claudylab.flashbytes.R;
import com.claudylab.flashbytes.fragments.BookmarkFragment;
import com.claudylab.flashbytes.fragments.CategoriesFragment;
import com.claudylab.flashbytes.fragments.HomeFragment;
import com.claudylab.flashbytes.fragments.SearchFragment;


public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    ImageView home,categories,search,bookmark;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        frameLayout = findViewById(R.id.container);
        home = findViewById(R.id.item_home);
        categories = findViewById(R.id.item_category);
        search = findViewById(R.id.item_search);
        bookmark = findViewById(R.id.item_bookmark);
        home.setBackgroundResource(R.drawable.image_bg_fill);
       home.setImageResource(R.drawable.ic_home_white);

        home.setOnClickListener(view -> {
            home.setImageResource(R.drawable.ic_home_white);
            home.setBackgroundResource(R.drawable.image_bg_fill);
            categories.setImageResource(R.drawable.ic_list);
            categories.setBackgroundResource(R.drawable.image_bg);
            search.setImageResource(R.drawable.ic_search);
            search.setBackgroundResource(R.drawable.image_bg);
            bookmark.setImageResource(R.drawable.ic_bookmark);
            bookmark.setBackgroundResource(R.drawable.image_bg);
            moveToFragment(new HomeFragment());

        });

        categories.setOnClickListener(view -> {
            categories.setImageResource(R.drawable.ic_list_white);
            categories.setBackgroundResource(R.drawable.image_bg_fill);
            home.setImageResource(R.drawable.ic_home);
            home.setBackgroundResource(R.drawable.image_bg);
            search.setImageResource(R.drawable.ic_search);
            search.setBackgroundResource(R.drawable.image_bg);
            bookmark.setImageResource(R.drawable.ic_bookmark);
            bookmark.setBackgroundResource(R.drawable.image_bg);
            moveToFragment(new CategoriesFragment());


        });
        search.setOnClickListener(view -> {
            search.setBackgroundResource(R.drawable.image_bg_fill);
            search.setImageResource(R.drawable.ic_search_white);

            categories.setImageResource(R.drawable.ic_list);
            categories.setBackgroundResource(R.drawable.image_bg);
            home.setImageResource(R.drawable.ic_home);
            home.setBackgroundResource(R.drawable.image_bg);
            bookmark.setImageResource(R.drawable.ic_bookmark);
            bookmark.setBackgroundResource(R.drawable.image_bg);
            moveToFragment(new SearchFragment());

        });
        bookmark.setOnClickListener(view -> {
            bookmark.setImageResource(R.drawable.ic_bookmark_white);
            bookmark.setBackgroundResource(R.drawable.image_bg_fill);
            categories.setImageResource(R.drawable.ic_list);
            categories.setBackgroundResource(R.drawable.image_bg);
            search.setImageResource(R.drawable.ic_search);
            search.setBackgroundResource(R.drawable.image_bg);
            home.setImageResource(R.drawable.ic_home);
            home.setBackgroundResource(R.drawable.image_bg);
            moveToFragment(new BookmarkFragment());
        });



        moveToFragment(new HomeFragment());


    }

    private void moveToFragment(Fragment fragment) {

          getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }


}