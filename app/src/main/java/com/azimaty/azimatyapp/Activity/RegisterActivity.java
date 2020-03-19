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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.GlobalData;
import com.azimaty.azimatyapp.Model.MemberModel;
import com.azimaty.azimatyapp.Model.SharedPManger;
import com.azimaty.azimatyapp.R;
import com.azimaty.azimatyapp.Utlities.UtilityApp;
import com.hbb20.CountryCodePicker;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity {

    private ImageButton mClose;
    private ImageView mImageView;
    private EditText mUsername;
    private EditText mPhonenumber;
    private EditText mPassword;
    private EditText mReaptedpassword;
    private Button mRegisterbutton;
    private TextView mLogin;
    private AVLoadingIndicatorView mAvi;
    private AVLoadingIndicatorView mLoading;
    String userphone;
    String CountryCode = "+966";
    boolean select_country = false;
    private CountryCodePicker mCcp;
    String phonewithoutplus;
    int type;
    SharedPManger sharedPManger;
    boolean InternetConnect=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mClose = findViewById(R.id.close);
        mImageView = findViewById(R.id.imageView);
        mUsername = findViewById(R.id.username);
        mPhonenumber = findViewById(R.id.phonenumber);
        mPassword = findViewById(R.id.password);
        mReaptedpassword = findViewById(R.id.reaptedpassword);
        mRegisterbutton = findViewById(R.id.registerbutton);
        mLogin = findViewById(R.id.login);
        mLoading = findViewById(R.id.loading);
        mCcp = findViewById(R.id.ccp);
        sharedPManger=new SharedPManger(getActiviy());
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


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRegisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUsername.getText().toString().equals(null) || mUsername.getText().toString().equals("")) {
                    mUsername.setError(getString(R.string.mUsername));
                    mUsername.requestFocus();
                } else if (mPhonenumber.getText().toString().equals(null) || mPhonenumber.getText().toString().equals("")) {
                    mPhonenumber.setError(getString(R.string.mPhonenumber));
                    mPhonenumber.requestFocus();
                } else if (mPassword.getText().toString().equals(null) || mPassword.getText().toString().equals("")) {
                    mPassword.setError(getString(R.string.mPassword));
                    mPassword.requestFocus();
                } else if (mReaptedpassword.getText().toString().equals(null) || mReaptedpassword.getText().toString().equals("")) {
                    mReaptedpassword.setError(getString(R.string.mReaptedpassword));
                    mReaptedpassword.requestFocus();
                } else if (!mReaptedpassword.getText().toString().equals(mPassword.getText().toString())) {
                    mReaptedpassword.setError(getString(R.string.mReaptedpasswordnot));
                    mPassword.requestFocus();

                } else {


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
                    InternetConnect= CheckInternet();
                    if(InternetConnect){
                        Register(mUsername.getText().toString(),userphone,mPassword.getText().toString(),1+"");

                    }
                    else {
                        Toast(getString(R.string.checkInternet));


                    }

                }
            }
        });

    }

    public void Register( final String name, final String phone, final String password,final String user_type) {
        showProgreesDilaog(getActiviy(),getString(R.string.register2),getString(R.string.loadregister));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("REGISTER", response);

                    if (status == 1) {
                        JSONObject data=register_response.getJSONObject("data");
                        Log.e("REGISTER data", data+"");

                        JSONObject user=data.getJSONObject("user");
                        Log.e("id", user+"");
                        Log.e("id", data+"");
                        int id=user.getInt("id");
                        String name=user.getString("name");
                        String phone=user.getString("phone");
                        String photo = user.getString("photo");
                        boolean userStatus = user.getBoolean("status");

                        Log.e("id", id+"");
                        Log.e("name", name+"");
                        Log.e("phone", phone+"");
                        sharedPManger.SetData(AppConstants.USERPHONE,userphone);
//                        sharedPManger.SetData(AppConstants.PASSWORD,mPassword.getText().toString());
//                        sharedPManger.SetData(AppConstants.SelectedCountryCodeplus,CountryCode);

//                        MemberModel memberModel = new MemberModel("", id, name, phone, photo, userStatus);
//                        UtilityApp.setUserData(memberModel);
                        Intent intent = new Intent(RegisterActivity.this, ActivatePhoneActivity.class);
                        intent.putExtra(AppConstants.USERPHONE,userphone);
                        startActivity(intent);
                        finish();


                    } else {
                        Toast.makeText(RegisterActivity.this, "" + message, Toast.LENGTH_LONG).show();

                    }

                    hideProgreesDilaog(getActiviy(),getString(R.string.register2),getString(R.string.loadregister));


                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgreesDilaog(getActiviy(),getString(R.string.register2),getString(R.string.loadregister));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgreesDilaog(getActiviy(),getString(R.string.register2),getString(R.string.loadregister));

              //  Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("name", name);
                map.put("phone", phone);
                map.put("password", password);
              //  map.put("user_type", user_type+"");


                return map;

            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    public void LoginAfterRegister( final String phone, final String password) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {
                     //   Toast.makeText(RegisterActivity.this, "" + message, Toast.LENGTH_LONG).show();
                        JSONObject data=register_response.getJSONObject("data");
                        JSONObject user=data.getJSONObject("user");
                        int id=user.getInt("id");
                        String name=user.getString("name");
                        String phone=user.getString("phone");
                      //  String type=user.getString("type");
                        String token=data.getString("token");
                        Log.e("id", id+"");
                        Log.e("name", name+"");
                        Log.e("phone", phone+"");
                        Log.e("type", type+"");
//                        sharedPManger.SetData(AppConstants.USERPHONE,userphone);
//                        sharedPManger.SetData(AppConstants.TOKEN,token);
//                        sharedPManger.SetData(AppConstants.USERPHONE,mPhonenumber.getText().toString());
//                        sharedPManger.SetData(AppConstants.PASSWORD,mPassword.getText().toString());
//                        sharedPManger.SetData(AppConstants.ISLOGIN,true);
//                        /*  sharedPManger.SetData(AppConstants.SelectedCountryCode,mCcp.getSelectedCountryCode());*/
//                        sharedPManger.SetData(AppConstants.SelectedCountryCodeplus,CountryCode);
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();



                    } else {
                        Toast.makeText(RegisterActivity.this, "" + message, Toast.LENGTH_LONG).show();

                    }


                    hideProgreesDilaog(getActiviy(),getString(R.string.logintitle),getString(R.string.loadlogin));



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    hideProgreesDilaog(getActiviy(),getString(R.string.logintitle),getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoading.smoothToHide();
                mLoading.hide();
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(),getString(R.string.logintitle),getString(R.string.loadlogin));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("phone", phone);
                map.put("password", password);


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
