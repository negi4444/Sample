package com.bdizital.sample.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bdizital.sample.AppConstants.AppConstants;
import com.bdizital.sample.Dashboard;
import com.bdizital.sample.R;
import com.bdizital.sample.Utility.Utils;
import com.bdizital.sample.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by OP Singh on 11-Jun-16.
 */
public class SignUp extends Fragment implements View.OnClickListener {

    OnFragSelected mCallback;

    private TextInputLayout inputLayoutEmail, inputLayoutPassword, inputLayoutPasswordRepeat, inputLayoutFullName, inputLayoutPhone;
    private EditText inputEmail, inputPassword, inputPasswordRepeat,inputFullName, inputPhone;
    String regexStr = "^[0-9]{10}$";
    Button btn_signup;

    View rootView;

    static String statusRegister;
    String msgRegister;
    ProgressBar pb_signup;
    TextView txt_signup;
    Utils utils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sign_up, container, false);

        utils = new Utils(getActivity());

        inputLayoutEmail = (TextInputLayout) rootView.findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);
        inputLayoutPasswordRepeat = (TextInputLayout) rootView.findViewById(R.id.input_layout_password_repeat);
        inputLayoutFullName = (TextInputLayout) rootView.findViewById(R.id.input_layout_full_name);
        inputLayoutPhone = (TextInputLayout) rootView.findViewById(R.id.input_layout_phone);

        inputEmail = (EditText) rootView.findViewById(R.id.input_email);
        inputPassword = (EditText) rootView.findViewById(R.id.input_password);
        inputPasswordRepeat = (EditText) rootView.findViewById(R.id.input_password_repeat);
        inputFullName = (EditText) rootView.findViewById(R.id.input_full_name);
        inputPhone = (EditText) rootView.findViewById(R.id.input_phone);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputPasswordRepeat.addTextChangedListener(new MyTextWatcher(inputPasswordRepeat));
        inputFullName.addTextChangedListener(new MyTextWatcher(inputFullName));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));

        btn_signup = (Button) rootView.findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        pb_signup = (ProgressBar) rootView.findViewById(R.id.pb_signup);
        txt_signup = (TextView) rootView.findViewById(R.id.txt_signup);

        return rootView;
    }



    @Override
    public void onClick(View v) {
        if (!validateFullName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validatePasswordRepeat()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }

        String fullName = inputFullName.getText().toString();
        String email  = inputEmail.getText().toString();
        String password = inputPasswordRepeat.getText().toString();
        String number = inputPhone.getText().toString();

        SignUpUser signUpUser = new SignUpUser(email, password, fullName, number);
        signUpUser.execute();
    }

    public class SignUpUser extends AsyncTask<Void, Void, String> {
        String email, password, fullName, number;
        private ProgressDialog pDialog;
        private SignUpUser( String _email, String _password, String _fullName, String _number) {
            this.email = _email;
            this.password = _password;
            this.fullName = _fullName;
            this.number = _number;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Registering...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            HashMap<String, String> localHashMap = new HashMap<>();
            localHashMap.put("name", fullName);
            localHashMap.put("email", email);
            localHashMap.put("password", password);
            localHashMap.put("mob_no", number);

            String url = AppConstants.BASE_URL + AppConstants.REGISTER;
            WebServices ws = new WebServices();
            String result = ws.funcGlobal(localHashMap, url);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.e("Register", result);
                  if (pDialog != null) {
                    pDialog.dismiss();
                }
                try {
                    JSONObject jsonObjectMain = new JSONObject(result);
                    statusRegister = jsonObjectMain.getString("result");
                    msgRegister = jsonObjectMain.getString("data");
                    if (statusRegister.equals("1")) {

                        JSONArray jsonArray = new JSONArray(msgRegister);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectSub = jsonArray.getJSONObject(i);

                            SharedPreferences sharedPref = getActivity().getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt(AppConstants.LOGIN_STATUS, 1);
                            editor.putString(AppConstants.REGISTRATION_ID, jsonObjectSub.getString(AppConstants.REGISTRATION_ID));
                            editor.putString(AppConstants.NAME, jsonObjectSub.getString(AppConstants.NAME));
                            editor.putString(AppConstants.MOBILE_NUMBER, jsonObjectSub.getString(AppConstants.MOBILE_NUMBER));
                            editor.putString(AppConstants.EMAIL, jsonObjectSub.getString(AppConstants.EMAIL));
                            editor.apply();

                            Intent ii = new Intent(getActivity(), Dashboard.class);
                            startActivity(ii);
                            getActivity().finish();
                        }

                    } else {
                        utils.showToast(msgRegister);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_password_repeat:
                    validatePasswordRepeat();
                    break;
                case R.id.input_full_name:
                    validateFullName();
                    break;
                case R.id.input_phone:
                    validatePhone();
                    break;

            }
        }
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Invalid Email");
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().length() == 0) {
            inputLayoutPassword.setError("Password should not be empty");
            requestFocus(inputPassword);
            return false;
        } else if(inputPassword.getText().toString().trim().length() < 6){
            inputLayoutPassword.setError("Password should be 6 character long");
            requestFocus(inputPassword);
            return false;
        }else{
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePasswordRepeat() {
        if (!(inputPasswordRepeat.getText().toString().equals(inputPassword.getText().toString()))) {
            inputLayoutPasswordRepeat.setError("Password do not match");
            requestFocus(inputPasswordRepeat);
            return false;
        } else {
            inputLayoutPasswordRepeat.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateFullName() {
        if (inputFullName.getText().toString().trim().isEmpty()) {
            inputLayoutFullName.setError("Full Name");
            requestFocus(inputFullName);
            return false;
        } else {
            inputLayoutFullName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhone() {
        if (inputPhone.getText().toString().trim().isEmpty()){
            inputLayoutPhone.setError("Number should not be empty");
            requestFocus(inputPhone);
            return false;
        } else if(!inputPhone.getText().toString().trim().matches(regexStr)){
            inputLayoutPhone.setError("Invalid Number");
            requestFocus(inputPhone);
            return false;
        }else{
            inputLayoutPhone.setErrorEnabled(false);
        }
        return true;
    }

    public interface OnFragSelected {
        public void OnFragSelected(int id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnFragSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement OnFragSelected interface..");
        }
    }
}
