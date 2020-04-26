package com.covid.covidmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.covid.covidmonitor.Interface.ChileCoronaApi;
import com.covid.covidmonitor.Model.CountryUpdate;
import com.covid.covidmonitor.Model.DateHistoricalCountry;
import com.covid.covidmonitor.fragments.PaisFragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoricalDataCountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_data_country);

        TextView historicalData = findViewById(R.id.txtConfirmados);
        TextView muertes = findViewById(R.id.txtMuertes);
        TextView recuperados = findViewById(R.id.txtRecuperados);

        historicalData.setText(getIntent().getStringExtra("contagiados"));
        muertes.setText(getIntent().getStringExtra("muertes"));
        recuperados.setText(getIntent().getStringExtra("recuperados"));

    }
}
