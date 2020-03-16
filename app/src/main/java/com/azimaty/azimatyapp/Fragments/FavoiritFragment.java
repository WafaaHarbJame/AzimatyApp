package com.azimaty.azimatyapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Activity.LoginActivity;
import com.azimaty.azimatyapp.Adapter.FavoiriteAdapter;
import com.azimaty.azimatyapp.Adapter.ItemAdapter;
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
    private RecyclerView mBestrating;
    List<Items_image_service> items_image_services;
    int favorite_int;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        root = inflater.inflate(R.layout.fragment_favoirit, container, false);
        mBestrating = root.findViewById(R.id.bestrating);

        itemList = new ArrayList<>();
        subItemList = new ArrayList<>();
        items_image_services = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        itemAdapter = new FavoiriteAdapter(itemList, getContext());
        mBestrating.setLayoutManager(layoutManager);
        InternetConnect = CheckInternet();

        if (InternetConnect) {

            if (UtilityApp.isLogin()) {
                String token = UtilityApp.getUserToken();
                GetFavorite(token);


            } else {

                Toast.makeText(getContext(), "" + getContext().getString(R.string.you_must_login_toaddfav), Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast(getString(R.string.checkInternet));


        }


        return root;

    }


    public void GetFavorite(final String token) {
       showProgreesDilaog(getActivity(), getString(R.string.load_data_tittle), getString(R.string.load_data));

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
                            JSONObject Items=jsonObject.getJSONObject("item_id");
                            int item_id_id=Items.getInt("id");
                            String name = Items.getString("name");
                            String description = Items.getString("description");
                            int rating = Items.getInt("rating");
                            JSONArray item_images = Items.getJSONArray("image");
                            boolean favorite=Items.getBoolean("favorite");

                            if(favorite){
                                favorite_int=1;
                            }
                            else {
                                favorite_int=0;

                            }

                            for (int k = 0; k < item_images.length(); k++) {
                                JSONObject jsonObjectitem_images = item_images.getJSONObject(k);
                                int image_id = jsonObjectitem_images.getInt("id");
                                String image_url = jsonObjectitem_images.getString("name");


                                items_image_services.add(new Items_image_service(item_id_id, favorite_int, image_url,id));

                            }

                         itemList.add(new Item(id, name, items_image_services.get(0).getImage(), rating, description, subItemList));


                        }
                        mBestrating.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();


                        hideProgreesDilaog(getActivity(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG).show();

                    }


                    hideProgreesDilaog(getActivity(), getString(R.string.logintitle), getString(R.string.loadlogin));


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
