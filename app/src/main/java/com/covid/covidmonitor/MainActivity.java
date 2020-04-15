package com.covid.covidmonitor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.covid.covidmonitor.fragments.PaisFragment;

public class MainActivity extends AppCompatActivity {

    public FragmentManager manager = getSupportFragmentManager();
    public Fragment paisFragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paisFragment = new PaisFragment();
        createFragment(paisFragment);
    }

    public void createFragment(Fragment fragment){
         manager.beginTransaction()
                 .replace(R.id.main_fragment_container,fragment)
                 .commit();
    }
}
