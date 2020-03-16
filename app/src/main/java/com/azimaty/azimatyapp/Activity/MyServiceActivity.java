package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Adapter.ItemAdapter;
import com.azimaty.azimatyapp.Adapter.MyServiceAdapter;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Fragments.AddServiceFragment;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;
import com.azimaty.azimatyapp.Utlities.UtilityApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyServiceActivity extends BaseActivity {

    MyServiceAdapter myServiceAdapter;
    List<SubItem> subItemList;
    List<String>list=new ArrayList<>();
    List<Item> itemList;
    private TextView mServicename;
    private ImageButton mAddservice;
    private ImageButton mMenu;
    private ImageView mBack;
    private RecyclerView mMyservicerecycler;
    private SwipeRefreshLayout mAllswip;
    String tag;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);
        mServicename = findViewById(R.id.servicename);
        mAddservice = findViewById(R.id.addservice);
        mMenu = findViewById(R.id.menu);
        mBack = findViewById(R.id.back);
        mMyservicerecycler = findViewById(R.id.myservicerecycler);
        AppConstants.InternetConnect = CheckInternet();
        mAllswip = findViewById(R.id.allswip);
        mAllswip.setRefreshing(false);

        itemList = new ArrayList<>();
        subItemList = new ArrayList<>();

        mAllswip.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
          mAllswip.setRefreshing(false);

        mAllswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppConstants.InternetConnect) {
                    AppConstants.isrefersh = true;
                    if (UtilityApp.isLogin()) {
                        String token = UtilityApp.getUserToken();
                        getMyService(token);
                        ;


                    }

                } else {
                    Toast(getString(R.string.checkInternet));


                }


            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(MyServiceActivity.this);
        myServiceAdapter = new MyServiceAdapter(itemList, MyServiceActivity.this);
        mMyservicerecycler.setLayoutManager(layoutManager);

        mAddservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyServiceActivity.this, AddServiceActivity.class);
                intent.putExtra(AppConstants.KEY_TYPE, AppConstants.ADD_SERVICE_FOR_MENU);
                startActivity(intent);
                finish();
            }
        });


        if (AppConstants.InternetConnect) {
            if (UtilityApp.isLogin()) {
                String token = UtilityApp.getUserToken();
                getMyService(token);
                ;


            }

        } else {
            Toast(getString(R.string.checkInternet));


        }

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


    }

    public void getMyService(final String token) {
        if (AppConstants.isrefersh) {

            hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));
        } else {
            showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.My_services, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                itemList.clear();
                subItemList.clear();

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("Services");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String phone = jsonObject.getString("phone");
                            String logo = jsonObject.getString("logo");
                            String Services_status = jsonObject.getString("status");
                            JSONObject city = jsonArray.getJSONObject(i).getJSONObject("city");
                            int city_id = city.getInt("id");
                            int rating = jsonObject.getInt("rating");
                            String city_name = city.getString("name");
                            tag = jsonObject.getString("tag");
                            String[] items = tag.split("-");
                            List<SubItem> subItemList = new ArrayList<>();

                            for (String item : items) {
//                                System.out.println("item = " + item);
                                subItemList.add(new SubItem(item, id));
                            }

                                itemList.add(new Item(id, name, logo, rating, city_name, subItemList));

                        }
                        mMyservicerecycler.setAdapter(myServiceAdapter);
                        myServiceAdapter.notifyDataSetChanged();


                        hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));
                    mAllswip.setRefreshing(false);


                } catch (JSONException e) {
                    e.printStackTrace();

                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));
                    mAllswip.setRefreshing(false);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));
                mAllswip.setRefreshing(false);


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();


                return map;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    @Override
    protected void onResume() {

        super.onResume();
//        if (AppConstants.refershService) {
//            String token = UtilityApp.getUserToken();
//            mAllswip.setRefreshing(true);
//            getMyService(token);
//            AppConstants.refershService = false;
//
//
//        }

    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (AppConstants.refershService) {
//            String token = UtilityApp.getUserToken();
//            mAllswip.setRefreshing(true);
//            getMyService(token);
//            AppConstants.refershService = false;
//
//
//        }
    }
}
