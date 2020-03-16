package com.azimaty.azimatyapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Adapter.PersonsAdapterAdapter;
import com.azimaty.azimatyapp.Adapter.SliderAdapterExample;
import com.azimaty.azimatyapp.Adapter.SliderAdapterExample1;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.DataCallback;
import com.azimaty.azimatyapp.Model.Items_image_service;
import com.azimaty.azimatyapp.Model.MemberModel;
import com.azimaty.azimatyapp.Model.PsersonRating;
import com.azimaty.azimatyapp.Model.RateingBottomDialog;
import com.azimaty.azimatyapp.Model.SliderItem;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;
import com.azimaty.azimatyapp.Utlities.UtilityApp;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceItemDetails extends BaseActivity {
    PersonsAdapterAdapter personsAdapterAdapter;
    Context context;
    LinearLayoutManager layoutManager;
    int item_id;
    private List<PsersonRating> psersonRatingList;
    private SliderAdapterExample1 adapter;
    List<SliderItem> mSliderItems;

    private ImageButton mClose;
    private SliderView sliderView;
    private TextView mDescdetails;
    private ImageView mMore;
    private TextView mNumberrating;
    private TextView mCountrratingtv;
    private RecyclerView mRating;
    private TextView mButrating;
    public   String image_url;
    String item_name;
    int list_id;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_item_details);

        mClose = findViewById(R.id.close);
        sliderView = findViewById(R.id.imageSlider);
        mDescdetails = findViewById(R.id.descdetails);
        mMore = findViewById(R.id.more);
        mNumberrating = findViewById(R.id.numberrating);
        mCountrratingtv = findViewById(R.id.countrratingtv);
        mRating = findViewById(R.id.rating);
        mButrating = findViewById(R.id.Butrating);


        layoutManager = new LinearLayoutManager(ServiceItemDetails.this);
        mRating.setNestedScrollingEnabled(false);
        mRating.setLayoutManager(layoutManager);
        item_id = getIntent().getIntExtra(AppConstants.item_id, 0);
        list_id = getIntent().getIntExtra(AppConstants.list_id, 0);

        mSliderItems = new ArrayList<>();

        psersonRatingList = new ArrayList<>();

        personsAdapterAdapter = new PersonsAdapterAdapter(psersonRatingList, ServiceItemDetails.this);

        adapter = new SliderAdapterExample1(getApplicationContext(), mSliderItems);



        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setAutoCycle(true);
        sliderView.setAutoCycle(true);

        getSingleItem(item_id);



        mButrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (UtilityApp.isLogin()) {
                    RateingBottomDialog rateingBottomDialog = new RateingBottomDialog(new DataCallback() {
                        @Override
                        public void dataResult(Object obj, String func, boolean IsSuccess) {
                            PsersonRating psersonRating = (PsersonRating) obj;
                            token = UtilityApp.getUserToken();
                            String comment=psersonRating.getRating_text();
                            int Rating=psersonRating.getRatingnumber();
                            Toast(psersonRating.getRating_text());
                            Make_Rateing_item(token,Rating+"",comment,item_id+"");


                        }
                    });
                    rateingBottomDialog.show(getSupportFragmentManager(), rateingBottomDialog.getTag());
                }
                else {

                    Toast.makeText(getActiviy(), ""+getString(R.string.you_must_login), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActiviy(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });



        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


    public void getSingleItem(int list_id) {
        psersonRatingList.clear();
        mSliderItems.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.items_details + list_id, new Response.Listener<String>() {
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
                            JSONArray item_images = jsonObjectlist.getJSONArray("image");
                            JSONArray item_comments = jsonObjectlist.getJSONArray("comment");
                            int  countRating=jsonObjectlist.getInt("countRating");
                            boolean favorite=jsonObjectlist.getBoolean("favorite");
                            int item_id = jsonObjectlist.getInt("id");
                             item_name = jsonObjectlist.getString("name");
                            String item_descriptione = jsonObjectlist.getString("description");
                            if(countRating>99){
                                mNumberrating.setText("+99");

                            }
                            else {
                                mNumberrating.setText(countRating+"");


                            }
                            mDescdetails.setText(item_descriptione);

                            for (int k = 0; k < item_images.length(); k++) {
                                JSONObject jsonObjectitem_images = item_images.getJSONObject(k);
                                int image_id = jsonObjectitem_images.getInt("id");
                                 image_url = jsonObjectitem_images.getString("name");
                                //  items_image_services.add(new Items_image_service(item_id, 1, image_url));
                                mSliderItems.add(new SliderItem(image_url, item_name));
                            }

                            for (int l = 0; l < item_comments.length(); l++) {

                                JSONObject jsonObjectitem_comments = item_comments.getJSONObject(l);
                                int comment_id = jsonObjectitem_comments.getInt("id");
                                int comment_rating = jsonObjectitem_comments.getInt("rating");
                                String comment_date = jsonObjectitem_comments.getString("date");
                                String comment = jsonObjectitem_comments.getString("comment");
                                JSONObject user_comment = jsonObjectitem_comments.getJSONObject("user");
                                String user_comment_name = user_comment.getString("name");
                                String user_comment_photo = user_comment.getString("photo");

                                psersonRatingList.add(new PsersonRating(user_comment_name,
                                        1, comment, comment_date, user_comment_photo, comment_rating));
                            }

                            mRating.setAdapter(personsAdapterAdapter);
                            sliderView.setSliderAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

                        }
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }


    public void Make_Rateing_item(final String token, final String rating, final String comment,final  String item_id) {
        showProgreesDilaog(getActiviy(),getString(R.string.load_data),getString(R.string.load_data_tittle));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.making_rating+item_id
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("RATEING", response);

                    if (status == 1) {
                      Toast(message);
                        getSingleItem(Integer.parseInt(item_id));




                    }
                    hideProgreesDilaog(getActiviy(),getString(R.string.load_data),getString(R.string.load_data_tittle));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActiviy(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgreesDilaog(getActiviy(),getString(R.string.load_data),getString(R.string.load_data_tittle));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgreesDilaog(getActiviy(),getString(R.string.load_data),getString(R.string.load_data_tittle));

                Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("rating", rating);
                map.put("comment", comment);


                return map;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap();
                header.put("Authorization",  token);
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
