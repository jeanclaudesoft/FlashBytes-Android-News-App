package com.claudylab.flashbytes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.claudylab.flashbytes.activities.NewsDetailActivity;
import com.claudylab.flashbytes.models.topnews.Datum;
import com.claudylab.flashbytes.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopNewsAdapter extends RecyclerView.Adapter<TopNewsAdapter.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    Context context;
    List<Datum> DatumList;

    public TopNewsAdapter(Context context, List<Datum> DatumList) {
        this.context = context;
        this.DatumList = DatumList;
    }

    @NonNull
    @Override
    public TopNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==VIEW_TYPE_ITEM)
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.all_news,parent,false));
        else return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading,parent,false));
    }

    @Override
    public int getItemViewType(int position) {

        return DatumList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull TopNewsAdapter.ViewHolder holder, int position) {

        if (getItemViewType(position) == VIEW_TYPE_ITEM){
            Datum datum = DatumList.get(position);
            holder.title.setText(DatumList.get(position).getTitle());

            if (DatumList.get(position).getCategories().size() != 0)
                holder.category.setText(DatumList.get(position).getCategories().get(0).toUpperCase());
            else
                holder.category.setVisibility(View.GONE);

            String image = datum.getImageUrl();
            System.out.println("IMAGE" + image);

            if (image.equals(""))
                Picasso.get().load(R.color.purple_200).into(holder.postImage);

            else
                Picasso.get().load(image).into(holder.postImage);

            holder.itemView.setOnClickListener(view -> {

                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("data", datum);
                intent.putExtra("type", "top");
                context.startActivity(intent);
            });

            holder.bookmark.setOnClickListener(view -> {

            });
        }

    }

    @Override
    public int getItemCount() {
        return DatumList == null ? 0 : DatumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView postImage;
        TextView category, title,date;
        ImageView bookmark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postImage = itemView.findViewById(R.id.ivImage);
            category = itemView.findViewById(R.id.txtCat);
            title = itemView.findViewById(R.id.txtTitle);
            bookmark = itemView.findViewById(R.id.bookmark);

        }
    }
}
