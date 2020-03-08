package com.azimaty.azimatyapp.Activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azimaty.azimatyapp.Adapter.PersonsAdapterAdapter;
import com.azimaty.azimatyapp.Adapter.SliderAdapterExample;
import com.azimaty.azimatyapp.Adapter.SliderAdapterExample1;
import com.azimaty.azimatyapp.Model.DataCallback;
import com.azimaty.azimatyapp.Model.PsersonRating;
import com.azimaty.azimatyapp.Model.RateingBottomDialog;
import com.azimaty.azimatyapp.Model.SliderItem;
import com.azimaty.azimatyapp.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ServiceItemDetails extends BaseActivity {
    private List<PsersonRating> psersonRatingList;
    PersonsAdapterAdapter personsAdapterAdapter;
    private SliderAdapterExample1 adapter;

    private ImageButton mClose;
    private SliderView sliderView;
    private TextView mDescdetails;
    private ImageView mMore;
    private TextView mNumberrating;
    private TextView mCountrratingtv;
    private RecyclerView mRating;
    Context context;
    LinearLayoutManager layoutManager;
    private TextView mButrating;

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

        adapter = new SliderAdapterExample1(getActiviy());

        layoutManager = new LinearLayoutManager(ServiceItemDetails.this);
        mRating.setNestedScrollingEnabled(false);
        mRating.setLayoutManager(layoutManager);

        psersonRatingList = new ArrayList<>();
        psersonRatingList.add(new PsersonRating(getString(R.string.username), 1, getString(R.string.descdetails), "10/1/2020", "hh", 2));
        psersonRatingList.add(new PsersonRating(getString(R.string.username), 1, getString(R.string.descdetails), "10/1/2020", "hh", 2));
        psersonRatingList.add(new PsersonRating(getString(R.string.username), 1, getString(R.string.descdetails), "10/1/2020", "hh", 3));
        psersonRatingList.add(new PsersonRating(getString(R.string.username), 1, getString(R.string.descdetails), "10/1/2020", "hh", 4));

        personsAdapterAdapter = new PersonsAdapterAdapter(psersonRatingList, getActiviy());
        mRating.setAdapter(personsAdapterAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setAutoCycle(true);
        sliderView.setAutoCycle(true);
        adapter.addItem(new SliderItem("https://i.ibb.co/R7M8snL/itemsimage.png","طبق اللازنيا"));
        adapter.addItem(new SliderItem("https://i.ibb.co/R7M8snL/itemsimage.png","طبق اللازنيا"));
        adapter.addItem(new SliderItem("https://i.ibb.co/R7M8snL/itemsimage.png","طبق اللازنيا"));
        adapter.addItem(new SliderItem("https://i.ibb.co/R7M8snL/itemsimage.png","طبق اللازنيا"));
        adapter.addItem(new SliderItem("https://i.ibb.co/R7M8snL/itemsimage.png","طبق اللازنيا"));

        mButrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateingBottomDialog rateingBottomDialog=new RateingBottomDialog(new DataCallback() {
                    @Override
                    public void dataResult(Object obj, String func, boolean IsSuccess) {
                        PsersonRating psersonRating= (PsersonRating) obj;
                        Toast(psersonRating.getRating_text());



                    }
                });
                rateingBottomDialog.show(getSupportFragmentManager(), rateingBottomDialog.getTag());

            }
        });

        sliderView.setSliderAdapter(adapter);


        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
