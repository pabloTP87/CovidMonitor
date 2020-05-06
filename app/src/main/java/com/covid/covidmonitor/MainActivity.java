package com.covid.covidmonitor;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.covid.covidmonitor.fragments.PaisFragment;
import com.covid.covidmonitor.fragments.RegionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public FragmentManager manager = getSupportFragmentManager();
    public RegionFragment regionFragment = new RegionFragment();
    public PaisFragment paisFragment = new PaisFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createFragment(paisFragment);

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

            switch (item.getItemId()) {
                case R.id.menu_pais:
                    /*
                    utilizamos hide y show para mantener el estado del fragement con sus datos, para no
                    realizar una nueva carga al desplazarnos entre ellos.
                    */
                    if (paisFragment.isAdded()){
                        manager.beginTransaction()
                                .hide(regionFragment)
                                .show(paisFragment)
                                .commit();
                    }else {
                        manager.beginTransaction()
                                .hide(regionFragment)
                                .add(R.id.main_fragment_container,paisFragment)
                                .commit();
                    }
                    return true;
                case R.id.menu_region:

                    if (regionFragment.isAdded()){
                        manager.beginTransaction()
                                .hide(paisFragment)
                                .show(regionFragment)
                                .commit();
                    }else {
                        manager.beginTransaction()
                                .hide(paisFragment)
                                .add(R.id.main_fragment_container,regionFragment)
                                .commit();
                    }
                    return true;
            }
            return false;
        };

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    public void createFragment(Fragment fragment){
        manager.beginTransaction()
                .replace(R.id.main_fragment_container,fragment)
                .commit();
    }
}
