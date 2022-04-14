package com.claudylab.flashbytes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.claudylab.flashbytes.R;
import com.claudylab.flashbytes.models.OnBoard;

import java.util.List;

public class OnBoardAdapter extends RecyclerView.Adapter<OnBoardAdapter.ViewHolder> {

    Context context;
    List<OnBoard> onBoardList;


    public OnBoardAdapter(Context context, List<OnBoard> onBoardList) {
        this.context = context;
        this.onBoardList = onBoardList;
    }

    @NonNull
    @Override
    public OnBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.onboard_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnBoardAdapter.ViewHolder holder, int position) {

        holder.setOnBoardingData(onBoardList.get(position));


    }

    @Override
    public int getItemCount() {
        return onBoardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnboarding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);

        }

        void setOnBoardingData(OnBoard onBoardingItem){
            textTitle.setText(onBoardingItem.getTitle());
            textDescription.setText(onBoardingItem.getDescription());
            imageOnboarding.setImageResource(onBoardingItem.getImage());
        }
    }
}
