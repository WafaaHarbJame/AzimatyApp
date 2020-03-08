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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.azimaty.azimatyapp.Activity.BaseActivity;
import com.azimaty.azimatyapp.Activity.CoffeActivity;
import com.azimaty.azimatyapp.Activity.FamilyActivity;
import com.azimaty.azimatyapp.Activity.HomeActivity;
import com.azimaty.azimatyapp.Activity.HotllActivity;
import com.azimaty.azimatyapp.Activity.ResturantActivity;
import com.azimaty.azimatyapp.Activity.StartActivity;
import com.azimaty.azimatyapp.Activity.ui.home.HomeViewModel;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private HomeViewModel homeViewModel;
    private RecyclerView mBestrating;
    private SliderLayout mDemoSlider;
    private SliderAdapterExample adapter;
    SliderView sliderView;
    private ImageView mFamily;
    private ImageView mCofffe;
    private ImageView mHotle;
    private ImageView mResturant;
    SharedPManger sharedPManger;
    boolean InternetConnect = false;
    List<SliderItem> mSliderItems;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //  textView.setText(s);
            }
        });
        mBestrating = root.findViewById(R.id.bestrating);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ItemAdapter itemAdapter = new ItemAdapter(buildItemList(), getContext());
        mBestrating.setAdapter(itemAdapter);
        mBestrating.setLayoutManager(layoutManager);
        InternetConnect=CheckInternet();
        mSliderItems = new ArrayList<>();


        sliderView = root.findViewById(R.id.imageSlider);
        mFamily = root.findViewById(R.id.family);
        mCofffe = root.findViewById(R.id.cofffe);
        mHotle = root.findViewById(R.id.hotle);
        mResturant = root.findViewById(R.id.resturant);
        adapter = new SliderAdapterExample(getActivity(),mSliderItems);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setAutoCycle(true);
        // sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);

        if (InternetConnect) {

            getAdvertisements();

        } else {
            Toast(getString(R.string.checkInternet));


        }
       /* adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
*/

        mFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), FamilyActivity.class);
                startActivity(intent);
            }
        });
        mBestrating.setNestedScrollingEnabled(false);

        mCofffe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), CoffeActivity.class);
                startActivity(intent);
            }
        });     mResturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ResturantActivity.class);
                startActivity(intent);
            }
        });     mHotle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), HotllActivity.class);
                startActivity(intent);
            }
        });


        return root;



    }

    public void getAdvertisements() {

        showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

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
                        JSONArray jsonArray=data.getJSONArray("advertisements");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String photo = jsonObject.getString("photo");
                            mSliderItems.add(new SliderItem(photo,""));

                        }

                        sliderView.setSliderAdapter(adapter);
                        adapter.notifyDataSetChanged();




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
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));


        return subItemList;
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

}