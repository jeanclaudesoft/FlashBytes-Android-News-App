package com.claudylab.flashbytes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;



import com.claudylab.flashbytes.activities.NewsDetailActivity;
import com.claudylab.flashbytes.models.topnews.Datum;
import com.claudylab.flashbytes.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;


import java.util.List;

public class HomeNewsAdapter extends PagerAdapter {

    Context context;
    List<Datum> DatumList;

    public HomeNewsAdapter(Context context, List<Datum> DatumList) {
        this.context = context;
        this.DatumList = DatumList;
    }

    public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
        ((ViewPager) paramViewGroup).removeView((View) paramObject);
    }


    @Override
    public int getCount() {
        return DatumList.size();
    }


    @NonNull
    public Object instantiateItem(ViewGroup paramViewGroup, int position) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.top_news, null);
        RoundedImageView postImage = itemView.findViewById(R.id.ivImage);
        TextView category = itemView.findViewById(R.id.txtCat);
        TextView title = itemView.findViewById(R.id.txtTitle);
        ImageView bookmark = itemView.findViewById(R.id.bookmark);


        Datum datum = DatumList.get(position);
        title.setText(DatumList.get(position).getTitle());

        if (DatumList.get(position).getCategories().size() != 0)
            category.setText(DatumList.get(position).getCategories().get(0).toUpperCase());
        else
            category.setVisibility(View.GONE);

        String image = datum.getImageUrl();
        System.out.println("IMAGE" + image);

        if (image.equals(""))
            Picasso.get().load(R.color.purple_200).into(postImage);

        else
            Picasso.get().load(image).into(postImage);
        // Picasso.get().load(image).into(postImage);

       itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("data", datum);
            intent.putExtra("type", "featured");
            context.startActivity(intent);
        });

       bookmark.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

        ((ViewPager) paramViewGroup).addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);

    }

}
