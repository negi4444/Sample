package com.bdizital.sample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bdizital.sample.Utility.Utils;
import com.bdizital.sample.AppConstants.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Saleem on 3/7/2016.
 */
public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    EditText et_phone_number;
    EditText et_current_password;
    EditText et_new_password;
    EditText et_new_password_repeat;
    Button btn_update_password;

    private ProgressDialog pDialog;

    public static ChangePassword changePassword;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        changePassword = this;
        utils = new Utils(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Change Password");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_current_password = (EditText) findViewById(R.id.et_currentPassword);
        et_new_password = (EditText)findViewById(R.id.et_newPassword);
        et_new_password_repeat = (EditText)findViewById(R.id.et_newPasswordRepeat);
        btn_update_password = (Button)findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v){

        et_current_password.getText().toString();
        et_new_password.getText().toString();
        et_new_password_repeat.getText().toString();

        if(et_current_password.getText().toString()!=null && !et_current_password.getText().toString().isEmpty()){

        if(et_new_password.getText().toString()!= null && !et_new_password.getText().toString().isEmpty()){

            if(et_new_password.getText().toString().length() >= 6){

                if(et_new_password_repeat.getText().toString().equals(et_new_password.getText().toString())){

                    if(!(et_new_password.getText().toString().equals(et_current_password.getText().toString()))){
            UpdatePassword updatePassword = new UpdatePassword(et_current_password.getText().toString(), et_new_password_repeat.getText().toString());
            updatePassword.execute();

         }else{
            Toast.makeText(this, "Current Password and New Password should not be same.", Toast.LENGTH_LONG).show();
         }
         }else{
         Toast.makeText(this, "Repeat New Password does not match with New Password", Toast.LENGTH_LONG).show();
         }

         }else{
         Toast.makeText(this, "New Password atleast 6 digit long.", Toast.LENGTH_LONG).show();
         }
         }else{
              Toast.makeText(this, "New Password is required", Toast.LENGTH_LONG).show();
         }
         }else{
              Toast.makeText(this, "Current Password is required", Toast.LENGTH_LONG).show();
         }
    }

    public class UpdatePassword extends AsyncTask<Void, Void, String> {
        String  current_password, new_pasword;
        private UpdatePassword(String _current_password, String _new_password) {
            this.current_password = _current_password;
            this.new_pasword = _new_password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ChangePassword.this);
            pDialog.setMessage("Updating Password");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setProgress(0);
            pDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {

            HashMap<String, String> localHashMap = new HashMap<>();
            localHashMap.put("recruiter_id", utils.getRegistrationId());
            localHashMap.put("topassword", current_password);
            localHashMap.put("tnpassword", new_pasword);

            String url = AppConstants.BASE_URL + AppConstants.CHANGE_PASSWORD;
            WebServices ws = new WebServices();
            String result = ws.funcGlobal(localHashMap, url);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
            Log.e("password change", result);

                if(pDialog!= null){
                    pDialog.dismiss();
                }
                JSONObject jsonObjectMain = null;
                try {
                    jsonObjectMain = new JSONObject(result);
                    String status = jsonObjectMain.getString("result");
                    String msg = jsonObjectMain.getString("data");

                    if(status.equals("1")){

                   Toast.makeText(ChangePassword.this, msg, Toast.LENGTH_LONG).show();

                        Intent iTodashboard = new Intent(ChangePassword.this, Dashboard.class);
                        startActivity(iTodashboard);

                    } else{
                        Toast.makeText(ChangePassword.this, msg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
