package com.parse.starter.zubbycab.view.navigationdrawer.fragment;

/**
 * Created by Ravi on 29/07/15.
 */

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.parse.starter.zubbycab.R;
import com.parse.starter.zubbycab.databinding.FragmentNavigationDrawerBinding;
import com.parse.starter.zubbycab.interfaces.ResultInterface;
import com.parse.starter.zubbycab.utils.ApiKeys;
import com.parse.starter.zubbycab.utils.PreferenceConnector;
import com.parse.starter.zubbycab.view.navigationdrawer.activity.GPSTracker;
import com.parse.starter.zubbycab.view.navigationdrawer.activity.MainActivity;
import com.parse.starter.zubbycab.view.navigationdrawer.adapter.NavigationDrawerAdapter;
import com.parse.starter.zubbycab.view.navigationdrawer.model.NavDrawerItem;
import com.parse.starter.zubbycab.view.registration_login.activity.OtpVarifyActivity;
import com.parse.starter.zubbycab.view.registration_login.presenter.AddRiderDataPresenter;
import com.parse.starter.zubbycab.view.ridermanagement.activity.RiderInformationManagementActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentDrawer extends Fragment implements View.OnClickListener {

    private static String TAG = FragmentDrawer.class.getSimpleName();
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;


    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    private FragmentNavigationDrawerBinding mFragmentNavigationDrawerBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        mFragmentNavigationDrawerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_navigation_drawer, container, false);
        mFragmentNavigationDrawerBinding.userimage.setOnClickListener(this);
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        mFragmentNavigationDrawerBinding.drawerList.setAdapter(adapter);
        mFragmentNavigationDrawerBinding.drawerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentNavigationDrawerBinding.drawerList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                mFragmentNavigationDrawerBinding.drawerList, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return mFragmentNavigationDrawerBinding.getRoot();
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.userimage:
                getActivity().invalidateOptionsMenu();
                mDrawerLayout.closeDrawer(containerView);
                Intent intent = new Intent(getActivity(), RiderInformationManagementActivity.class);
                startActivity(intent);
                break;
        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }


}
