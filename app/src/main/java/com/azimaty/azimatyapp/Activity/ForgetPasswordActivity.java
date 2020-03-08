package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.MemberModel;
import com.azimaty.azimatyapp.R;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends BaseActivity {

    private ImageButton mClose;
    private ImageView mImageView;
    private EditText mPhonenumber;
    private Button mSent;
    boolean InternetConnect=false;
    String userphone;
    String CountryCode = "+966";
    String phonewithoutplus;
    private CountryCodePicker mCcp;
    boolean select_country = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mClose = findViewById(R.id.close);
        mImageView = findViewById(R.id.imageView);
        mPhonenumber = findViewById(R.id.phonenumber);
        mSent = findViewById(R.id.sent);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        InternetConnect= CheckInternet();
        mCcp = findViewById(R.id.ccp);

        mCcp.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                select_country = true;
                return false;
            }
        });
        mCcp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                select_country = true;
                CountryCode = mCcp.getSelectedCountryCodeWithPlus();

            }
        });


        mSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if (mPhonenumber.getText().toString().equals(null) || mPhonenumber.getText().toString().equals("")) {
                    mPhonenumber.setError(getString(R.string.mPhonenumber));
                    mPhonenumber.requestFocus();
                }
                  else {

                      String phonewithoutzero;

                      if (mPhonenumber.getText().toString().startsWith("0")) {

                          phonewithoutzero = mPhonenumber.getText().toString().replaceFirst("0", "");
                          mPhonenumber.setText(phonewithoutzero);
                      }

                      userphone = CountryCode + mPhonenumber.getText().toString();

                      if (userphone.startsWith("+")) {

                          phonewithoutplus = userphone.replace("+", "");
                          userphone = phonewithoutplus;


                      }
                      if(InternetConnect){
                          ForgetPassword(userphone);

                      }
                      else {
                          Toast(getString(R.string.checkInternet));


                      }



                  }


            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void ForgetPassword( final String phone) {
        showProgreesDilaog(getActiviy(),getString(R.string.forgetpassward),getString(R.string.forgetpasswardte));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.FORGER_PASSWAORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {
                        Intent intent = new Intent(getActiviy(), LoginActivity.class);
                        startActivity(intent);
                        finish();



                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }

                    hideProgreesDilaog(getActiviy(),getString(R.string.forgetpassward),getString(R.string.forgetpasswardte));


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgreesDilaog(getActiviy(),getString(R.string.forgetpassward),getString(R.string.forgetpasswardte));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgreesDilaog(getActiviy(),getString(R.string.forgetpassward),getString(R.string.forgetpasswardte));



            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("phone", phone);


                return map;

            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }
}
