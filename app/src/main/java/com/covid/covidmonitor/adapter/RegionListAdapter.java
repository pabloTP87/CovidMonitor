package com.covid.covidmonitor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid.covidmonitor.Model.RegionList;
import com.covid.covidmonitor.R;

import java.util.ArrayList;

public class RegionListAdapter extends RecyclerView.Adapter<RegionListAdapter.ViewHolder>{

    private ArrayList<RegionList> regionList;
    private RegionOnClickListener regionOnClickListener;

    public RegionListAdapter(ArrayList<RegionList> regionList, RegionOnClickListener regionOnClickListener){
        this.regionList = regionList;
        this.regionOnClickListener = regionOnClickListener;
    }

    @NonNull
    @Override
    public RegionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.region_list,viewGroup,false);
        return new ViewHolder(view, regionOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RegionListAdapter.ViewHolder holder, int position) {
        holder.regionName.setText(regionList.get(position).getRegionName());
    }

    @Override
    public int getItemCount() {
        return regionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView regionName;
        RegionOnClickListener regionOnClickListener;

        ViewHolder(View itemView, RegionOnClickListener regionOnClickListener){
            super(itemView);
            regionName = itemView.findViewById(R.id.region_name);
            this.regionOnClickListener = regionOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            regionOnClickListener.getRegionListClick(getAdapterPosition());
        }
    }

    public interface RegionOnClickListener{
        void getRegionListClick(int position);
    }
}



