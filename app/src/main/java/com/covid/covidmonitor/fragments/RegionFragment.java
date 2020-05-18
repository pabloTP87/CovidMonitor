package com.covid.covidmonitor.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.covid.covidmonitor.Interface.ChileCoronaApi;
import com.covid.covidmonitor.Model.Region;
import com.covid.covidmonitor.Model.RegionList;
import com.covid.covidmonitor.R;
import com.covid.covidmonitor.RegionDataActivity;
import com.covid.covidmonitor.adapter.RegionListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegionFragment extends Fragment implements RegionListAdapter.RegionOnClickListener {

    private ArrayList<RegionList> regionList = new ArrayList<>();
    private RecyclerView regionRecycler;
    private RecyclerView.Adapter regionListAdapter;
    private RecyclerView.LayoutManager manager;

    private ProgressBar listLoad;
    private TextView textLoad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_region, container, false);

        DividerItemDecoration did = new DividerItemDecoration(Objects.requireNonNull(getActivity()),DividerItemDecoration.VERTICAL);

        regionRecycler = view.findViewById(R.id.region_list_view);
        manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        regionRecycler.addItemDecoration(did);
        regionRecycler.setLayoutManager(manager);

        listLoad = view.findViewById(R.id.region_list_load);
        textLoad = view.findViewById(R.id.region_text_load);

        getRegionData();

        // Inflate the layout for this fragment
        return view;
    }

    private void getRegionData(){

        ArrayList<RegionList> list = new ArrayList<>();
        // Instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v3/models/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // llamamos a la interfaz
        ChileCoronaApi chileCoronaApi = retrofit.create(ChileCoronaApi.class);
        Call <HashMap<String,Region>> call = chileCoronaApi.getRegionName();

        call.enqueue(new Callback<HashMap<String, Region>>() {
            @Override
            public void onResponse(Call<HashMap<String, Region>> call, Response<HashMap<String, Region>> response) {
                if (!response.isSuccessful()){
                    Log.d("fallo","Código: " + response.code());
                }

                HashMap<String,Region> regionData = response.body();

                assert regionData != null;
                for(String id : regionData.keySet()){
                    regionList.add(new RegionList(id,regionData.get(id).getRegion()));
                }

                initRecycler(regionList);
                listLoad.setVisibility(View.GONE);
                textLoad.setVisibility(View.GONE);
                regionRecycler.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<HashMap<String, Region>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getRegionListClick(int position) {
        Intent intent = new Intent(getActivity(), RegionDataActivity.class);
        intent.putExtra("regionId",regionList.get(position).getId());
        intent.putExtra("regionName",regionList.get(position).getRegionName());
        startActivity(intent);
    }

    private void initRecycler(ArrayList<RegionList> list){
        regionListAdapter = new RegionListAdapter(list,this);
        regionRecycler.setAdapter(regionListAdapter);
    }
}
