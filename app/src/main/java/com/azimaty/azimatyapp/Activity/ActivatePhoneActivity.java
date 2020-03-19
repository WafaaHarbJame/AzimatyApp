package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.MemberModel;
import com.azimaty.azimatyapp.Model.SharedPManger;
import com.azimaty.azimatyapp.R;
import com.azimaty.azimatyapp.Utlities.UtilityApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivatePhoneActivity extends BaseActivity {

    private ImageButton mClose;
    private ImageView mImageView;
    private TextView mSendsms;
    private EditText mEditText1;
    private EditText mEditText2;
    private EditText mEditText3;
    private EditText mEditText4;
    private TextView mSentagain;
    private Button mSent;
    String validationCode = "";
   public SharedPManger sharedPManger;
    String phone;
    boolean InternetConnect=false;
    MemberModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_phone);
        mClose = findViewById(R.id.close);
        mImageView = findViewById(R.id.imageView);
        mSendsms = findViewById(R.id.sendsms);
        mEditText1 = findViewById(R.id.editText1);
        mEditText2 = findViewById(R.id.editText2);
        mEditText3 = findViewById(R.id.editText3);
        mEditText4 = findViewById(R.id.editText4);
        mSentagain = findViewById(R.id.sentagain);
        mSent = findViewById(R.id.sent);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    sharedPManger = new SharedPManger(getActiviy());

       phone=sharedPManger.getDataString(AppConstants.USERPHONE);


        mSendsms.setText(getString(R.string.sentnumber)+""+phone);

        InternetConnect= CheckInternet();
        mEditText1.requestFocus();
        mSentagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetConnect){
                    resetCode(phone);

                }
                else {
                    Toast(getString(R.string.checkInternet));


                }
            }
        });
        mEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (mEditText1.length() == 1) {
                    mEditText2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        // Focus to txtDigit 2
        mEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (mEditText2.length() == 1) {
                    mEditText3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        // Focus to txtDigit 4
        mEditText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (mEditText3.length() == 1) {
                    mEditText4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        mEditText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (mEditText4.length() == 1) {
                   VaildateCode();
                }


            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        mSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InternetConnect){
                    VaildateCode();

                }
                else {
                    Toast(getString(R.string.checkInternet));


                }
            }
        });


    }
    public  void VaildateCode() {
        validationCode = mEditText1.getText().toString()
                + mEditText2.getText().toString() + mEditText3.getText().toString() +
                mEditText4.getText().toString() ;

        if (validationCode.length() == 4) {
            activateCode(phone,validationCode);



        }
    }





    public void activateCode( final String phone, final String activate) {

        showProgreesDilaog(getActiviy(),getString(R.string.activate),getString(R.string.activateaccount));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.ACTIVATE_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {
                        JSONObject data=register_response.getJSONObject("data");
                        JSONObject user=data.getJSONObject("user");
                        int id=user.getInt("id");
                        String name=user.getString("name");
                        String phone=user.getString("phone");
                        //String type=user.getString("type");
                        String token=data.getString("token");
                        Log.e("id", id+"");
                        Log.e("name", name+"");
                        Log.e("phone", phone+"");
//                     //   Log.e("type", type+"");
//                        sharedPManger.SetData(AppConstants.TOKEN,token);
//                        sharedPManger.SetData(AppConstants.ISLOGIN,true);
                        boolean userStatus = user.getBoolean("status");
                        String photo = user.getString("photo");

                        MemberModel memberModel = new MemberModel(token, id, name, phone, photo, userStatus);
                        UtilityApp.setUserData(memberModel);

                        Intent intent = new Intent(getActiviy(), LoginActivity.class);
                        startActivity(intent);
                        finish();



                    } else {
                        Toast(message);
                        mEditText1.setText(null);
                        mEditText2.setText(null);
                        mEditText3.setText(null);
                        mEditText4.setText(null);
                        mEditText1.requestFocus();

                    }


                    hideProgreesDilaog(getActiviy(),getString(R.string.activate),getString(R.string.activateaccount));



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActiviy(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    hideProgreesDilaog(getActiviy(),getString(R.string.activate),getString(R.string.activateaccount));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(),getString(R.string.activate),getString(R.string.activateaccount));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("phone", phone);
                map.put("activate", activate);


                return map;

            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);

    }



    public void resetCode( final String phone) {

        showProgreesDilaog(getActiviy(),getString(R.string.activate),getString(R.string.activateaccountagain));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.ResetCode_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {
                        JSONObject data=register_response.getJSONObject("data");

                    } else {
                        Toast(message);
                        mEditText1.setText(null);
                        mEditText2.setText(null);
                        mEditText3.setText(null);
                        mEditText4.setText(null);
                        mEditText1.requestFocus();

                    }


                    hideProgreesDilaog(getActiviy(),getString(R.string.activate),getString(R.string.activateaccountagain));



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActiviy(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    hideProgreesDilaog(getActiviy(),getString(R.string.activate),getString(R.string.activateaccountagain));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(),getString(R.string.activate),getString(R.string.activateaccountagain));


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
