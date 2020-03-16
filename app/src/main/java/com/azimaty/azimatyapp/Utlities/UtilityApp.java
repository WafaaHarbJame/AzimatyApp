package com.azimaty.azimatyapp.Utlities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import com.azimaty.azimatyapp.Api.MyApplication;

import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.CitiesModel;
import com.azimaty.azimatyapp.Model.MemberModel;
import com.google.gson.Gson;


public class UtilityApp {

    public static String getUnique() {

        String android_id = Settings.Secure.getString(MyApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }

    public static int getAppVersion() {

        PackageInfo pinfo = null;
        try {
            pinfo = MyApplication.getInstance().getPackageManager().getPackageInfo(MyApplication.getInstance().getPackageName(), 0);

            int versionNumber = pinfo.versionCode;

//            Log.i("Utility", "Log versionNumber " + versionNumber);

            return versionNumber;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


//        int versionCode = BuildConfig.VERSION_CODE;


        return 0;
    }

    public static String getAppVersionStr() {

        PackageInfo pinfo = null;
        try {
            pinfo = MyApplication.getInstance().getPackageManager()
                    .getPackageInfo(MyApplication.getInstance().getPackageName(), 0);

            String versionName = pinfo.versionName;

//            Log.i("Utility", "Log versionNumber " + versionNumber);

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


//        int versionCode = BuildConfig.VERSION_CODE;


        return "";
    }

    public static boolean isFirstRun() {

        boolean isFirstRun = MyApplication.getInstance().getSharedPManger().getDataBool(AppConstants.KEY_FIRST_RUN, true);
        return isFirstRun;
    }

    public static void setIsFirstRun(boolean isFirstRun) {

        MyApplication.getInstance().getSharedPManger().SetData(AppConstants.KEY_FILE, isFirstRun);
    }


    public static boolean isLogin() {
        String userToken = MyApplication.getInstance().getSharedPManger().getDataString(AppConstants.KEY_MEMBER,
                null);
        return userToken != null;
    }


    public static void logOut() {

        MyApplication.getInstance().getSharedPManger().SetData(AppConstants.KEY_MEMBER, null);
    }

    public static void setToShPref(String key, String data) {
        MyApplication.getInstance().getSharedPManger().SetData(key, data);
    }

    public static String getFromShPref(String key) {
        return MyApplication.getInstance().getSharedPManger().getDataString(key);
    }

    public static void setFCMToken(String fcmToken) {
        MyApplication.getInstance().getSharedPManger().SetData(AppConstants.KEY_FIREBASE_TOKEN, fcmToken);
    }

    public static String getFCMToken() {
        return MyApplication.getInstance().getSharedPManger().getDataString(AppConstants.KEY_FIREBASE_TOKEN);
    }

    public static void setLanguage(String language) {
        MyApplication.getInstance().getSharedPManger().SetData(AppConstants.KEY_MEMBER_LANGUAGE, language);
    }

    public static String getLanguage() {
        return MyApplication.getInstance().getSharedPManger().getDataString(AppConstants.KEY_MEMBER_LANGUAGE);
    }

    public static void setUserData(MemberModel user) {
        String userData = new Gson().toJson(user);
        MyApplication.getInstance().getSharedPManger().SetData(AppConstants.KEY_MEMBER, userData);
    }


    public static void setCitiesData(CitiesModel citiesModel) {
        String cityData = new Gson().toJson(citiesModel);
        MyApplication.getInstance().getSharedPManger().SetData(AppConstants.KEY_CITIES, cityData);
    }
    public static MemberModel getUserData() {
        String userJsonData = MyApplication.getInstance().getSharedPManger().getDataString(AppConstants.KEY_MEMBER);
        MemberModel user = new Gson().fromJson(userJsonData, MemberModel.class);
        return user;
    }





    public static String getUserToken() {

        MemberModel memberModel = getUserData();
        if (memberModel != null) {
            String token = AppConstants.TOKEN_PREFIX + memberModel.ApiToken;
            return token;
        }

        return null;
    }



}
