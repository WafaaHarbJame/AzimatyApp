package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.CitiesModel;
import com.azimaty.azimatyapp.Model.MemberModel;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.UploadActivity;
import com.azimaty.azimatyapp.Utlities.UtilityApp;
import com.azimaty.azimatyapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends BaseActivity {

    boolean InternetConnect = false;
    List<SubItem> subItemListCity;
    private Button mBustart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        mBustart = findViewById(R.id.bustart);

        InternetConnect = CheckInternet();

        if (UtilityApp.isLogin()) {
            String token = UtilityApp.getUserToken();
            System.out.println("Log token " + token);
            if (InternetConnect) {
                getProfile(token);
            } else {
                Toast(getString(R.string.checkInternet));


            }
        }
        mBustart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });
    }

    public void getProfile(final String token) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");
                        Log.e("id", user + "");
                        Log.e("id", data + "");
                        int id = user.getInt("id");
                        String name = user.getString("name");
                        String phone = user.getString("phone");
                        String photo = user.getString("photo");
                        boolean userStatus = user.getBoolean("status");
                        Log.e("id", id + "");
                        Log.e("name", name + "");
                        Log.e("phone", phone + "");
                        MemberModel memberModel = UtilityApp.getUserData();
                        memberModel.id = id;
                        memberModel.phone = phone;
                        memberModel.photo = photo;
                        memberModel.status = userStatus;
                        UtilityApp.setUserData(memberModel);


                    } else {
                        Toast(message);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActiviy(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));
                Toast(error.getMessage());


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


    public void getCities() {

        showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.CITIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {

                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("cities");
                        subItemListCity.add(new SubItem("الكل", 0));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            subItemListCity.add(new SubItem(name, id));

                        }



                        CitiesModel citiesModel = new CitiesModel(subItemListCity);
                        UtilityApp.setCitiesData(citiesModel);



                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


                } catch (JSONException e) {
                    e.printStackTrace();

                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();


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
