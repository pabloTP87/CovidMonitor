package com.covid.covidmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

        // se muestra el botón arrow back en el activity
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
    }
    // función para navegar hacia atrás precionando arrow back en el navBar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
