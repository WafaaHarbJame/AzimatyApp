package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.azimaty.azimatyapp.Fragments.MenuFragment;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Items_image_service;
import com.azimaty.azimatyapp.Model.Setting;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;
import com.azimaty.azimatyapp.Utlities.UtilityApp;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactUsActivity extends BaseActivity {

    boolean InternetConnect = false;
    Setting setting;
    private ImageButton mMBack;
    private TextView mPhonenumber;
    private TextView mEmail;
    private TextView mFacebook;
    private TextView mTwitter;
    private TextView mInstagram;
    private TextView mTvadvertisawahats;
    private TextView mAdvertisawahats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        mMBack = findViewById(R.id.mBack);
        mPhonenumber = findViewById(R.id.phonenumber);
        mEmail = findViewById(R.id.email);
        mFacebook = findViewById(R.id.facebook);
        mTwitter = findViewById(R.id.twitter);
        mInstagram = findViewById(R.id.instagram);
        mTvadvertisawahats = findViewById(R.id.Tvadvertisawahats);
        mAdvertisawahats = findViewById(R.id.advertisawahats);

        InternetConnect = CheckInternet();

        mMBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });
        setting = UtilityApp.getSettingData();
        if (setting != null) {
            mPhonenumber.setText(setting.getPhone());
            mEmail.setText(setting.getEmail());
            mFacebook.setText(setting.getFacebook());
            mAdvertisawahats.setText(setting.getWatsapp());
            mInstagram.setText(setting.getInstagram());
            mTwitter.setText(setting.getTweeter());

        } else {

            getSetting();


        }
//        if (InternetConnect) {
//           getSetting();
//
//        } else {
//           // Toast(getString(R.string.checkInternet));
//
//
//        }

        mPhonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailus(mPhonenumber.getText().toString());
            }
        });
        mTvadvertisawahats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContatWhats(mAdvertisawahats.getText().toString());
            }
        });
    }


    public void getSetting() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.setting, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        String phone = data.getString("phone");
                        String email = data.getString("email");
                        String facebook = data.getString("facebook");
                        String tweeter = data.getString("tweeter");
                        String instagram = data.getString("instagram");
                        String watsapp = data.getString("watsapp");
                        mPhonenumber.setText(phone);
                        mEmail.setText(email);
                        mFacebook.setText(facebook);
                        mAdvertisawahats.setText(watsapp);
                        mInstagram.setText(instagram);
                        mTwitter.setText(tweeter);


                        //hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {
                        // Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


                    //hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


                } catch (JSONException e) {
                    e.printStackTrace();

                    //hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();


                return map;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap();
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
