package com.azimaty.azimatyapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Activity.CatogoryActivity;
import com.azimaty.azimatyapp.Activity.ui.home.HomeViewModel;
import com.azimaty.azimatyapp.Adapter.CityAdapter;
import com.azimaty.azimatyapp.Adapter.ItemAdapter;
import com.azimaty.azimatyapp.Adapter.SliderAdapterExample;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.SharedPManger;
import com.azimaty.azimatyapp.Model.SliderItem;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SliderView sliderView;
    SharedPManger sharedPManger;
    boolean InternetConnect = false;
    List<SliderItem> mSliderItems;
    CityAdapter cityAdapter;
    ItemAdapter itemAdapter;

    List<SubItem> subItemListCity;
    List<Item> itemList;
    List<SubItem> subItemList;
    List<SubItem> buildcityList;
    private HomeViewModel homeViewModel;
    private RecyclerView mBestrating;
    private SliderLayout mDemoSlider;
    private SliderAdapterExample adapter;
    private ImageView mFamily;
    private ImageView mCofffe;
    private ImageView mHotle;
    private ImageView mResturant;
    private AVLoadingIndicatorView mProgressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        mProgressBar = root.findViewById(R.id.progress_bar);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //  textView.setText(s);
            }
        });
        mBestrating = root.findViewById(R.id.bestrating);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mBestrating.setLayoutManager(layoutManager);

        InternetConnect = CheckInternet();
        mSliderItems = new ArrayList<>();
        itemList = new ArrayList<>();
        subItemList = new ArrayList<>();

        sliderView = root.findViewById(R.id.imageSlider);
        mFamily = root.findViewById(R.id.family);
        mCofffe = root.findViewById(R.id.cofffe);
        mHotle = root.findViewById(R.id.hotle);
        mResturant = root.findViewById(R.id.resturant);

        adapter = new SliderAdapterExample(getContext(), mSliderItems);
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setAutoCycle(true);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);


        if (InternetConnect) {

            getAdvertisements();
            GetBestServices();

        } else {
            Toast(getString(R.string.checkInternet));
            mProgressBar.setVisibility(View.GONE);

        }


        mFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CatogoryActivity.class);
                intent.putExtra(AppConstants.Catogory_id, 1);
                intent.putExtra(AppConstants.Catogory_Name, getString(R.string.family));

                startActivity(intent);
            }
        });
        mBestrating.setNestedScrollingEnabled(false);

        mCofffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CatogoryActivity.class);
                intent.putExtra(AppConstants.Catogory_id, 2);
                intent.putExtra(AppConstants.Catogory_Name, getString(R.string.cofeee));

                startActivity(intent);
            }
        });

        mHotle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CatogoryActivity.class);
                intent.putExtra(AppConstants.Catogory_id, 3);
                intent.putExtra(AppConstants.Catogory_Name, getString(R.string.hotles));

                startActivity(intent);
            }
        });

        mResturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CatogoryActivity.class);
                intent.putExtra(AppConstants.Catogory_id, 4);
                intent.putExtra(AppConstants.Catogory_Name, getString(R.string.resturants));

                startActivity(intent);
            }
        });


        return root;


    }

    public void getAdvertisements() {

        // showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.ADVERTISTMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("advertisements");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String photo = jsonObject.getString("photo");
                            mSliderItems.add(new SliderItem(photo, ""));

                        }

                        sliderView.setSliderAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    } else {
                        // Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


                    // hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


                } catch (JSONException e) {
                    e.printStackTrace();

                    // hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //  Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


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


    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        // mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    public void GetBestServices() {

        mBestrating.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
//   showProgreesDilaog(getActivity(), getString(R.string.load_data_tittle), getString(R.string.load_data));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.services_best_rating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                itemList.clear();
                subItemList.clear();
                mBestrating.setVisibility(View.VISIBLE);

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

                            itemList.add(new Item(id,0, 0,name, logo, rating, city_name, subItemList));


                        }
                        itemAdapter = new ItemAdapter(itemList, getContext());
                        mBestrating.setAdapter(itemAdapter);
//                        itemAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);


//                       hideProgreesDilaog(getActivity(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {
                        //   Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG).show();
                        mProgressBar.setVisibility(View.GONE);

                    }


//                  hideProgreesDilaog(getActivity(), getString(R.string.logintitle), getString(R.string.loadlogin));
                    mProgressBar.setVisibility(View.GONE);


                } catch (JSONException e) {
                    e.printStackTrace();
                    mProgressBar.setVisibility(View.GONE);

//                 hideProgreesDilaog(getActivity(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);

                //Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
//               hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();


                return map;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");

                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }


}