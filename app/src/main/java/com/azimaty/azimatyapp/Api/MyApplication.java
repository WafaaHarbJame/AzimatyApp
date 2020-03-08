package com.azimaty.azimatyapp.Api;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.azimaty.azimatyapp.Model.SharedPManger;
import com.franmontiel.localechanger.LocaleChanger;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hp on 12/09/2017.
 */

public class MyApplication extends Application {

    private RequestQueue requestQueue;
    private static MyApplication anInstance;
    SharedPreferences sharedPreferences;
    String appLanguage;
    SharedPreferences.Editor editor_signUp;
    private  SharedPManger sharedPManger;



    @Override
    public void onCreate() {
        super.onCreate();
        anInstance = this;
        List<Locale> locales = new ArrayList<>();
        locales.add(new Locale("ar"));
        sharedPManger=new SharedPManger(this);

        LocaleChanger.initialize(getApplicationContext(), locales);

        LocaleChanger.setLocale(new Locale("ar"));


    }

    public void addToRequestQueue(Request request){
        if(getRequestQueue() !=null) {
            getRequestQueue().add(request);
        }
        else {
            requestQueue = Volley.newRequestQueue(this);
            getRequestQueue().add(request);
        }
    }

    private RequestQueue getRequestQueue(){
        return  requestQueue;
    }

    public void cancelRequest(String tag){
        getRequestQueue().cancelAll(tag);
    }

    public static synchronized MyApplication getInstance(){
        return anInstance;
    }

    private static Activity mCurrentActivity = null;
    public static Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        MyApplication.mCurrentActivity = mCurrentActivity;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LocaleChanger.onConfigurationChanged();

    }
    public SharedPManger getSharedPManger(){


      return  sharedPManger;
    }
}
