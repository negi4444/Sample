package com.bdizital.sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bdizital.sample.AppConstants.AppConstants;
import com.bdizital.sample.Utility.Utils;

public class Splash extends AppCompatActivity {

    private boolean isOnline;
    private LinearLayout linearLayout;
    Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        utils = new Utils(this);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        checkInternetConnection();

        if (isOnline == true) {

            Thread timer = new Thread() {
                public void run() {

                    try {
                        sleep(2000);

                        if (utils.getLoginStatus() == 1) {
                            Intent i = new Intent(Splash.this, Dashboard.class);
                            startActivity(i);
                            finish();

                        } else if (utils.getLoginStatus() == 0) {
                            Intent inR = new Intent(Splash.this, Dashboard.class);
                            startActivity(inR);
                            finish();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.start();

        }else if(isOnline == false){

            Snackbar snackbar = Snackbar
                    .make(linearLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            startActivity(getIntent());
                        }
                    });

            snackbar.setActionTextColor(Color.WHITE);

            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.RED);
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    private void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            isOnline = true;
        } else {
            isOnline = false;
        }
    }
}
