package com.covid.covidmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.covid.covidmonitor.Interface.ChileCoronaApi;
import com.covid.covidmonitor.Model.CountryUpdate;
import com.covid.covidmonitor.Model.DateHistoricalCountry;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoricalDataCountryActivity extends AppCompatActivity {

    private TextView historicalData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_data_country);

        historicalData = findViewById(R.id.txtData);

        getHistoricalCountryData();
    }

    protected void getHistoricalCountryData(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v3/historical/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChileCoronaApi chileCoronaApi = retrofit.create(ChileCoronaApi.class);

        Call<HashMap<String,CountryUpdate>> call = chileCoronaApi.getHistoricalCountry();

        call.enqueue(new Callback<HashMap<String,CountryUpdate>>() {
            @Override
            public void onResponse(Call<HashMap<String,CountryUpdate>> call, Response<HashMap<String,CountryUpdate>> response) {

                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(HistoricalDataCountryActivity.this,"Código" + response.code(), Toast.LENGTH_LONG);
                    toast.show();
                }

                HashMap<String, CountryUpdate> data = response.body();

                assert data != null;
                Log.d("datos",data.keySet().toString());
            }

            @Override
            public void onFailure(Call<HashMap<String,CountryUpdate>> call, Throwable t) {
                Toast toast = Toast.makeText(HistoricalDataCountryActivity.this,t.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
