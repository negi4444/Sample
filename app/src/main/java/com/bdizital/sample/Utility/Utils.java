package com.bdizital.sample.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.bdizital.sample.AppConstants.AppConstants;

/**
 * Created by OP Singh on 22-Jun-16.
 */
public class Utils {

    static Context context;
    public ProgressDialog progressDialog;
    SharedPreferences sharedPref;

    public Utils(Context context) {
        Utils.context = context;
    }

    public void showToast(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(String msg) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    public String getRegistrationId(){
        sharedPref = context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String reg_id = sharedPref.getString(AppConstants.REGISTRATION_ID, "not_available");
        return reg_id;
    }

    public int getLoginStatus(){
        sharedPref = context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        int login_status = sharedPref.getInt(AppConstants.LOGIN_STATUS, 0);
        return login_status;
    }

    public String getName(){
        sharedPref = context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String name = sharedPref.getString(AppConstants.NAME, "not_available");
        return name;
    }

    public String getMobileNumber(){
        sharedPref = context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String number = sharedPref.getString(AppConstants.MOBILE_NUMBER, "not_available");
        return number;
    }

    public String getEmail(){
        sharedPref = context.getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String email = sharedPref.getString(AppConstants.EMAIL, "not_available");
        return email;
    }
}
