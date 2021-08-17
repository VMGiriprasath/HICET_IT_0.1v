package com.renotech.app.hicetit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.renotech.app.hicetit.Fragment.HomeFragment;
import com.renotech.app.hicetit.Fragment.SettingsFragment;

public class Mainactivity extends AppCompatActivity {
   FrameLayout frameLayout_;
   BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        //find view by id
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        frameLayout_=findViewById(R.id.frame);
        bottomNavigationView=findViewById(R.id.bottomnavigation);
        //without pressing home button commit
    getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               switch (item.getItemId())
               {
                   case R.id.home:
                       setFragment(new HomeFragment());
                       return true;

                   case R.id.settings:
                       setFragment(new SettingsFragment());
                       return true;
               }
                return false;
            }

            private void setFragment(Fragment fragment)
            {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.commit();
            }
        });
    }
}