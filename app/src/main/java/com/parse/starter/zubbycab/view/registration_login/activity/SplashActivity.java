package com.parse.starter.zubbycab.view.registration_login.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.parse.starter.zubbycab.R;
import com.parse.starter.zubbycab.databinding.ActivitySplashBinding;
import com.parse.starter.zubbycab.interfaces.ResultInterface;
import com.parse.starter.zubbycab.interfaces.ResultInterface1;
import com.parse.starter.zubbycab.utils.ApiKeys;
import com.parse.starter.zubbycab.utils.BaseActivity;
import com.parse.starter.zubbycab.utils.PreferenceConnector;
import com.parse.starter.zubbycab.view.navigationdrawer.activity.MainActivity;
import com.parse.starter.zubbycab.view.registration_login.model.ValidSessionModel;
import com.parse.starter.zubbycab.view.registration_login.presenter.ValidSessionPresenter;
import com.parse.starter.zubbycab.view.tokengenerate.GenerateTokenClass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimeZone;

public class SplashActivity extends BaseActivity implements ResultInterface1 {
    private ActivitySplashBinding mActivitySplashBinding;
    private Boolean mValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        TimeZone tz = TimeZone.getDefault();
        PreferenceConnector.writeString(context, ApiKeys.TIMEZONE, tz.getID());

//        while (PreferenceConnector.readString(context, ApiKeys.TOKEN, "").equals("")) {
//            new GenerateTokenClass(context);
//        }

        Log.e("string", PreferenceConnector.readString(context, ApiKeys.TOKEN, ""));
        Thread timer = new Thread() {
            public void run() {
                try {
                    if (PreferenceConnector.readString(context, ApiKeys.TOKEN, "").equals("")) {
                        new GenerateTokenClass(context);
                    }
                    validateSession();
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Log.e("valid", "" + mValid);
                    if (mValid) {

                    } else {

                    }
                }
            }
        };
        timer.start();
    }

    public void validateSession() {
        new ValidSessionPresenter().show(this, context);
    }

    @Override
    public void onSuccess(Object object) {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailed(Object failobject) {
        Intent intent = new Intent(context, StartActivity.class);
        startActivity(intent);
    }
}
