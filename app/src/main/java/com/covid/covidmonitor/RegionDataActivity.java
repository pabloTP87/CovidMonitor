package com.covid.covidmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.covid.covidmonitor.Interface.ChileCoronaApi;
import com.covid.covidmonitor.Model.LastDataRegion;
import com.covid.covidmonitor.Model.RegionUpdate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegionDataActivity extends AppCompatActivity {

    private TextView poblacion;
    private TextView casos;
    private TextView fallecidos;
    private TextView textLoad;

    private CardView tittleCard, card_1, card_2, card_3;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_data);

        TextView regionName = findViewById(R.id.regionName_tittle);
        TextView fecha = findViewById(R.id.txt_fecha);

        poblacion = findViewById(R.id.poblacion);
        casos = findViewById(R.id.casos);
        fallecidos = findViewById(R.id.txt_fallecidos);
        textLoad = findViewById(R.id.text_data_load);
        progressBar = findViewById(R.id.region_data_load);
        tittleCard = findViewById(R.id.header_card);
        card_1 = findViewById(R.id.cardView1);
        card_2 = findViewById(R.id.cardView2);
        card_3 = findViewById(R.id.cardView3);

        regionName.setText(getIntent().getStringExtra("regionName"));
        int regionId = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("regionId")));

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        fecha.setText(df.format(c));

        // se muestra el botón arrow back en el activity
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }

        getRegionData(regionId);
    }

    private void getRegionData (int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v3/latest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChileCoronaApi chileCoronaApi = retrofit.create(ChileCoronaApi.class);

        Call<LastDataRegion> call = chileCoronaApi.getRegionData(id);

        call.enqueue(new Callback<LastDataRegion>() {
            @Override
            public void onResponse(Call<LastDataRegion> call, Response<LastDataRegion> response) {
                if (!response.isSuccessful()){
                    Log.d("fail","código" + response.code());
                }

                assert response.body() != null;
                LastDataRegion dataRegion = response.body();
                String pob = String.valueOf(dataRegion.getRegionInfo().getPopulation());
                poblacion.setText("Pob. " + pob);

                HashMap<String,RegionUpdate> data = dataRegion.getRegionData();

                for (String id : data.keySet()){
                    casos.setText(String.valueOf(data.get(id).getConfirmed()));
                    fallecidos.setText(String.valueOf(data.get(id).getDeaths()));
                }

                progressBar.setVisibility(View.GONE);
                textLoad.setVisibility(View.GONE);
                showView();
            }

            @Override
            public void onFailure(Call<LastDataRegion> call, Throwable t) {
                Log.e("fail","Error" + t.getMessage());
            }
        });
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

    private void showView () {
        tittleCard.setVisibility(View.VISIBLE);
        card_1.setVisibility(View.VISIBLE);
        card_2.setVisibility(View.VISIBLE);
        card_3.setVisibility(View.VISIBLE);
    }
}
