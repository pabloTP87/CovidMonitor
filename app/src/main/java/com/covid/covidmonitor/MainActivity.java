package com.covid.covidmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.covid.covidmonitor.Interface.JsonPlaceholderApi;
import com.covid.covidmonitor.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView jsonTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonTxt = findViewById(R.id.jsonText);

        getPost();
    }

    private void getPost(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
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
                    jsonTxt.setText("Codigo: "+response.code());
                }

                List<Post> postList = response.body();

                assert postList != null;
                for (Post post: postList) {
                    String content = "";
                    content += "userID: "+ post.getUserId()  + "\n";
                    content += "id: "+ post.getId()  + "\n";
                    content += "tittle: "+ post.getTitle()  + "\n";
                    content += "Body: "+ post.getBody()  + "\n\n";

                    jsonTxt.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                    jsonTxt.setText(t.getMessage());
            }
        });
    }
}
