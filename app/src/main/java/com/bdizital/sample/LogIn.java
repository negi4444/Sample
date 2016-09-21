package com.bdizital.sample;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bdizital.sample.AppConstants.AppConstants;
import com.bdizital.sample.Utility.Utils;
import com.bdizital.sample.Fragment.Forget_Password;
import com.bdizital.sample.Fragment.SignUp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by OP Singh on 11-Jun-16.
 */
public class LogIn extends AppCompatActivity implements View.OnClickListener, SignUp.OnFragSelected {

    private TextInputLayout inputLayoutEmailLogin, inputLayoutPasswordLogin;
    private EditText inputEmailLogin, inputPasswordLogin;

    Button btn_goto_signup, btn_forgot_password;
    Button btn_login;
    String regexStr = "^[0-9]{10}$";
    static String statusLogin;
    Dialog dialogEnterNumber;
    TextInputLayout input_layout_number_enter;
    EditText input_number_enter;
    String msgLogin;
    Utils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        utils = new Utils(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sample");
        setSupportActionBar(toolbar);

        inputLayoutEmailLogin = (TextInputLayout) findViewById(R.id.input_layout_email_login);
        inputLayoutPasswordLogin = (TextInputLayout) findViewById(R.id.input_layout_password_login);
        inputEmailLogin = (EditText) findViewById(R.id.input_email_login);
        inputPasswordLogin = (EditText) findViewById(R.id.input_password_login);
        inputEmailLogin.addTextChangedListener(new MyTextWatcher(inputEmailLogin));
        inputPasswordLogin.addTextChangedListener(new MyTextWatcher(inputPasswordLogin));

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_goto_signup = (Button) findViewById(R.id.btn_goto_signup);
        btn_goto_signup.setOnClickListener(this);
        btn_forgot_password = (Button) findViewById(R.id.btn_forgot_password);
        btn_forgot_password.setOnClickListener(this);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_goto_signup:
                OnFragSelected(800);
                break;

            case R.id.btn_login:

                if (!validatePhone()) {
                    return;
                }

                if (!validatePassword()) {
                    return;
                }

                String email = inputEmailLogin.getText().toString();
                String password = inputPasswordLogin.getText().toString();

                LoginUser loginUser = new LoginUser(email, password);
                loginUser.execute();

                break;

            case R.id.btn_forgot_password:

                dialogEnterNumber = new Dialog(this);
                // dialogSignUp.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                View viewEnterNumber = getLayoutInflater().inflate(R.layout.view_enter_number, null);
                dialogEnterNumber.setContentView(viewEnterNumber);
                dialogEnterNumber.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogEnterNumber.show();

                input_layout_number_enter = (TextInputLayout) viewEnterNumber.findViewById(R.id.input_layout_number_enter);
                input_number_enter = (EditText) viewEnterNumber.findViewById(R.id.input_number_enter);
                input_number_enter.addTextChangedListener(new MyTextWatcher(input_number_enter));

                Button btn_ok = (Button) viewEnterNumber.findViewById(R.id.btn_number_enter);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!validatePhoneForOtp()) {
                            return;
                        }
                        dialogEnterNumber.dismiss();
                        String number = input_number_enter.getText().toString();
                        SendNumberForOtp sendNumberForOtp = new SendNumberForOtp(number);
                        sendNumberForOtp.execute();

                    }
                });
                break;

        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email_login:
                    validatePhone();
                    break;
                case R.id.input_password_login:
                    validatePassword();
                    break;
                case R.id.input_number_enter:
                    validatePhoneForOtp();
                    break;
            }
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePhone() {
        if (inputEmailLogin.getText().toString().trim().isEmpty()){
            inputLayoutEmailLogin.setError("Number should not be empty");
            requestFocus(inputEmailLogin);
            return false;
        } else if(!inputEmailLogin.getText().toString().trim().matches(regexStr)){
            inputLayoutEmailLogin.setError("Invalid Number");
            requestFocus(inputEmailLogin);
            return false;
        }else{
            inputLayoutEmailLogin.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (inputPasswordLogin.getText().toString().trim().length() == 0) {
            inputLayoutPasswordLogin.setError("Password should not be empty");
            requestFocus(inputPasswordLogin);
            return false;
        } else{
            inputLayoutPasswordLogin.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhoneForOtp() {
        if (input_number_enter.getText().toString().trim().isEmpty()){
            input_layout_number_enter.setError("Number should not be empty");
            requestFocus(input_number_enter);
            return false;
        } else if(!input_number_enter.getText().toString().trim().matches(regexStr)){
            input_layout_number_enter.setError("Invalid Number");
            requestFocus(input_number_enter);
            return false;
        }else{
            input_layout_number_enter.setErrorEnabled(false);
        }
        return true;
    }

    @Override
    public void OnFragSelected(int id) {

        SignUp signUp = new SignUp();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.login_signup, signUp);
        transaction.commit();
        btn_login.setVisibility(View.GONE);
    }
    public class LoginUser extends AsyncTask<Void, Void, String> {
        private String phone_number, password;
        private LoginUser(String _phone_number, String _password) {
            this.phone_number = _phone_number;
            this.password = _password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            utils.showProgressDialog("Logging...");

        }
        @Override
        protected String doInBackground(Void... params) {

            HashMap<String, String> localHashMap = new HashMap<>();
            localHashMap.put("phoneno", phone_number);
            localHashMap.put("password", password);

            String url = AppConstants.BASE_URL + AppConstants.LOGIN;
            WebServices ws = new WebServices();
            String result = ws.funcGlobal(localHashMap, url);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.e("Login", result);
                if (utils.progressDialog != null) {
                    utils.progressDialog.dismiss();
                }

                try {
                    JSONObject jsonObjectMain = new JSONObject(result);
                    statusLogin = jsonObjectMain.getString("result");
                    msgLogin = jsonObjectMain.getString("data");

                    switch (Integer.parseInt(statusLogin)) {
                        case 1:
                            JSONArray jsonArray = new JSONArray(msgLogin);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectSub = jsonArray.getJSONObject(i);

                                SharedPreferences sharedPref = getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt(AppConstants.LOGIN_STATUS, 1);
                                editor.putString(AppConstants.REGISTRATION_ID, jsonObjectSub.getString(AppConstants.REGISTRATION_ID));
                                editor.putString(AppConstants.NAME, jsonObjectSub.getString(AppConstants.NAME));
                                editor.putString(AppConstants.MOBILE_NUMBER, jsonObjectSub.getString(AppConstants.MOBILE_NUMBER));
                                editor.putString(AppConstants.EMAIL, jsonObjectSub.getString(AppConstants.EMAIL));
                                editor.apply();

                                Intent ii = new Intent(LogIn.this, Dashboard.class);
                                startActivity(ii);
                                finish();
                            }

                            break;
                        case 0:
                            utils.showToast(msgLogin);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class SendNumberForOtp extends AsyncTask<Void, Void, String> {
        private String  phone_number;
        private SendNumberForOtp(String _phone_number) {
            this.phone_number = _phone_number;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            utils.showProgressDialog("Fetching OTP...");
        }
        @Override
        protected String doInBackground(Void... params) {

            HashMap<String, String> localHashMap = new HashMap<>();
            localHashMap.put("phoneno", phone_number);
            String url = AppConstants.BASE_URL + AppConstants.FORGET_PASSWORD;
            WebServices ws = new WebServices();
            String result = ws.funcGlobal(localHashMap, url);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.e("Number Sent Success", result);
                if (utils.progressDialog != null) {
                    utils.progressDialog.dismiss();
                }

                try {
                    JSONObject jsonObjectMain = new JSONObject(result);
                    String  statusLogin = jsonObjectMain.getString("result");
                    String msgLogin = jsonObjectMain.getString("data");

                    switch(Integer.parseInt(statusLogin)){
                        case 1:

                            Bundle data = new Bundle();
                            data.putString("NUMBER", phone_number);

                            Forget_Password forget_password = new Forget_Password();
                            forget_password.setArguments(data);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.login_signup, forget_password);
                            transaction.commit();
                            btn_login.setVisibility(View.GONE);

                            break;
                        case 0:
                            utils.showToast(msgLogin);
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            btn_login.setVisibility(View.VISIBLE);
        }else{
            this.finish();
        }
    }

    public void btnVisible(){
        btn_login.setVisibility(View.VISIBLE);
    }

}
