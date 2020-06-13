package com.jamaatna.jamaatnaapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Adapter.ItemImagesAadapter;
import com.jamaatna.jamaatnaapp.Adapter.SubItemAdapterWhiteText;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.Items_image_service;
import com.jamaatna.jamaatnaapp.Model.SubItem;
import com.jamaatna.jamaatnaapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicedetailsAactivity extends BaseActivity {

    GridLayoutManager gridLayoutManager;
    ItemImagesAadapter itemImagesAadapter;
    List<Items_image_service> items_image_services;
    SubItemAdapterWhiteText subItemAdapterWhiteText;
    List<SubItem> subItemList;
    LinearLayoutManager linearLayoutManager;
    int Service_id;
    private ImageButton mMBack;
    private TextView mPhonenumber;
    private TextView mLocation;
    private RecyclerView mRvitems;
    private ImageView mBackfround;
    private ImageView mProfileserviceimage;
    private RatingBar mRatingBar;
    private TextView mServicename;
    private RecyclerView mRvSubItem;
    private TextView mIsServiceon;
    int list_id;
    int favorite_int;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicedetails_aactivity);
        mMBack = findViewById(R.id.mBack);
        mPhonenumber = findViewById(R.id.phonenumber);
        mLocation = findViewById(R.id.location);
        mRvitems = findViewById(R.id.Rvitems);
        mMBack = findViewById(R.id.mBack);
        mBackfround = findViewById(R.id.backfround);
        mProfileserviceimage = findViewById(R.id.profileserviceimage);
        mRatingBar = findViewById(R.id.ratingBar);
        mServicename = findViewById(R.id.servicename);
        mRvSubItem = findViewById(R.id.rv_sub_item);
        mPhonenumber = findViewById(R.id.phonenumber);
        mLocation = findViewById(R.id.location);
        mRvitems = findViewById(R.id.Rvitems);
        mIsServiceon = findViewById(R.id.isServiceon);

        mRvSubItem.setHasFixedSize(true);
        items_image_services = new ArrayList<>();

        if (getIntent().getExtras() != null) {
            Service_id = getIntent().getIntExtra(AppConstants.service_id, 0);
            getSingleService(Service_id);


        }
        gridLayoutManager = new GridLayoutManager(getActiviy(), 3);
        mRvitems.setLayoutManager(gridLayoutManager);
        mRvitems.setHasFixedSize(true);
        mPhonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailus(mPhonenumber.getText().toString());
            }
        });
        mRvitems.setNestedScrollingEnabled(false);


        itemImagesAadapter = new ItemImagesAadapter(getActiviy(), items_image_services);

        subItemList = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getActiviy());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvSubItem.setLayoutManager(linearLayoutManager);

        subItemAdapterWhiteText = new SubItemAdapterWhiteText(subItemList);

        mMBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void getSingleService(int service_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.SIGNAL_SERVICE + service_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                subItemList.clear();
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAAsERVICES ", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("Service");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONArray list = jsonObject.getJSONArray("list");
                             list_id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String phone = jsonObject.getString("phone");
                            String logo = jsonObject.getString("logo");
                            JSONObject city = jsonArray.getJSONObject(i).getJSONObject("city");
//                            boolean favorite=jsonObject.getBoolean("favorite");


                            int city_id = city.getInt("id");
                            int rating = jsonObject.getInt("rating");
                            String city_name = city.getString("name");
                            String tag = jsonObject.getString("tag");
                            String[] items = tag.split("-");
                            mRatingBar.setRating(rating);

                            for (String item : items) {
//                                System.out.println("item = " + item);
                                subItemList.add(new SubItem(item, i));
                            }
                            mRvSubItem.setAdapter(subItemAdapterWhiteText);


                            boolean Services_status = jsonObject.getBoolean("status");
                            if (Services_status) {
                                mIsServiceon.setText(getString(R.string.serviceon));
                            } else {
                                mIsServiceon.setText(getString(R.string.serviceoff));
                                mIsServiceon.setBackground(getResources().getDrawable(R.drawable.redstyle));


                            }
                            for (int j = 0; j < list.length(); j++) {
                                {
                                    JSONObject jsonObjectlist = list.getJSONObject(j);
                                    JSONArray item_images = jsonObjectlist.getJSONArray("image");
                                    JSONArray item_comments = jsonObjectlist.getJSONArray("comment");
                                    boolean favorite=jsonObjectlist.getBoolean("favorite");
                                    if(favorite){
                                        favorite_int=1;
                                    }
                                    else {
                                        favorite_int=0;

                                    }

                                    int item_id = jsonObjectlist.getInt("id");
                                    String item_name = jsonObjectlist.getString("name");
                                    String item_descriptione = jsonObjectlist.getString("description");

//                                    JSONObject jsonObjectitem_images = item_images.getJSONObject(j);
//                                    int image_id = jsonObjectitem_images.getInt("id");
//                                    String image_url = jsonObjectitem_images.getString("name");
//                                    items_image_services.add(new Items_image_service(item_id, favorite_int, image_url,list_id,image_id));
                                    if(item_images.length()>0){
                                        JSONObject jsonObjectitem_images = item_images.getJSONObject(0);
                                        int image_id = jsonObjectitem_images.getInt("id");
                                        String image_url = item_images.getJSONObject(i).getString("name");
                                        items_image_services.add(new Items_image_service(item_id, favorite_int, image_url,list_id,image_id));


                                    }

//                                    for (int k = 0; k < item_images.length(); k++) {
//                                        JSONObject jsonObjectitem_images = item_images.getJSONObject(i);
//                                        int image_id = jsonObjectitem_images.getInt("id");
//                                        String image_url = item_images.getJSONObject(i).getString("name");
//                                        items_image_services.add(new Items_image_service(item_id, favorite_int, image_url,list_id,image_id));
//
//                                    }

                                    for (int l = 0; l < item_comments.length(); l++) {

                                        JSONObject jsonObjectitem_comments = item_comments.getJSONObject(l);
                                        int comment_id = jsonObjectitem_comments.getInt("id");
                                        int comment_rating = jsonObjectitem_comments.getInt("rating");
                                        String comment_date = jsonObjectitem_comments.getString("date");

                                        JSONObject user_comment = jsonObjectitem_comments.getJSONObject("user");
                                        String user_comment_name = user_comment.getString("name");
                                        String user_comment_photo = user_comment.getString("photo");



                                    }





                                    }
                                }



                            mRvitems.setAdapter(itemImagesAadapter);

                            mPhonenumber.setText(phone);
                            mServicename.setText(name);
                            mLocation.setText(city_name);

                            JSONObject user = jsonArray.getJSONObject(i).getJSONObject("user");
                            int user_id = user.getInt("id");
                            String user_name = user.getString("name");
                            boolean userStatus = user.getBoolean("status");
                            String user_phone = user.getString("phone");
                            String user_photo = user.getString("photo");

                            Picasso.with(getActiviy()).load(logo).error(R.drawable.imagedetails).into(mBackfround);

                            Picasso.with(getActiviy()).load(user_photo).error(R.drawable.imageservice).into(mProfileserviceimage);



                            mRvSubItem.setAdapter(subItemAdapterWhiteText);
                            subItemAdapterWhiteText.notifyDataSetChanged();



                        }


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

                //Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
