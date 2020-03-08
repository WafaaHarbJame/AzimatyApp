package com.azimaty.azimatyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
    CityAdapter cityAdapter;
    List<SubItem> subItemList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    List<SubItem> buildcityList;
    boolean InternetConnect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        mMenu = findViewById(R.id.menu);
        mBack = findViewById(R.id.back);
        mRvCity = findViewById(R.id.rv_city);
        mFamilyrecycler = findViewById(R.id.familyrecycler);
        linearLayoutManager = new LinearLayoutManager(ResturantActivity.this);
        cityAdapter = new CityAdapter(getActiviy(), subItemList, new DataCallback() {
            @Override
            public void dataResult(Object obj, String func, boolean IsSuccess) {
                SubItem subItem= (SubItem) obj;
                Toast(subItem.getSubItemTitle());

            }
        });
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvCity.setLayoutManager(linearLayoutManager);
        mRvCity.setHasFixedSize(true);

        buildcityList=new ArrayList<>();
        InternetConnect=CheckInternet();
        if (InternetConnect) {

            getCities();

        } else {
            Toast(getString(R.string.checkInternet));


        }
        cityAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(ResturantActivity.this);
        ItemAdapter itemAdapter = new ItemAdapter(buildItemList(), ResturantActivity.this);
        mFamilyrecycler.setAdapter(itemAdapter);
        mFamilyrecycler.setLayoutManager(layoutManager);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResturantActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });



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
                       // Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray=data.getJSONArray("cities");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            subItemList.add(new SubItem(name,id));
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





    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(getString(R.string.item_title), "gg", 5, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 4, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 3, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "gg", 5, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 4, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 3, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));

        return itemList;
    }


    private List<SubItem> buildSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        subItemList.add(new SubItem("مندي",2));
        subItemList.add(new SubItem("مندي",2));


        return subItemList;
    }
}
