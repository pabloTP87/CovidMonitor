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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaisFragment extends Fragment {

    private CardView headerCard, cardView1, cardView2;
    private TextView footerText;
    private TextView textCalendar;
    private TextView textDataLoad;
    private TextView infectadosTxt;
    private TextView muertesTxt;
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
        fechaTxt = view.findViewById(R.id.date_update);
        nuevosCasosTxt = view.findViewById(R.id.nuevos_casos);
        dataLoad = view.findViewById(R.id.country_data_load);
        showCalendar = view.findViewById(R.id.date_picker);
        headerCard = view.findViewById(R.id.header_card);
        cardView1 = view.findViewById(R.id.cardView1);
        cardView2 = view.findViewById(R.id.cardView2);
        textCalendar = view.findViewById(R.id.text_calendar);
        textDataLoad = view.findViewById(R.id.text_data_load);
        footerText = view.findViewById(R.id.footer_text);

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
        getNewDailyCases();
        // Inflate the layout for this fragment
        return view;
    }

    // función para obtener los datos actualizados de covid - 19 en chile
    private void getCountryData () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v3/latest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChileCoronaApi chileCoronaApi = retrofit.create(ChileCoronaApi.class);

        Call<CountryUpdate> call = chileCoronaApi.getCountryUpdate();

        call.enqueue(new Callback<CountryUpdate>() {
            @Override
            public void onResponse(Call<CountryUpdate> call, Response<CountryUpdate> response) {
                if (!response.isSuccessful()){
                    Log.e("fail","Fallo: " + response.code());
                }

                CountryUpdate countryUpdate = response.body();

                assert countryUpdate != null;
                String fecha = "Fecha de actualización \n" + countryUpdate.getDay();
                String infectados = "Casos confirmados \n" + countryUpdate.getConfirmed();
                String fallecidos = "Fallecidos: " + countryUpdate.getDeaths();

                fechaTxt.setText(fecha);
                infectadosTxt.setText(infectados);
                muertesTxt.setText(fallecidos);

                // Sacamos la visibilidad del progress bar
                dataLoad.setVisibility(View.GONE);
                textDataLoad.setVisibility(View.GONE);

                // Mostramos las vistas con los datos
                showView();

            }

            @Override
            public void onFailure(Call<CountryUpdate> call, Throwable t) {
                Log.e("error","error: " + t.getMessage());
            }
        });
    }

    // funcion para consultar los datos historicos nivel país y calcular los nuevos casos con la función newCases()
    private void getNewDailyCases () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v3/historical/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // llamamos a la interfaz
        ChileCoronaApi chileCoronaApi = retrofit.create(ChileCoronaApi.class);
        // Creamos un Call y le asignamos el método de la interfaz
        Call<HashMap<String, CountryUpdate>> call = chileCoronaApi.getHistoricalCountry();

        call.enqueue(new Callback<HashMap<String, CountryUpdate>>() {
            @Override
            public void onResponse(Call<HashMap<String, CountryUpdate>> call, Response<HashMap<String, CountryUpdate>> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getActivity(),"Código" + response.code(), Toast.LENGTH_LONG);
                    toast.show();
                }

                HashMap <String, CountryUpdate> countryUpdate = response.body();

                assert countryUpdate != null;
                newCases(countryUpdate);
            }

            @Override
            public void onFailure(Call<HashMap<String, CountryUpdate>> call, Throwable t) {
                Log.e("Fail", "error: " + t.getMessage());
            }
        });
    }

    // función para obtener los datos históricos de contagios nivel país desde api
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
            }

            @Override
            public void onFailure(Call<HashMap<String,CountryUpdate>> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    // función para mostrar las vistas despues de esconder el progress bar
    private void showView(){
        headerCard.setVisibility(View.VISIBLE);
        cardView1.setVisibility(View.VISIBLE);
        cardView2.setVisibility(View.VISIBLE);
        textCalendar.setVisibility(View.VISIBLE);
        showCalendar.setVisibility(View.VISIBLE);
        footerText.setVisibility(View.VISIBLE);
    }

    // función para caclular nos nuevos casos diarios
    private void newCases (HashMap<String , CountryUpdate> data) {

        // declaramos un array list donde guardamos todos los casos confirmados a la fecha
        ArrayList<Integer> confirmedList = new ArrayList<>();

        // recorremos las key del hashmap y agregamos los confirmados históricos al array
        for(String id : data.keySet()){
            confirmedList.add(Integer.valueOf(data.get(id).getConfirmed()));
        }
        // se ordena el array de menor a mayor para reflejar los datos hasta la actualidad
        Collections.sort(confirmedList);
        // obtenemos el ultimo y penúltimo dato que corresponde a los confirmados del dia de hoy y ayer y los restamos
        String newCases = String.valueOf(confirmedList.get(confirmedList.size()-1) - confirmedList.get(confirmedList.size()-2));
        // asignamos el resultado al Texview de nuevos casos
        String nuevosCasos = "Nuevos casos: " + newCases;
        nuevosCasosTxt.setText(nuevosCasos);
    }

}
