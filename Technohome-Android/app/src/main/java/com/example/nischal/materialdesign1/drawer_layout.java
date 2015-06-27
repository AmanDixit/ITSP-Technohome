package com.example.nischal.materialdesign1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class drawer_layout extends android.support.v4.app.Fragment {

    private RecyclerView mrecyclerview;
    private Toolbar mtoolbar;
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerview;
    private recycleradapter adapter;
    private clicklistener mclicklistner;

    public drawer_layout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_drawer_layout, container, false);
        mrecyclerview = (RecyclerView) layout.findViewById(R.id.recylerview);
        adapter = new recycleradapter(getActivity(), getData());
        mrecyclerview.setAdapter(adapter);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mrecyclerview.addOnItemTouchListener(new recycleviewlisterner(getActivity(), mrecyclerview, new clicklistener() {
            @Override
            public void onClick(View view, int position) {
               // Toast.makeText(getActivity(), "postion" + positi, Toast.LENGTH_SHORT).show();
                switch(position){
                    case 1: startActivity(new Intent(getActivity(),internet.class));
                        break;
                    case 0: startActivity(new Intent(getActivity(),bluetooth.class));
                        break;
                    case 2: startActivity(new Intent(getActivity(),SettingsActivity.class));
                        break;
                    case 4: startActivity(new Intent(getActivity(),about_us.class));
                        break;
                    case 3: startActivity(new Intent(getActivity(),help.class));
                        break;


                }

            }
        }));
        return layout;
    }

    public static List<information> getData() {
        List<information> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_bluetooth_black_24dp, R.drawable.ic_router_black_24dp, R.drawable.ic_settings_applications_black_24dp, R.drawable.ic_help_black_24dp, R.drawable.ic_info_black_24dp};
        String[] titles = {"Bluetooth", "Internet", "Settings", "Help", "About us"};

        for (int i = 0; i < icons.length && i < titles.length; i++) {
            information current = new information();
            current.iconid = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }


    public void setUp(int fragmentid, DrawerLayout drawerlayout, Toolbar toolbar) {
        mtoolbar = toolbar;
        containerview = getActivity().findViewById(fragmentid);
        mDrawerLayout = drawerlayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float nslideoffset;
                nslideoffset = slideOffset / 3;

                mtoolbar.setAlpha(1 - nslideoffset);


            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerview);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferencename, String preferencevalue) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(preferencename, preferencevalue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferencename, String defaultvalue) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedpreferences.getString(preferencename, defaultvalue);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.getBoolean(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    class recycleviewlisterner implements RecyclerView.OnItemTouchListener {

        private GestureDetector mgesturedetector;
        private clicklistener mclicklistner;

        recycleviewlisterner(Context context, final RecyclerView recyclerView, final clicklistener mclicklistener) {
            this.mclicklistner = mclicklistener;

            mgesturedetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    Log.d("TAP", "onSingleTapUp" + e);
                    View child = mrecyclerview.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mclicklistener != null) {
                        mclicklistener.onClick(child, recyclerView.getChildPosition(child));
                    }
                    return true;
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mclicklistner != null && mgesturedetector.onTouchEvent(e)) {
                mclicklistner.onClick(child, rv.getChildPosition(child));
            }


            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {


        }
    }

    public static interface clicklistener {
        public void onClick(View view, int position);
    }
}