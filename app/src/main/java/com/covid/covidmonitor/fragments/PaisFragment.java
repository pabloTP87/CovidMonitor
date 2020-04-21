package com.covid.covidmonitor.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.covid.covidmonitor.HistoricalDataCountryActivity;
import com.covid.covidmonitor.Interface.ChileCoronaApi;
import com.covid.covidmonitor.Model.CountryUpdate;
import com.covid.covidmonitor.R;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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
    private TextView nuevosCasosTxt;
    private ProgressBar dataLoad;
    private Calendar calendar;
    private DatePickerDialog picker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pais, container, false);

        infectadosTxt = view.findViewById(R.id.infectados);
        muertesTxt = view.findViewById(R.id.muertes);
        recuperadosTxt = view.findViewById(R.id.recuperados);
        fechaTxt = view.findViewById(R.id.date_update);
        nuevosCasosTxt = view.findViewById(R.id.nuevos_casos);

        dataLoad = view.findViewById(R.id.country_data_load);

        Button showCalendar = view.findViewById(R.id.date_picker);

        showCalendar.setOnClickListener(v -> {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d("fecha", dayOfMonth+"/"+month+"/"+year);
                    }
                },day,month,year);
                picker.show();
        });

        getCountryData();
        // Inflate the layout for this fragment
        return view;
    }

    private void getCountryData(){
        dataLoad.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v3/latest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // llamamos a la interfaz
        ChileCoronaApi chileCoronaApi = retrofit.create(ChileCoronaApi.class);
        // Creamos un Call y le asignamos el método de la interfaz
        Call<CountryUpdate> call = chileCoronaApi.getCountryUpdate();

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

        infectadosTxt.setVisibility(View.VISIBLE);
        muertesTxt.setVisibility(View.VISIBLE);
        nuevosCasosTxt.setVisibility(View.VISIBLE);
        recuperadosTxt.setVisibility(View.VISIBLE);
        fechaTxt.setVisibility(View.VISIBLE);
    }
}
