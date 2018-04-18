package com.parse.starter.zubbycab.view.registration_login.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.starter.zubbycab.R;
import com.parse.starter.zubbycab.databinding.ActivitySplashBinding;
import com.parse.starter.zubbycab.databinding.ActivityStartBinding;
import com.parse.starter.zubbycab.utils.BaseActivity;

public class StartActivity extends BaseActivity {
    private ActivityStartBinding mActivitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        mActivitySplashBinding.startExploring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });
       // Log.e("api", "" + apiService.hashCode());
         Log.e("apirservice", "" + mApiService.hashCode());
//        if (mCheckInternet) {
//            Toast.makeText(context, "netConnected", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(context, "CheckNet", Toast.LENGTH_LONG).show();
//        }
    }
}
