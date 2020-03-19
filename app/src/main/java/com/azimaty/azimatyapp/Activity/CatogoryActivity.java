package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.azimaty.azimatyapp.Utlities.UtilityApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatogoryActivity extends BaseActivity {

    //    CityAdapter cityAdapter;
    ItemAdapter itemAdapter;

    List<SubItem> subItemListCity;
    List<Item> itemList;
    //    List<SubItem> subItemList;
    List<SubItem> buildcityList;

    List<SubItem> subItem;
    LinearLayoutManager linearLayoutManager;
    boolean InternetConnect = false;
    TextView catogoryTitleTV;
    int Catogory_id;
    boolean isrefersh = false;
    SubItem subItem1;
    //    CitiesModel citiesModel;
    private ImageButton mMenu;
    private ImageView mBack;
    private RecyclerView mRvCity;
    private RecyclerView mFamilyrecycler;
    private SwipeRefreshLayout mAllswip;
    int city_id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        mMenu = findViewById(R.id.menu);
        mBack = findViewById(R.id.back);
        mRvCity = findViewById(R.id.rv_city);
        mFamilyrecycler = findViewById(R.id.familyrecycler);
        catogoryTitleTV = findViewById(R.id.catogoryTitleTV);

        buildcityList = new ArrayList<>();
        subItemListCity = new ArrayList<>();
//        subItemList = new ArrayList<>();
        itemList = new ArrayList<>();
        mAllswip = findViewById(R.id.allswip);
        mAllswip.setRefreshing(false);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Catogory_id = bundle.getInt(AppConstants.Catogory_id);
            String categoryName = bundle.getString(AppConstants.Catogory_Name);
            catogoryTitleTV.setText(categoryName);
        }

        mRvCity.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActiviy());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvCity.setLayoutManager(llm);

        InternetConnect = CheckInternet();


        getCacheCities();


//        Toast(subItemListCity.toString()+"");
//


//        subItemListCity=UtilityApp.getCitiesData();
//
//
//
//
//
//        if(subItemListCity==null){
//
//            getCities();
//
//        }

        mAllswip.setColorSchemeResources(R.color.darkpink, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

        mAllswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (InternetConnect) {
                    isrefersh = true;
                    getServiceByCitAndCatogory(city_id, Catogory_id);
                    ;

                } else {
                    Toast(getString(R.string.checkInternet));


                }


            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(CatogoryActivity.this);
        mFamilyrecycler.setLayoutManager(layoutManager);

        itemAdapter = new ItemAdapter(itemList, CatogoryActivity.this);

        if (InternetConnect) {

            //  getCities();
            getServiceByCitAndCatogory(city_id, Catogory_id);
            ;

        } else {
            Toast(getString(R.string.checkInternet));


        }
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatogoryActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void getServiceByCity(final int City_Id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.SERVICE_BY_CITY + City_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                itemList.clear();
                // subItemList.clear();
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        //JSONArray jsonArraycategory=data.getJSONArray("category");
                        JSONArray jsonArray = data.getJSONArray("category");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONArray service = jsonObject.getJSONArray("Services");
                            int id = service.getJSONObject(i).getInt("id");
                            String name = service.getJSONObject(i).getString("name");
                            String phone = service.getJSONObject(i).getString("phone");
                            String logo = service.getJSONObject(i).getString("logo");
                            String Services_status = service.getJSONObject(i).getString("status");
                            JSONObject city = jsonArray.getJSONObject(i).getJSONObject("city");
                            int city_id = city.getInt("id");
                            int rating = jsonObject.getInt("rating");
                            String city_name = city.getString("name");

                            String tag = jsonObject.getString("tag");

                            String[] items = tag.split("-");
                            // subItemList.clear();

                            for (String item : items) {
                                System.out.println("item = " + item);
                                //  subItemList.add(new SubItem(item, 0));
                            }

//                            itemList.add(new Item(id, name, logo, rating, city_name, subItemList));


                        }
                        mFamilyrecycler.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();


//                        hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


//                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


                } catch (JSONException e) {
                    e.printStackTrace();

//                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


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

    private void getCacheCities() {

        subItemListCity = UtilityApp.getCitiesData();
        if (subItemListCity == null) {
            getCities();
        } else {
            subItemListCity.add(0, new SubItem(getString(R.string.all)));

            initCitiesAdapter();
        }
    }

    private void initCitiesAdapter() {

        CityAdapter cityAdapter = new CityAdapter(getActiviy(), subItemListCity, new DataCallback() {
            @Override
            public void dataResult(Object obj, String func, boolean IsSuccess) {
                SubItem subItem = (SubItem) obj;
                //Toast(subItem.getId()+"");
                itemList.clear();
                mFamilyrecycler.setVisibility(View.GONE);
                city_id = subItem.getId();
                getServiceByCitAndCatogory(subItem.getId(), Catogory_id);


            }
        });
        mRvCity.setAdapter(cityAdapter);

//        cityAdapter.notifyDataSetChanged();
    }

    public void getCities() {
        subItemListCity = new ArrayList<>();
        // showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.CITIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                subItemListCity.clear();

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {

                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("cities");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            subItemListCity.add(new SubItem(name, id));

                        }

                        UtilityApp.setCitiesData(subItemListCity);

                        subItemListCity.add(0, new SubItem(getString(R.string.all), 0));
                        initCitiesAdapter();

                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


//                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


                } catch (JSONException e) {
                    e.printStackTrace();

//                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


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
//        if (isrefersh) {
//
//            hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));
//
//        } else {
//            showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));
//
//
//        }
        mAllswip.setRefreshing(true);
        mFamilyrecycler.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.SERVICE_BY_CITY_catogory + Catogory_id + "/" + City_Id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                itemList.clear();
                //subItemList.clear();
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
//                            System.out.println("Log items tags " + items);
//                            subItemList.clear();
                            List<SubItem> subItemList = new ArrayList<>();

                            for (String item : items) {
//                                System.out.println("item = " + item);
                                subItemList.add(new SubItem(item, id));
                            }
                            itemList.add(new Item(id,0,0, name, logo, rating, city_name, subItemList));

                        }
                        mFamilyrecycler.setAdapter(itemAdapter);
//                        itemAdapter.notifyDataSetChanged();

//                        hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


//                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));
                    mAllswip.setRefreshing(false);


                } catch (JSONException e) {
                    e.printStackTrace();

//                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));
                    mAllswip.setRefreshing(false);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));
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

}
