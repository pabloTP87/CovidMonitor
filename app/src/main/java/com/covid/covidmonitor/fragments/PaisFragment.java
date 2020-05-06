package com.covid.covidmonitor.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.covid.covidmonitor.HistoricalDataCountryActivity;
import com.covid.covidmonitor.Interface.ChileCoronaApi;
import com.covid.covidmonitor.Model.CountryUpdate;
import com.covid.covidmonitor.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaisFragment extends Fragment {

    private CardView headerCard, cardView1, cardView2, cardView3;
    private TextView textCalendar;
    private TextView textDataLoad;
    private TextView infectadosTxt;
    private TextView muertesTxt;
    private TextView recuperadosTxt;
    private TextView fechaTxt;
    private TextView nuevosCasosTxt;
    private ProgressBar dataLoad;
    private FloatingActionButton showCalendar;
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
        showCalendar = view.findViewById(R.id.date_picker);
        headerCard = view.findViewById(R.id.header_card);
        cardView1 = view.findViewById(R.id.cardView1);
        cardView2 = view.findViewById(R.id.cardView2);
        cardView3 = view.findViewById(R.id.cardView3);
        textCalendar = view.findViewById(R.id.text_calendar);
        textDataLoad = view.findViewById(R.id.text_data_load);

        showCalendar.setOnClickListener(v -> {
                calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));


                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String calendarDate;

                        if(dayOfMonth <= 9 && month > 8){
                            calendarDate = (month+1)+ "/" + "0" + dayOfMonth  + "/" + year;
                            sendHistoricalCountryData(calendarDate);

                        }else if (month <= 8 && dayOfMonth <= 9){
                            calendarDate = "0" + (month+1)+ "/" + "0" + dayOfMonth + "/" + year;
                            sendHistoricalCountryData(calendarDate);

                        }else if (month <= 8 && dayOfMonth > 9){
                            calendarDate = "0" + (month+1) + "/" + dayOfMonth + "/" +  year;
                            sendHistoricalCountryData(calendarDate);

                        }else {
                            calendarDate = (month+1) + "/" +dayOfMonth + "/" +  year;
                            sendHistoricalCountryData(calendarDate);
                        }
                    }
                },day,month,year);
                picker.updateDate(year,month,day);
                picker.show();

        });

        getCountryData();
        // Inflate the layout for this fragment
        return view;
    }

    private void getCountryData(){

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

                String infectadosLabel = "Casos confirmados " + "\n" + countryUpdate.getConfirmed();
                nuevosCasosTxt.setText("Nuevos casos: +300");
                String muertesLabel = "Cantidad de fallecidos: " + countryUpdate.getDeaths();
                String recuperadosLabel = "Cantidad de recuperados: " + countryUpdate.getRecovered();
                String fechaLabel = "Fecha de actualización " + "\n" + countryUpdate.getDay();

                infectadosTxt.setText(infectadosLabel);
                muertesTxt.setText(muertesLabel);
                recuperadosTxt.setText(recuperadosLabel);
                fechaTxt.setText(fechaLabel);

                dataLoad.setVisibility(View.GONE);
                textDataLoad.setVisibility(View.GONE);

                showView();
            }

            @Override
            public void onFailure(Call<CountryUpdate> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void sendHistoricalCountryData(String pickerDate){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v3/historical/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChileCoronaApi chileCoronaApi = retrofit.create(ChileCoronaApi.class);

        Call<HashMap<String,CountryUpdate>> call = chileCoronaApi.getHistoricalCountry();

        call.enqueue(new Callback<HashMap<String,CountryUpdate>>() {
            @Override
            public void onResponse(Call<HashMap<String,CountryUpdate>> call, Response<HashMap<String,CountryUpdate>> response) {

                boolean check = false;

                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getActivity(),"Código" + response.code(), Toast.LENGTH_LONG);
                    toast.show();
                }

                HashMap<String, CountryUpdate> data = response.body();

                assert data != null;

                for(String fecha : data.keySet()){
                    if(fecha.equals(pickerDate)){
                        check = true;
                        break;
                    }
                }

                if (check){

                    Intent intent = new Intent(getActivity(),HistoricalDataCountryActivity.class);
                    intent.putExtra("contagiados", Objects.requireNonNull(data.get(pickerDate)).getConfirmed());
                    intent.putExtra("muertes", data.get(pickerDate).getDeaths());
                    intent.putExtra("fecha",pickerDate);
                    startActivity(intent);
                    Log.d("datos", Objects.requireNonNull(data.get(pickerDate)).getDeaths());
                }else {
                    Toast toast = Toast.makeText(getActivity(),"No existen datos en esta fecha",Toast.LENGTH_SHORT);
                    toast.show();
                    Log.d("check","false");
                }
                //assert data != null;
                //Log.d("datos",data.get("04/21/2020").getConfirmed());
            }

            @Override
            public void onFailure(Call<HashMap<String,CountryUpdate>> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void showView(){
        headerCard.setVisibility(View.VISIBLE);
        cardView1.setVisibility(View.VISIBLE);
        cardView2.setVisibility(View.VISIBLE);
        cardView3.setVisibility(View.VISIBLE);
        textCalendar.setVisibility(View.VISIBLE);
        showCalendar.setVisibility(View.VISIBLE);
    }
}
