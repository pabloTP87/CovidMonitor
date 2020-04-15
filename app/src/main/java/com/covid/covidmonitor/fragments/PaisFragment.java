package com.covid.covidmonitor.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.covid.covidmonitor.Interface.ChileCoronaApi;
import com.covid.covidmonitor.Model.CountryUpdate;
import com.covid.covidmonitor.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaisFragment extends Fragment {

    private TextView infectadosTxt;
    private TextView muertesTxt;
    private TextView recuperadosTxt;
    private TextView fechaTxt;
    private TextView tittleTxt;
    private TextView nuevosCasosTxt;
    private ProgressBar dataLoad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pais, container, false);

        infectadosTxt = view.findViewById(R.id.infectados);
        muertesTxt = view.findViewById(R.id.muertes);
        recuperadosTxt = view.findViewById(R.id.recuperados);
        fechaTxt = view.findViewById(R.id.date_update);
        tittleTxt = view.findViewById(R.id.tittle);
        nuevosCasosTxt = view.findViewById(R.id.nuevos_casos);
        dataLoad = view.findViewById(R.id.country_data_load);

        getPost();
        // Inflate the layout for this fragment
        return view;
    }

    private void getPost(){
        dataLoad.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v3/latest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // llamamos a la interfaz
        ChileCoronaApi chileCoronaApi = retrofit.create(ChileCoronaApi.class);
        // Creamos un Call y le asignamos el método de la interfaz
        Call<CountryUpdate> call = chileCoronaApi.getPost();

        call.enqueue(new Callback<CountryUpdate>() {
            @Override
            public void onResponse(Call<CountryUpdate> call, Response<CountryUpdate> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getActivity(),"Código" + response.code(), Toast.LENGTH_LONG);
                    toast.show();
                }

                CountryUpdate countryUpdate = response.body();

                assert countryUpdate != null;

                String infectadosLabel = "Infectados: " + countryUpdate.getConfirmed();
                String muertesLabel = "Muertes: " + countryUpdate.getDeaths();
                String recuperadosLabel = "Recuperados: " + countryUpdate.getRecovered();
                String fechaLabel = "Fecha de actualización " + countryUpdate.getDay();

                infectadosTxt.setText(infectadosLabel);
                muertesTxt.setText(muertesLabel);
                recuperadosTxt.setText(recuperadosLabel);
                fechaTxt.setText(fechaLabel);

                dataLoad.setVisibility(View.GONE);
                showView();
            }

            @Override
            public void onFailure(Call<CountryUpdate> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void showView(){

        tittleTxt.setVisibility(View.VISIBLE);
        infectadosTxt.setVisibility(View.VISIBLE);
        muertesTxt.setVisibility(View.VISIBLE);
        nuevosCasosTxt.setVisibility(View.VISIBLE);
        recuperadosTxt.setVisibility(View.VISIBLE);
        fechaTxt.setVisibility(View.VISIBLE);
    }
}
