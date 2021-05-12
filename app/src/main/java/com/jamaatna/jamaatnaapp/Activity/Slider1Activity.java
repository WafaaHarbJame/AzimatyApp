package com.jamaatna.jamaatnaapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.jamaatna.jamaatnaapp.Adapter.SliderAdapterExample;
import com.jamaatna.jamaatnaapp.Model.SliderItem;
import com.jamaatna.jamaatnaapp.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class Slider1Activity extends AppCompatActivity {

    SliderView sliderView;
    private SliderAdapterExample adapter;
    List<SliderItem> mSliderItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider1);

        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this,mSliderItems);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setAutoCycle(true);
       // sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));


    }

    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
          //  sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
            } else {
                sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem() {
        SliderItem sliderItem = new SliderItem();
        List<SliderItem> mSliderItems = new ArrayList<>();
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
        adapter.addItem(new SliderItem("https://i.ibb.co/7pZmMSp/slider.png",""));
//   adapter.addItem(mSliderItems);
    }
}