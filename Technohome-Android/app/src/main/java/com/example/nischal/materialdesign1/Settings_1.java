package com.example.nischal.materialdesign1;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Settings_1 extends ActionBarActivity {
    private android.support.v7.widget.Toolbar mytoolbar;
    EditText[] settings=new EditText[11];
    String[] send=new String[11];
    Button save;
    public void init(){
        for(int i=0;i<11;i++) {
          settings[i].setText(send[i]);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_1);
        mytoolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settings[0]=(EditText)findViewById(R.id.new_name1);
        settings[1]=(EditText)findViewById(R.id.new_name2);
        settings[2]=(EditText)findViewById(R.id.new_name3);
        settings[3]=(EditText)findViewById(R.id.mac);
        settings[4]=(EditText)findViewById(R.id.voice_on_1);
        settings[5]=(EditText)findViewById(R.id.voice_off_1);
        settings[6]=(EditText)findViewById(R.id.voice_on_2);
        settings[7]=(EditText)findViewById(R.id.voice_off_2);
        settings[8]=(EditText)findViewById(R.id.voice_on_3);
        settings[9]=(EditText)findViewById(R.id.voice_off_3);
        settings[10]=(EditText)findViewById(R.id.url);
        save=(Button)findViewById(R.id.save);
        init();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<11;i++) {
                    send[i] = settings[i].getText().toString();
                }






            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
