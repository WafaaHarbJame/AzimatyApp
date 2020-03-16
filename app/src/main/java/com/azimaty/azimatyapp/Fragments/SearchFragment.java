package com.azimaty.azimatyapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Adapter.SearchAdapter;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.Items_image_service;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends BaseFragment {
    View view;
    private ImageButton mClose;
    private EditText mSearch;
    private RecyclerView mBestrating;
    List<Item> itemList;
    List<SubItem> subItemList;
    SearchAdapter itemAdapter;
    boolean InternetConnect = false;
    List<Items_image_service> items_image_services;
    int favorite_int;
    private SearchView mSearchviewtext;
    private TextView mResult;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        mClose = view.findViewById(R.id.close);
        mSearch = view.findViewById(R.id.search);
        mBestrating = view.findViewById(R.id.bestrating);
        // Inflate the layout for this fragment
        mSearchviewtext = view.findViewById(R.id.searchviewtext);
        mResult = view.findViewById(R.id.result);


        itemList = new ArrayList<>();
        subItemList = new ArrayList<>();
        items_image_services = new ArrayList<>();
        mSearchviewtext.setQueryHint(getString(R.string.searchhinit));
        mResult.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        itemAdapter = new SearchAdapter(itemList, getContext());
        mBestrating.setLayoutManager(layoutManager);
        InternetConnect = CheckInternet();
        mSearchviewtext.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if (InternetConnect) {
//                    items_image_services.clear();
//                    itemList.clear();
//                    getListByserach(query);
//
//                } else {
//                    Toast(getString(R.string.checkInternet));
//
//
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (InternetConnect) {
                    items_image_services.clear();
                    itemList.clear();
                    getListByserach(newText);

                } else {
                    Toast(getString(R.string.checkInternet));


                }
                return true;
            }
        });


//        mSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (InternetConnect) {
//                    getListByserach(mSearch.getText().toString());
//
//                } else {
//                    Toast(getString(R.string.checkInternet));
//
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (InternetConnect) {
//                    getListByserach(mSearch.getText().toString());
//
//                } else {
//                    Toast(getString(R.string.checkInternet));
//
//
//                }
//            }
//        });


        return view;

    }

    public void getListByserach(String list_name) {
        items_image_services.clear();
        itemList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.listsearch, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray list = data.getJSONArray("list");
                        for (int j = 0; j < list.length(); j++) {
                            JSONObject jsonObjectlist = list.getJSONObject(j);
                            String name = jsonObjectlist.getString("name");
                            JSONArray item_images = jsonObjectlist.getJSONArray("image");
                            JSONArray item_comments = jsonObjectlist.getJSONArray("comment");
                            int countRating = jsonObjectlist.getInt("countRating");
                            boolean favorite = jsonObjectlist.getBoolean("favorite");
                            int item_id = jsonObjectlist.getInt("id");
                            int rating = jsonObjectlist.getInt("rating");
                            String description = jsonObjectlist.getString("description");
                            String item_name = jsonObjectlist.getString("name");
                            String item_descriptione = jsonObjectlist.getString("description");


                            if (favorite) {
                                favorite_int = 1;
                            } else {
                                favorite_int = 0;

                            }

                            items_image_services.clear();
                            for (int k = 0; k < item_images.length(); k++) {
                                JSONObject jsonObjectitem_images = item_images.getJSONObject(k);
                                int image_id = jsonObjectitem_images.getInt("id");
                                String image_url = jsonObjectitem_images.getString("name");


                                items_image_services.add(new Items_image_service(item_id, favorite_int, image_url, item_id));

                            }


                            if (items_image_services.isEmpty()) {

                                itemList.add(new Item(item_id, name, "http://empty", rating, description, subItemList));

                            } else {

                                itemList.add(new Item(item_id, name, items_image_services.get(0).getImage(), rating, description, subItemList));

                            }



                            hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

                        }
                        mBestrating.setAdapter(itemAdapter);
                        itemAdapter.notifyDataSetChanged();
                        mResult.setVisibility(View.VISIBLE);

                        mResult.setText(getString(R.string.result)+" "+itemList.size());


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
                map.put("name", list_name);


                return map;

            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {


                return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

}