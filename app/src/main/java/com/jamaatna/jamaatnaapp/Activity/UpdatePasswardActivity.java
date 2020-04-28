package com.jamaatna.jamaatnaapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.MemberModel;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdatePasswardActivity extends BaseActivity {


    private ImageButton mClose;
    private ImageView mImageView;
    private EditText mOldpassword;
    private EditText mPassword;
    private EditText mReaptedpassword;
    private Button mSent;
//    public SharedPManger sharedPManger;
    String token;
    boolean InternetConnect = false;
    MemberModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_passward);

        mClose = findViewById(R.id.close);
        mImageView = findViewById(R.id.imageView);
        mOldpassword = findViewById(R.id.oldpassword);
        mPassword = findViewById(R.id.password);
        mReaptedpassword = findViewById(R.id.reaptedpassword);
        mSent = findViewById(R.id.sent);
//        sharedPManger = new SharedPManger(getActiviy());
        InternetConnect = CheckInternet();
        user= UtilityApp.getUserData();
        token = UtilityApp.getUserToken();

        mSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOldpassword.getText().toString().equals(null) || mOldpassword.getText().toString().equals("")) {
                    mOldpassword.setError(getString(R.string.mOldpassword));
                    mOldpassword.requestFocus();
                }
               else if (mPassword.getText().toString().equals(null) || mPassword.getText().toString().equals("")) {
                    mPassword.setError(getString(R.string.mPassword));
                    mPassword.requestFocus();
                } else if (mReaptedpassword.getText().toString().equals(null) || mReaptedpassword.getText().toString().equals("")) {
                    mReaptedpassword.setError(getString(R.string.mReaptedpassword));
                    mReaptedpassword.requestFocus();
                } else if (!mReaptedpassword.getText().toString().equals(mPassword.getText().toString())) {
                    mReaptedpassword.setError(getString(R.string.mReaptedpasswordnot));
                    mPassword.requestFocus();

                }

               else {
                    if (InternetConnect) {

                        UpdatePassward(token,mOldpassword.getText().toString(),
                                mPassword.getText().toString(),mReaptedpassword.getText().toString());

                    } else {
                        Toast(getString(R.string.checkInternet));


                    }

                }


            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        });
    }


    public void UpdatePassward(final String token,final String old_password,final String new_password,final  String confirmed_password) {
        showProgreesDilaog(getActiviy() ,getString(R.string.updatepasswardtx), getString(R.string.updatepaaward));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.Update_PASSWARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Toast(message);
                    Log.e("WAFAA", response);
//                    Intent intent = new Intent(getActiviy(), LoginActivity.class);
//                    startActivity(intent);
                    hideProgreesDilaog(getActiviy() ,getString(R.string.updatepasswardtx), getString(R.string.updatepaaward));
                    finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgreesDilaog(getActiviy() ,getString(R.string.updatepasswardtx), getString(R.string.updatepaaward));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast(error.getMessage());

                hideProgreesDilaog(getActiviy() ,getString(R.string.updatepasswardtx), getString(R.string.updatepaaward));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("old_password", old_password);
                map.put("new_password", new_password + "");
                map.put("confirmed_password", confirmed_password + "");


                return map;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap();
                header.put("Authorization", token);
                return header;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }
}
