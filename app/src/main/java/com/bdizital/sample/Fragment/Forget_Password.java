package com.bdizital.sample.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bdizital.sample.AppConstants.AppConstants;
import com.bdizital.sample.LogIn;
import com.bdizital.sample.R;
import com.bdizital.sample.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by OP Singh on 05-May-16.
 */
public class Forget_Password extends Fragment implements View.OnClickListener {

    private TextInputLayout input_layout_otp, input_layout_new_password, input_layout_confirm_new_password;
    private EditText input_otp, input_new_password, input_confirm_new_password;
    Button btn_verify_otp, btn_update_password;
    String phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forget_password, container, false);

        Bundle extras = getArguments();
        phone = extras.getString("NUMBER");

        input_layout_otp = (TextInputLayout) rootView.findViewById(R.id.input_layout_otp);
        input_layout_new_password = (TextInputLayout) rootView.findViewById(R.id.input_layout_new_password);
        input_layout_confirm_new_password = (TextInputLayout) rootView.findViewById(R.id.input_layout_confirm_new_password);

        input_otp = (EditText) rootView.findViewById(R.id.input_otp);
        input_new_password = (EditText) rootView.findViewById(R.id.input_new_password);
        input_confirm_new_password = (EditText) rootView.findViewById(R.id.input_confirm_new_password);

        input_otp.addTextChangedListener(new MyTextWatcher(input_otp));
        input_new_password.addTextChangedListener(new MyTextWatcher(input_new_password));
        input_confirm_new_password.addTextChangedListener(new MyTextWatcher(input_confirm_new_password));


        btn_verify_otp = (Button) rootView.findViewById(R.id.btn_verify_otp);
        btn_update_password = (Button) rootView.findViewById(R.id.btn_update_password);
        btn_verify_otp.setOnClickListener(this);
        btn_update_password.setOnClickListener(this);

        return rootView;
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
                case R.id.input_otp:
                    validateOtp();
                    break;
                case R.id.input_new_password:
                    validatePassword();
                    break;
                case R.id.input_confirm_new_password:
                    validatePasswordRepeat();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_verify_otp:
                if (! validateOtp()) {
                    return;
                }
                String otp = input_otp.getText().toString();
                VerifyOtp verifyOtp = new VerifyOtp(otp);
                verifyOtp.execute();
                break;
            case R.id.btn_update_password:
                if (! validatePassword()) {
                    return;
                }
                if (! validatePasswordRepeat()) {
                    return;
                }
                String password  = input_new_password.getText().toString();
                String password_repeat =  input_confirm_new_password.getText().toString();
                UpdatePassword updatePassword = new UpdatePassword(password_repeat);
                updatePassword.execute();
                break;
        }
    }



    public class UpdatePassword extends AsyncTask<Void, Void, String> {
        private String  password;
        private ProgressDialog progressDialog;
        private UpdatePassword(String _password) {
            this.password = _password;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Updating Password...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {

            HashMap<String, String> localHashMap = new HashMap<>();
            localHashMap.put("phoneno", phone);
            localHashMap.put("password", password);

            String url = AppConstants.BASE_URL + AppConstants.NEW_PASSWORD;

            WebServices ws = new WebServices();
            String result = ws.funcGlobal(localHashMap, url);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.e("Update Password Result", result);
                if(progressDialog != null){
                    progressDialog.dismiss();
                }
                try {
                    JSONObject jsonObjectMain = new JSONObject(result);
                    String  status = jsonObjectMain.getString("result");
                    String msg = jsonObjectMain.getString("data");

                    switch(Integer.parseInt(status)){
                        case 1:
                            getActivity().getSupportFragmentManager().beginTransaction().remove(Forget_Password.this).commit();
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                            ((LogIn)getActivity()).btnVisible();
                            break;
                        case 0:
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public class VerifyOtp extends AsyncTask<Void, Void, String> {
        private String  otp;
        private ProgressDialog progressDialog;
        private VerifyOtp(String _otp) {
            this.otp = _otp;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Verifying OTP...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {

            HashMap<String, String> localHashMap = new HashMap<>();
            localHashMap.put("phoneno", phone);
            localHashMap.put("otpno", otp);
            String url = AppConstants.BASE_URL + AppConstants.OTP_VERIFIED;
            WebServices ws = new WebServices();
            String result = ws.funcGlobal(localHashMap, url);
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.e("OTP Result", result);
                if(progressDialog != null){
                    progressDialog.dismiss();
                }
                try {
                    JSONObject jsonObjectMain = new JSONObject(result);
                    String  statusLogin = jsonObjectMain.getString("result");
                    String msgLogin = jsonObjectMain.getString("data");
                    Log.e("Status", statusLogin);
                    Log.e("Message", msgLogin);
                    switch(Integer.parseInt(statusLogin)){
                        case 1:

                            input_layout_otp.setVisibility(View.GONE);
                            input_otp.setVisibility(View.GONE);
                            btn_verify_otp.setVisibility(View.GONE);

                            input_layout_new_password.setVisibility(View.VISIBLE);
                            input_new_password.setVisibility(View.VISIBLE);

                            input_layout_confirm_new_password.setVisibility(View.VISIBLE);
                            input_confirm_new_password.setVisibility(View.VISIBLE);

                            btn_update_password.setVisibility(View.VISIBLE);




                            break;
                        case 0:

                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateOtp() {
        if (input_otp.getText().toString().trim().isEmpty()){
            input_layout_otp.setError("Enter OTP");
            requestFocus(input_otp);
            return false;
        }else{
            input_layout_otp.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (input_new_password.getText().toString().trim().length() == 0) {
            input_layout_new_password.setError("Password should not be empty");
            requestFocus(input_new_password);
            return false;
        } else if(input_new_password.getText().toString().trim().length() < 6){
            input_layout_new_password.setError("Password should be 6 character long");
            requestFocus(input_new_password);
            return false;
        }else{
            input_layout_new_password.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePasswordRepeat() {
        if (!(input_confirm_new_password.getText().toString().equals(input_new_password.getText().toString()))) {
            input_layout_confirm_new_password.setError("Password do not match");
            requestFocus(input_confirm_new_password);
            return false;
        } else {
            input_layout_confirm_new_password.setErrorEnabled(false);
        }
        return true;
    }


}
