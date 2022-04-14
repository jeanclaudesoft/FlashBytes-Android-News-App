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
import com.claudylab.flashbytes.models.sources.Datum;

import java.util.List;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.ViewHolder> {

    Context context;
    List<Datum> DatumList;
    private static int lastClickedPosition = -1;
    private int selectedItem;
    itemClicked itemClicked;

    public SourcesAdapter(Context context, List<Datum> DatumList,itemClicked itemClicked) {
        this.context = context;
        this.DatumList = DatumList;
        this.itemClicked=itemClicked;
        selectedItem = 0;
    }

    @NonNull
    @Override
    public SourcesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_categories,parent,false),itemClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull SourcesAdapter.ViewHolder holder, int position) {

        Datum Datum = DatumList.get(position);
        holder.title.setText(Datum.getDomain().toUpperCase());

        if (selectedItem == position) {
            holder.relativeLayout.setSelected(true);
            holder.title.setTextColor(context.getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(view -> {
            itemClicked.sourceClick(position,selectedItem,holder);
        });

    }

    @Override
    public int getItemCount() {
        return DatumList.size();
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
    }

    public  interface  itemClicked{
        void sourceClick(int position, int selectItem, ViewHolder viewHolder);
    }
}
