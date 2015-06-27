package com.example.nischal.materialdesign1;

import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class internet extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mswiperefreshlayout;
    private WebView mwebView;
    private String Url;
    private android.support.v7.widget.Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subactivity);
        mytoolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String url = sharedPref.getString("url", "true");

        Url=url;
        mwebView= (WebView) findViewById(R.id.webViewon);

        mwebView.setWebViewClient(new WebViewClient());
        mwebView.getSettings().setJavaScriptEnabled(true);
        mwebView.loadUrl(Url);
        reload();
        mswiperefreshlayout=(SwipeRefreshLayout)findViewById(R.id.refresh_webview);
        mswiperefreshlayout.setOnRefreshListener(this);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(id==android.R.id.home){

            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        refreshcontent();
    }

    private void refreshcontent() {
        mwebView.loadUrl(Url);
        mswiperefreshlayout.setRefreshing(false);
    }
    public void reload() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                onRefresh();
                reload();
                // mWebview.loadUrl("http://www.google.com");
            }
        }, 20000);
    }



}
