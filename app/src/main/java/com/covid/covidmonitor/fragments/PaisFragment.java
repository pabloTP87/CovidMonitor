package com.covid.covidmonitor.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.covid.covidmonitor.Interface.JsonPlaceholderApi;
import com.covid.covidmonitor.Model.Post;
import com.covid.covidmonitor.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaisFragment extends Fragment {

    public PaisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pais, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    private void getPost(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chile-coronapi.herokuapp.com/api/v2/latest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // llamamos a la interfaz
        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        // Creamos un Call y le asignamos el método de la interfaz
        Call<List<Post>> call = jsonPlaceholderApi.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    //jsonText.setText("Codigo: "+response.code());
                }

                List<Post> postList = response.body();

                assert postList != null;
                for (Post post: postList) {
                    String content = "";
                    content += "confirmados: "+ post.getConfirmed()  + "\n";
                    content += "muertes: "+ post.getDeaths()  + "\n";
                    content += "fecha: "+ post.getLast_updated()  + "\n";
                    content += "nuevos infectados: "+ post.getNew_daily_cases()  + "\n";
                    content += "region: "+ post.getRegion()  + "\n\n";
                    //content += "ubicación: "+ post.getRegionInfo()  + "\n\n";

                    //jsonText.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                //jsonText.setText(t.getMessage());
            }
        });
    }
}
