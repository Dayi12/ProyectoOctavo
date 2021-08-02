package com.example.proyectooctavo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    //TabLayout
    TabLayout guiTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
       // Bundle bundle = this.getIntent().getExtras();
       guiTabs = findViewById(R.id.tabLayout);

        //Aparentemente, setOnClickListener () no es compatible con TabItems o
        // TabLayouts. En su lugar, utilicé la siguiente solución para crear
        // una función de clic para pestañas.

       guiTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0: Toast.makeText(Settings.this, "Tab 1", Toast.LENGTH_LONG).show();
                        goProfile();
                        break;
                    case 1: Toast.makeText(Settings.this, "Tab 2", Toast.LENGTH_LONG).show();
                        goDashboard();
                        break;
                    case 2: Toast.makeText(Settings.this, "Tab 3", Toast.LENGTH_LONG).show();

                        break;
                    case 3:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(Settings.this,
                                "Sesion cerrada",
                                Toast.LENGTH_SHORT).show();
                        gologin();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void gologin() {
        Intent i = new Intent(this, MainActivity.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private void goDashboard() {
        Intent i = new Intent(this, Dashboard.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private void goProfile() {
        Intent i = new Intent(this, Profile.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}