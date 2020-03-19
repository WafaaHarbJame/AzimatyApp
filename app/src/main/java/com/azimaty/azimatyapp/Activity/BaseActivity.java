package com.azimaty.azimatyapp.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.azimaty.azimatyapp.Model.SharedPManger;
import com.azimaty.azimatyapp.R;
import com.franmontiel.localechanger.LocaleChanger;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    SharedPManger sharedPManger;
    AwesomeProgressDialog awesomeProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleChanger.setLocale(new Locale("ar"));
        sharedPManger = new SharedPManger(BaseActivity.this);

    }

    private void forceRTLSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }


    public void showDialog(Activity activity, String message) {
        progressDialog = new ProgressDialog(activity);
        // progressDialog.getWindow()
        //.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        if (!activity.isFinishing()) {
            try {
                progressDialog.show();
            } catch (Exception e) {
                progressDialog.dismiss();

            }

        }


    }


    public void showProgreesDilaog(Activity activity, String tittle, String message) {
         awesomeProgressDialog = new AwesomeProgressDialog(activity);
        awesomeProgressDialog.setTitle(tittle).setMessage(message).
                setColoredCircle(R.color.darkpink).setDialogIconAndColor(R.drawable.ic_dialog_info,
                R.color.white).setCancelable(false).show();

    }

    public void hideProgreesDilaog(Activity activity, String tittle, String message) {
        if(awesomeProgressDialog!=null){
            awesomeProgressDialog.hide();

        }


    }


    public void progressDialog(Context c, String msg, boolean status) {
        // to show dialog insert status = true to dismiss doialog status = false
        Activity activity = (Activity) c;
        if (status) {
            progressDialog = new ProgressDialog(activity);
            progressDialog = new ProgressDialog(c);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            try {
                if (activity != null && !activity.isFinishing()) progressDialog.show();
            } catch (Exception e) {
                progressDialog.dismiss();
            }

        } else {
            if (progressDialog != null) progressDialog.dismiss();
        }
    }

    protected Activity Toast() {
        return this;

    }

    protected Activity getActiviy() {
        return this;

    }

    protected boolean CheckInternet() {
        boolean connected = false;

        ConnectivityManager conMgr = (ConnectivityManager) BaseActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            connected = true;

        } else {
            connected = false;

        }

        return connected;

    }

    public void hideDialog() {

        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    public void Toast(String msg) {

        Toast.makeText(getActiviy(), msg, Toast.LENGTH_SHORT).show();
    }

    public void Toast(int resId) {

        Toast.makeText(getActiviy(), getString(resId), Toast.LENGTH_SHORT).show();
    }


    public void dailus(String phone) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("myphone dialer", "Call failed", activityException);
        }
    }

    public void showSucessdialag(final Activity activity, String tittle, String message) {

        new AwesomeSuccessDialog(activity).setTitle(tittle).setMessage(message).setColoredCircle(R.color.darkpink).setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white).setCancelable(true).setPositiveButtonText(getString(R.string.dialog_yes_button)).setPositiveButtonbackgroundColor(R.color.darkpink).setPositiveButtonTextColor(R.color.white)
                // .setNegativeButtonText(getString(R.string.dialog_no_button))
                .setNegativeButtonbackgroundColor(R.color.darkpink).setNegativeButtonTextColor(R.color.white).setPositiveButtonClick(new Closure() {
            @Override
            public void exec() {
                Intent intent = new Intent(activity, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButtonClick(new Closure() {
            @Override
            public void exec() {
                //click
            }
        }).show();
    }

    public void ContatWhats(String phone) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.contactwhats) + phone);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(sendIntent, ""));
        startActivity(sendIntent);
    }

}

