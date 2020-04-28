package com.jamaatna.jamaatnaapp.Utlities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.MemberModel;
import com.jamaatna.jamaatnaapp.Model.Setting;
import com.jamaatna.jamaatnaapp.Model.SubItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


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

    public static void setSettingData(Setting setting) {
        String userData = new Gson().toJson(setting);
        MyApplication.getInstance().getSharedPManger().SetData(AppConstants.KEY_SETTING, userData);
    }


    public static MemberModel getUserData() {
        String userJsonData = MyApplication.getInstance().getSharedPManger().getDataString(AppConstants.KEY_MEMBER);
        MemberModel user = new Gson().fromJson(userJsonData, MemberModel.class);
        return user;
    }


    public static void setCitiesData(List<SubItem> citiesList) {
        String cityData = new Gson().toJson(citiesList);
        MyApplication.getInstance().getSharedPManger().SetData(AppConstants.KEY_CITIES, cityData);
    }

    public static List<SubItem> getCitiesData() {
        String citiesData = MyApplication.getInstance().getSharedPManger().getDataString(AppConstants.KEY_CITIES);

        return new Gson().fromJson(citiesData, new TypeToken<List<SubItem>>() {
        }.getType());

    }


    public static Setting getSettingData() {
        String settingdata = MyApplication.getInstance().getSharedPManger().getDataString(AppConstants.KEY_SETTING);
        Setting setting = new Gson().fromJson(settingdata, Setting.class);
        return setting;
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
