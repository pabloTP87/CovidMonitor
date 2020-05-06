package com.covid.covidmonitor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class HistoricalDataCountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_data_country);

        TextView fecha = findViewById(R.id.txt_fecha);
        TextView casos = findViewById(R.id.txt_casos);
        TextView muertes = findViewById(R.id.txt_fallecidos);

        casos.setText(getIntent().getStringExtra("contagiados"));
        muertes.setText(getIntent().getStringExtra("muertes"));
        fecha.setText(getIntent().getStringExtra("fecha"));

    }
}
