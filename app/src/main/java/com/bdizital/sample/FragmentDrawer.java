package com.bdizital.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bdizital.sample.AppConstants.AppConstants;

public class FragmentDrawer extends Fragment implements AdapterView.OnItemClickListener {

    public DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    String[] itemname = {
            "LogOut",
            // "Rejected Appointments",
    };

    Integer[] imgid = {
            R.drawable.logout,
          //  R.drawable.ic_launchernew,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        ListView listView = (ListView) layout.findViewById(R.id.listViewNd);
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemname, imgid);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return layout;
    }

    public class CustomListAdapter extends BaseAdapter {
        private final Activity context;
        private final String[] itemname;
        private final Integer[] imgid;
        private LayoutInflater inflater;

        public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
            this.context = context;
            this.itemname = itemname;
            this.imgid = imgid;
        }

        @Override
        public int getCount() {
            return itemname.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View view, ViewGroup parent) {

            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (view == null) {
                view = inflater.inflate(R.layout.fragment_drawer_list_customized, null);
            }

            TextView txtTitle = (TextView) view.findViewById(R.id.Itemname);
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);

            txtTitle.setText(itemname[position]);
            imageView.setImageResource(imgid[position]);

            return view;

        }

        ;
    }

    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
               // toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case (0):

                mDrawerLayout.closeDrawers();
                SharedPreferences sharedPrefLoginStatusD = getActivity().getSharedPreferences(AppConstants.SHARED_PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editorLoginStatusD = sharedPrefLoginStatusD.edit();
                editorLoginStatusD.clear();
                editorLoginStatusD.commit();
                Intent i = new Intent(getActivity(), LogIn.class);
                startActivity(i);
                getActivity().finish();

                break;
            case (1):
                mDrawerLayout.closeDrawers();


                break;

        }
    }
}
