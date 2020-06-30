package com.jamaatna.jamaatnaapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jamaatna.jamaatnaapp.Activity.CatogoryActivity;
import com.jamaatna.jamaatnaapp.Activity.ui.home.HomeViewModel;
import com.jamaatna.jamaatnaapp.Adapter.CityAdapter;
import com.jamaatna.jamaatnaapp.Adapter.ItemAdapter;
import com.jamaatna.jamaatnaapp.Adapter.SliderAdapterExample;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.Catogoriies;
import com.jamaatna.jamaatnaapp.Model.Item;
import com.jamaatna.jamaatnaapp.Model.SharedPManger;
import com.jamaatna.jamaatnaapp.Model.SliderItem;
import com.jamaatna.jamaatnaapp.Model.SubItem;
import com.jamaatna.jamaatnaapp.R;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
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
    List<Catogoriies> Catogories;
    String family_name, coffee_name, resturant_name, hotel_name;
    private HomeViewModel homeViewModel;
    private RecyclerView mBestrating;
    private SliderLayout mDemoSlider;
    private SliderAdapterExample adapter;
    private AVLoadingIndicatorView categoriesLoading;
    private LinearLayout categoriesLY;
    private ImageView mFamily;
    private ImageView mCofffe;
    private ImageView mHotle;
    private ImageView mResturant;
    private AVLoadingIndicatorView mProgressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        categoriesLoading = root.findViewById(R.id.categoriesLoading);
        categoriesLY = root.findViewById(R.id.categoriesLY);
        mProgressBar = root.findViewById(R.id.progress_bar);
        Catogories = new ArrayList<>();

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
            getCacheCatogories();
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
                intent.putExtra(AppConstants.Catogory_Name, family_name);

                startActivity(intent);
            }
        });
        mBestrating.setNestedScrollingEnabled(false);

        mCofffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CatogoryActivity.class);
                intent.putExtra(AppConstants.Catogory_id, 2);
                intent.putExtra(AppConstants.Catogory_Name, coffee_name);

                startActivity(intent);
            }
        });

        mHotle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CatogoryActivity.class);
                intent.putExtra(AppConstants.Catogory_id, 3);
                intent.putExtra(AppConstants.Catogory_Name, hotel_name);

                startActivity(intent);
            }
        });

        mResturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CatogoryActivity.class);
                intent.putExtra(AppConstants.Catogory_id, 4);
                intent.putExtra(AppConstants.Catogory_Name, resturant_name);

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

                            itemList.add(new Item(id, 0, 0, name, logo, rating, city_name, subItemList));


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

    public void getCatogories() {
        categoriesLoading.setVisibility(View.VISIBLE);
        categoriesLY.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.categories, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                categoriesLoading.setVisibility(View.GONE);

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("category");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String image = jsonObject.getString("image");
                            String icon = jsonObject.getString("icon");
                            Catogories.add(new Catogoriies(name, image));

                        }
                        categoriesLY.setVisibility(View.VISIBLE);
                       // initCatogory();
                        String image1Url = Catogories.get(0).getCat_photo();
                        String image2Url = Catogories.get(1).getCat_photo();
                        String image3Url = Catogories.get(2).getCat_photo();
                        String image4Url = Catogories.get(3).getCat_photo();
                        if(getContext()!=null){

                            Glide.with(getContext()).load(image1Url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.family).into(mFamily);
                            Glide.with(getContext()).load(image2Url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.cofffe).into(mCofffe);
                            Glide.with(getContext()).load(image3Url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.hotle).into(mHotle);
                            Glide.with(getContext()).load(image4Url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.resturant).into(mResturant);

                        }

                        family_name = Catogories.get(0).getCat_name();
                        coffee_name = Catogories.get(1).getCat_name();
                        hotel_name = Catogories.get(2).getCat_name();
                        resturant_name = Catogories.get(3).getCat_name();



                    } else {
//                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();


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
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    private void getCacheCatogories() {

//        Catogories = UtilityApp.getCatogoriesData();
//        if (Catogories == null) {
        getCatogories();
//        } else {
//            Log.e("WAFAAatogoriesnotnull", Catogories.size() + "");
//            initCatogory();
//
//        }
    }

    public void initCatogory() {
        String image1Url = Catogories.get(0).getCat_photo();
        String image2Url = Catogories.get(1).getCat_photo();
        String image3Url = Catogories.get(2).getCat_photo();
        String image4Url = Catogories.get(3).getCat_photo();

//        Picasso.with(getActivity()).invalidate(image1Url);
//        Picasso.with(getActivity()).invalidate(image2Url);
//        Picasso.with(getActivity()).invalidate(image3Url);
//        Picasso.with(getActivity()).invalidate(image4Url);

        Glide.with(getActivity()).load(image1Url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.family).into(mFamily);
        Glide.with(getActivity()).load(image2Url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.cofffe).into(mCofffe);
        Glide.with(getActivity()).load(image3Url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.hotle).into(mHotle);
        Glide.with(getActivity()).load(image4Url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.resturant).into(mResturant);

//        Picasso.with(getActivity()).load(image1Url).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.familyimage).into(mFamily);
//        Picasso.with(getActivity()).load(image2Url).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.cofffe).into(mCofffe);
//        Picasso.with(getActivity()).load(image3Url).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.hotle).into(mHotle);
//        Picasso.with(getActivity()).load(image4Url).networkPolicy(NetworkPolicy.NO_CACHE).error(R.drawable.resturant).into(mResturant);
        family_name = Catogories.get(0).getCat_name();
        coffee_name = Catogories.get(1).getCat_name();
        hotel_name = Catogories.get(2).getCat_name();
        resturant_name = Catogories.get(3).getCat_name();

    }


}