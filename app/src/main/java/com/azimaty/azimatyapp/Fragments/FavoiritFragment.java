package com.azimaty.azimatyapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Adapter.FavoiriteAdapter;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.Items_image_service;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoiritFragment extends BaseFragment {

    View root;
    List<Item> itemList;
    List<SubItem> subItemList;
    FavoiriteAdapter itemAdapter;
    boolean InternetConnect = false;
    List<Items_image_service> items_image_services;
    TextView favoritEmptyTv;
    int favorite_int;
    String token;
    private RecyclerView mBestrating;

    View lyt_failed;
    private Button mFailedRetry;
    private SwipeRefreshLayout mAllswip;
    boolean isrefersh=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        root = inflater.inflate(R.layout.fragment_favoirit, container, false);
        mBestrating = root.findViewById(R.id.bestrating);

        lyt_failed = root.findViewById(R.id.failed_home);
        mFailedRetry = lyt_failed.findViewById(R.id.failed_retry);

        itemList = new ArrayList<>();
        subItemList = new ArrayList<>();
        items_image_services = new ArrayList<>();
        mAllswip =  root.findViewById(R.id.allswip);
        favoritEmptyTv=root.findViewById(R.id.favoritEmptyTv);
        mAllswip.setRefreshing(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        itemAdapter = new FavoiriteAdapter(itemList, getContext());
        mBestrating.setLayoutManager(layoutManager);
        InternetConnect = CheckInternet();

        if (InternetConnect) {

            if (UtilityApp.isLogin()) {
                token = UtilityApp.getUserToken();
                GetFavorite(token);


            } else {

                Toast.makeText(getContext(), "" + getContext().getString(R.string.you_must_login_toaddfav), Toast.LENGTH_SHORT).show();

            }

        } else {

            lyt_failed.setVisibility(View.VISIBLE);
            // Toast(getString(R.string.checkInternet));


        }
        mFailedRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt_failed.setVisibility(View.GONE);
                if (UtilityApp.isLogin()) {
                    token = UtilityApp.getUserToken();
                    GetFavorite(token);


                }

            }
        });


        mAllswip.setColorSchemeResources
                (R.color.darkpink, android.R.color.holo_green_dark,
                        android.R.color.holo_orange_dark,
                        android.R.color.holo_blue_dark);



        mAllswip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (InternetConnect) {

                    if (UtilityApp.isLogin()) {
                        isrefersh=true;
                        token = UtilityApp.getUserToken();
                        GetFavorite(token);


                    } else {

                        Toast.makeText(getContext(), "" + getContext().getString(R.string.you_must_login_toaddfav), Toast.LENGTH_SHORT).show();

                    }

                } else {

                    lyt_failed.setVisibility(View.VISIBLE);
                    // Toast(getString(R.string.checkInternet));


                }


            }
        });
        return root;

    }


    public void GetFavorite(final String token) {


        if(isrefersh){

            hideProgreesDilaog(getActivity(), getString(R.string.load_data_tittle), getString(R.string.load_data));

        }

        else {
            showProgreesDilaog(getActivity(), getString(R.string.load_data_tittle), getString(R.string.load_data));


        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.favorites, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                itemList.clear();
                subItemList.clear();
                try {
                    JSONObject register_response = new JSONObject(response);
                    JSONObject meta = register_response.getJSONObject("meta");
                    String message = meta.getString("message");
                    int status = meta.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("favorite");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            JSONObject Items = jsonObject.getJSONObject("item_id");
                            int item_id_id = Items.getInt("id");
                            String name = Items.getString("name");
                            String description = Items.getString("description");
                            int rating = Items.getInt("rating");
                            JSONArray item_images = Items.getJSONArray("image");
                            boolean favorite = Items.getBoolean("favorite");

                            if (favorite) {
                                favorite_int = 1;
                            } else {
                                favorite_int = 0;

                            }

                            for (int k = 0; k < item_images.length(); k++) {
                                JSONObject jsonObjectitem_images = item_images.getJSONObject(k);
                                int image_id = jsonObjectitem_images.getInt("id");
                                String image_url = jsonObjectitem_images.getString("name");


                                items_image_services.add(new Items_image_service(item_id_id, favorite_int, image_url, favorite_int, image_id));

                            }

                            itemList.add(new Item(id,item_id_id,0, name, items_image_services.get(i).getImage(), rating, description, subItemList));


                        }
                        mBestrating.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();
                        if(itemList.isEmpty()){

                            favoritEmptyTv.setVisibility(View.VISIBLE);
                        }



                        hideProgreesDilaog(getActivity(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {




                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG).show();

                    }


                    hideProgreesDilaog(getActivity(), getString(R.string.logintitle), getString(R.string.loadlogin));
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

//                Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
