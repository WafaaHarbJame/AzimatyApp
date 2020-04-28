package com.jamaatna.jamaatnaapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.MemberModel;
import com.jamaatna.jamaatnaapp.Model.SharedPManger;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.hbb20.CountryCodePicker;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    SharedPManger sharedPManger;
    String userphone;
    String CountryCode = "+966";
    boolean select_country = false;
    String phonewithoutplus;
    int type;
    boolean InternetConnect = false;
    private ImageButton mClose;
    private ImageView mImageView;
    private EditText mPhonenumber;
    private EditText mPassword;
    private Switch mRememberpass;
    private TextView mForgetpassward;
    private Button mloginButton;
    private TextView mRegister;
    private AVLoadingIndicatorView mLoading;
    private CountryCodePicker mCcp;
    private boolean is_Remember_Passward;
    private  String phone_without_code;
    int code=966;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mClose = findViewById(R.id.close);
        mImageView = findViewById(R.id.imageView);
        mPhonenumber = findViewById(R.id.phonenumber);
        mPassword = findViewById(R.id.password);
        mRememberpass = findViewById(R.id.rememberpass);
        mForgetpassward = findViewById(R.id.forgetpassward);
        mloginButton = findViewById(R.id.loginButton);
        mRegister = findViewById(R.id.register);
        mLoading = findViewById(R.id.loading);
        mCcp = findViewById(R.id.ccp);


        sharedPManger = new SharedPManger(LoginActivity.this);

        mForgetpassward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });


mRememberpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if(mRememberpass.isChecked()){
            sharedPManger.SetData(AppConstants.mRememberpass,true);

        }
        else {
            sharedPManger.SetData(AppConstants.mRememberpass,false);


        }
    }
});


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
                 code=mCcp.getSelectedCountryCodeAsInt();
                 sharedPManger.SetData(AppConstants.code,code);

            }
        });


        is_Remember_Passward=sharedPManger.getDataBool(AppConstants.mRememberpass);
        if(is_Remember_Passward){
            String phone=sharedPManger.getDataString(AppConstants.phone_without_code);
                    String passward= sharedPManger.getDataString(AppConstants.PASSWORD);
                    int code=sharedPManger.getDataInt(AppConstants.code);

          //  Toast.makeText(this, ""+code, Toast.LENGTH_SHORT).show();
            mPhonenumber.setText(phone);
            mPassword.setText(passward);
            mRememberpass.setChecked(true);
            mCcp.setCountryForPhoneCode(code);

        }

        else {
            mRememberpass.setChecked(false);


        }

        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPhonenumber.getText().toString().equals(null) || mPhonenumber.getText().toString().equals("")) {
                    mPhonenumber.setError(getString(R.string.mPhonenumber));
                    mPhonenumber.requestFocus();
                } else if (mPassword.getText().toString().equals(null) || mPassword.getText().toString().equals("")) {
                    mPassword.setError(getString(R.string.mPassword));
                    mPassword.requestFocus();
                } else {

                    String phonewithoutzero;

                    if (mPhonenumber.getText().toString().startsWith("0")) {

                        phonewithoutzero = mPhonenumber.getText().toString().replaceFirst("0", "");
                        mPhonenumber.setText(phonewithoutzero);
                    }

                    phone_without_code=mPhonenumber.getText().toString();
                    sharedPManger.SetData(AppConstants.phone_without_code,phone_without_code);
                    sharedPManger.SetData(AppConstants.code,code);

                    userphone = CountryCode + mPhonenumber.getText().toString();

                    if (userphone.startsWith("+")) {

                        phonewithoutplus = userphone.replace("+", "");
                        userphone = phonewithoutplus;


                    }
                    InternetConnect = CheckInternet();
                    if (InternetConnect) {
                        Log.e("WAFAA", "" + userphone);

                        Login(userphone, mPassword.getText().toString());

                    } else {
                        Toast(getString(R.string.checkInternet));


                    }


                }
            }
        });


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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
    }

    public void Login(final String phone, final String password) {

        showProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MemberModel memberModel;

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        //Toast(message);
                        JSONObject data = register_response.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");
                        int id = user.getInt("id");
                        String name = user.getString("name");
                        boolean userStatus = user.getBoolean("status");
                        String phone = user.getString("phone");
                        String photo = user.getString("photo");
                        String token = data.getString("token");
                        sharedPManger.SetData(AppConstants.USERPHONE,userphone);
                        sharedPManger.SetData(AppConstants.PASSWORD,mPassword.getText().toString());
                        sharedPManger.SetData(AppConstants.SelectedCountryCodeplus,CountryCode);


                        Log.e("id", id + "");
                        Log.e("name", name + "");
                        Log.e("phone", phone + "");
                        Log.e("token", token + "");
                        memberModel = new MemberModel(token, id, name, phone, photo, userStatus);


                        UtilityApp.setUserData(memberModel);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();


                    }
                    else if(status==0)  {
                         if(register_response.has("data")){

                             JSONObject data = register_response.getJSONObject("data");
                             JSONObject user = data.getJSONObject("user");
                             int id = user.getInt("id");
                             String name = user.getString("name");
                             boolean userStatus = user.getBoolean("status");
                             if(!userStatus){
                                 Intent intent = new Intent(LoginActivity.this, ActivatePhoneActivity.class);
                                 sharedPManger.SetData(AppConstants.USERPHONE,userphone);
                                 intent.putExtra(AppConstants.USERPHONE,userphone);
                                 startActivity(intent);
                                 finish();

                             }
                         }

                         else {
                             Toast(message);
                         }



                    }
                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoading.smoothToHide();
                mLoading.hide();
               // Toast( error.getMessage());
                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


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
