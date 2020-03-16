package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Adapter.CityAdapter;
import com.azimaty.azimatyapp.Adapter.ItemAdapter;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.DataCallback;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResturantActivity extends BaseActivity {


    private ImageButton mMenu;
    private ImageView mBack;
    private RecyclerView mRvCity;
    private RecyclerView mFamilyrecycler;
    LinearLayoutManager linearLayoutManager;
    boolean InternetConnect = false;

    CityAdapter cityAdapter;
    ItemAdapter itemAdapter;

    List<SubItem> subItemListCity;
    List<Item> itemList = new ArrayList<>();
    List<SubItem> subItemList;
    List<SubItem> buildcityList;

    public int Catogory_id;
    private SwipeRefreshLayout mAllswip;
    boolean isrefersh=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        mMenu = findViewById(R.id.menu);
        mBack = findViewById(R.id.back);
        mRvCity = findViewById(R.id.rv_city);
        mFamilyrecycler = findViewById(R.id.familyrecycler);
        mAllswip = findViewById(R.id.allswip);

        buildcityList = new ArrayList<>();
        subItemListCity = new ArrayList<>();
        subItemList = new ArrayList<>();
        Catogory_id = getIntent().getIntExtra(AppConstants.Catogory_id, 3);


        linearLayoutManager = new LinearLayoutManager(ResturantActivity.this);

        cityAdapter = new CityAdapter(getActiviy(), subItemListCity, new DataCallback() {
            @Override
            public void dataResult(Object obj, String func, boolean IsSuccess) {
                SubItem subItem = (SubItem) obj;
                //Toast(subItem.getId()+"");
                getServiceByCitAndCatogory(subItem.getId(), Catogory_id);

            }
        });
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvCity.setLayoutManager(linearLayoutManager);
        mRvCity.setHasFixedSize(true);

        InternetConnect = CheckInternet();
        mAllswip.setRefreshing(false);

        mAllswip.setColorSchemeResources
                (R.color.colorPrimary, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);

        mAllswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (InternetConnect) {
                    isrefersh=true;
                    getCities();
                    getServiceByCitAndCatogory(0,Catogory_id);;

                } else {
                    Toast(getString(R.string.checkInternet));


                }


            }
        });


        cityAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(ResturantActivity.this);
        mFamilyrecycler.setAdapter(itemAdapter);
        itemAdapter = new ItemAdapter(itemList, getActiviy());

        mFamilyrecycler.setLayoutManager(layoutManager);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResturantActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (InternetConnect) {

            getCities();
            getServiceByCitAndCatogory(0,Catogory_id);;

        } else {
            Toast(getString(R.string.checkInternet));


        }
    }


    public void getServiceByCity(final int City_Id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.SERVICE_BY_CITY + City_Id, new Response.Listener<String>() {
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

                            String tag = jsonObject.getString("tag");

                            String[] items = tag.split("-");
                            subItemList.clear();

                            for (String item : items) {
                                System.out.println("item = " + item);
                                subItemList.add(new SubItem(item, 0));
                            }

                            itemList.add(new Item(id, name, logo, rating, city_name, subItemList));


                        }
                        mFamilyrecycler.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();


                        hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));


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

    public void getCities() {
        subItemListCity.clear();

        // showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

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

                        mRvCity.setAdapter(cityAdapter);
                        cityAdapter.notifyDataSetChanged();


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

    public void getServiceByCitAndCatogory(final int City_Id, final int Catogory_id) {
        if(isrefersh){

            hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

        }

        else {
            showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));


        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.SERVICE_BY_CITY_catogory + Catogory_id + "/" + City_Id, new Response.Listener<String>() {
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
                            String tag = jsonObject.getString("tag");

                            String[] items = tag.split("-");
                            List<SubItem> subItemList = new ArrayList<>();

                            for (String item : items) {
//                                System.out.println("item = " + item);
                                subItemList.add(new SubItem(item, id));
                            }

                            itemList.add(new Item(id, name, logo, rating, city_name, subItemList));


                        }
                        mFamilyrecycler.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();


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
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    private List<SubItem> buildSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        subItemList.add(new SubItem("مندي", 2));
        subItemList.add(new SubItem("مندي", 2));


        return subItemList;
    }
}
