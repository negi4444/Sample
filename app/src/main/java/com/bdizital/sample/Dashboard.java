package com.bdizital.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bdizital.sample.Utility.Utils;

public class Dashboard extends AppCompatActivity {

    private FragmentDrawer drawerFragment;
    String dRegId;
    boolean doubleBackToExitPressedOnce = false;
    TextView txt_appointment;
    Utils utils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        utils = new Utils(this);
        dRegId = utils.getRegistrationId();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("bDizital");
        setSupportActionBar(toolbar);

      //  drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
       // drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

  /*  public class FetchDocterApp extends AsyncTask<Void, Void, String> {
        private String dRegId;
        private ProgressDialog pDialog;
        private FetchDocterApp(String _dRegId) {
            this.dRegId = _dRegId;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           utils.showProgressDialog("Fetching...");
        }
        @Override
        protected String doInBackground(Void... params) {
        HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("", "");
            return new WebServices().fetchDocterApp(null, AppConstants.BASE_URL + AppConstants.GET_LISTING_ALL);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.e("D App", result);
                 if (utils.progressDialog != null) {
                    utils.progressDialog.dismiss();
                }
                JSONObject jsonObjectMain = null;
                try {
                    jsonObjectMain = new JSONObject(result);
                    String status = jsonObjectMain.getString("result");
                    String msg = jsonObjectMain.getString("data");

                    try {
                        JSONArray jsonArray = new JSONArray(msg);
                        if (!(jsonArray.length() == 0)) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectSub = jsonArray.getJSONObject(i);
                                arrayListAppointmentId.add(jsonObjectSub.getString("id"));
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("PATIENT_NAME", jsonObjectSub.getString("patient_name"));
                                hashMap.put("PATIENT_AGE", jsonObjectSub.getString("patient_age"));
                                arrayList.add(hashMap);
                            }

                            Log.e("Array", arrayList.toString());
                            DAppAdapter dashboardAdapter = new DAppAdapter(Dashboard.this, arrayList);
                            listViewDashboard.setAdapter(dashboardAdapter);
                        } else {
                            txt_no_app.setVisibility(View.VISIBLE);
                            listViewDashboard.setVisibility(View.INVISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }  */

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return  true;
    } */

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerFragment.getFragmentManager().getBackStackEntryCount() > 1){
            drawerFragment.getFragmentManager().popBackStack();
        }else if(drawerFragment.getFragmentManager().getBackStackEntryCount() == 1){
            drawerFragment.getFragmentManager().popBackStack();
            txt_appointment.setText("All Apppointments");

        }
        else if(drawerFragment.mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerFragment.mDrawerLayout.closeDrawers();
        }else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }
}
