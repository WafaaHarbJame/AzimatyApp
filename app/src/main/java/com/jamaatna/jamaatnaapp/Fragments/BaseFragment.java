package com.jamaatna.jamaatnaapp.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.jamaatna.jamaatnaapp.Activity.HomeActivity;
import com.jamaatna.jamaatnaapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    AwesomeProgressDialog awesomeProgressDialog;


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }


    public void Sucessdialag(final Activity activity, String tittle, String message){

        new AwesomeSuccessDialog(activity)
                .setTitle(tittle)
                .setMessage(message)
                .setColoredCircle(R.color.darkpink)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(true)
                .setPositiveButtonText(getString(R.string.dialog_yes_button))
                .setPositiveButtonbackgroundColor(R.color.darkpink)
                .setPositiveButtonTextColor(R.color.white)
                // .setNegativeButtonText(getString(R.string.dialog_no_button))
                .setNegativeButtonbackgroundColor(R.color.darkpink)
                .setNegativeButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        Intent intent = new Intent(activity, HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                })
                .show();
    }

    public void showProgreesDilaog(Activity activity,String tittle,String message){

        awesomeProgressDialog = new AwesomeProgressDialog(activity);
        awesomeProgressDialog.setTitle(tittle).setMessage(message).
                setColoredCircle(R.color.darkpink).setDialogIconAndColor(R.drawable.ic_dialog_info,
                R.color.white).setCancelable(false).show();
    }

    public void hideProgreesDilaog(Activity activity,String tittle,String message){

        if(awesomeProgressDialog!=null){
            awesomeProgressDialog.hide();

        }
    }


    public void showSucessdialag(final Activity activity, String tittle, String message){

        new AwesomeSuccessDialog(activity)
                .setTitle(tittle)
                .setMessage(message)
                .setColoredCircle(R.color.darkpink)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(true)
                .setPositiveButtonText(getString(R.string.yes))
                .setPositiveButtonbackgroundColor(R.color.darkpink)
                .setPositiveButtonTextColor(R.color.white)
                // .setNegativeButtonText(getString(R.string.dialog_no_button))
               // .setNegativeButtonbackgroundColor(R.color.darkpink)
             //   .setNegativeButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        Intent intent = new Intent(activity, HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                })
                .show();
    }
    public void Toast(String msg) {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected Activity getActiviy() {
        return getActivity();

    }

    public void Toast(int resId) {

        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
    }
    protected boolean CheckInternet() {
        boolean connected = false;

        ConnectivityManager conMgr = (ConnectivityManager) getActiviy().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            connected = true;

        } else {
            connected = false;

        }

        return connected;

    }


}
