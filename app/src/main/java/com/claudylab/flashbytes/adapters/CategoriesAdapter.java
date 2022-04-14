package com.claudylab.flashbytes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.claudylab.flashbytes.R;
import com.claudylab.flashbytes.models.Categories;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    Context context;
    List<Categories> categoriesList;
    private int selectedItem=0;
    itemClicked itemClicked;

    public CategoriesAdapter(Context context, List<Categories> categoriesList, itemClicked itemClicked) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.itemClicked=itemClicked;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_categories,parent,false),itemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {

        Categories categories = categoriesList.get(position);
        holder.title.setText(categories.getTitle());
        holder.bind();




    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView  title;
       public RelativeLayout relativeLayout;
        itemClicked itemClicked;
        public ViewHolder(@NonNull View itemView, itemClicked itemClicked) {
            super(itemView);

            title = itemView.findViewById(R.id.txtTitleCat);
            relativeLayout = itemView.findViewById(R.id.container);
            this.itemClicked=itemClicked;
        }
        void bind() {
            if (selectedItem == -1) {
                relativeLayout.setSelected(false);
                title.setTextColor(context.getResources().getColor(R.color.dark_grey));

            } else {
                if (selectedItem == getAdapterPosition()) {
                    relativeLayout.setSelected(true);
                    title.setTextColor(context.getResources().getColor(R.color.white));

                } else {
                    relativeLayout.setSelected(false);
                    title.setTextColor(context.getResources().getColor(R.color.dark_grey));


                }
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    relativeLayout.setSelected(true);
                    title.setTextColor(context.getResources().getColor(R.color.white));
                    if (selectedItem != getAdapterPosition()) {
                        notifyItemChanged(selectedItem);
                        selectedItem = getAdapterPosition();
                    }
                    itemClicked.sourceClick(getAdapterPosition(),selectedItem);
                }
            });
        }

    }

    public  interface  itemClicked{
        void sourceClick(int position, int selectItem);
    }
}
