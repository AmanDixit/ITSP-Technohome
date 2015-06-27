package com.example.nischal.materialdesign1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;


public class MainActivity extends ActionBarActivity {
    Switch laststate1;
    Switch laststate2;
    Switch laststate3;


    private android.support.v7.widget.Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_appbar);
        mytoolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawer_layout drawerfragment = (drawer_layout)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer_layout);

        drawerfragment.setUp(R.id.fragment_drawer_layout,(DrawerLayout)findViewById(R.id.main_page),mytoolbar);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
