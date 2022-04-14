package com.claudylab.flashbytes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.claudylab.flashbytes.R;
import com.claudylab.flashbytes.models.Languages;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    Context context;
    List<Languages> languagesList;
    private int selectedItem=0;
    itemClicked itemClicked;

    public LanguageAdapter(Context context, List<Languages> languagesList, itemClicked itemClicked) {
        this.context = context;
        this.languagesList = languagesList;
        this.itemClicked=itemClicked;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LanguageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lang,parent,false),itemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.ViewHolder holder, int position) {

        Languages languages = languagesList.get(position);
        holder.title.setText(languages.getTitle());
        holder.index.setText(languages.getTitle().substring(0,2));
        holder.bind(languages);




    }

    @Override
    public int getItemCount() {
        return languagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView  title, index;
       public LinearLayout relativeLayout;
        itemClicked itemClicked;
        ImageView select, unselect;
        public ViewHolder(@NonNull View itemView, itemClicked itemClicked) {
            super(itemView);

            title = itemView.findViewById(R.id.tvLang);
            index = itemView.findViewById(R.id.tvIndex);
            relativeLayout = itemView.findViewById(R.id.container);
            select = itemView.findViewById(R.id.ivCheck);
            unselect = itemView.findViewById(R.id.ivUncheck);
            this.itemClicked=itemClicked;
        }

        void bind(final Languages languages) {
            if (selectedItem == -1) {
                relativeLayout.setBackgroundResource(R.drawable.language_unselected);
                unselect.setVisibility(View.VISIBLE);
                select.setVisibility(View.GONE);

            } else {
                if (selectedItem == getAdapterPosition()) {
                    relativeLayout.setBackgroundResource(R.drawable.language_selected);
                    unselect.setVisibility(View.GONE);
                    select.setVisibility(View.VISIBLE);

                } else {
                    relativeLayout.setBackgroundResource(R.drawable.language_unselected);
                    unselect.setVisibility(View.VISIBLE);
                    select.setVisibility(View.GONE);
                }
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    relativeLayout.setBackgroundResource(R.drawable.language_selected);
                    unselect.setVisibility(View.GONE);
                    select.setVisibility(View.VISIBLE);
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
