package com.jamaatna.jamaatnaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamaatna.jamaatnaapp.Model.SliderItem;
import com.jamaatna.jamaatnaapp.R;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample1 extends SliderViewAdapter<SliderAdapterExample1.SliderAdapterVH1> {
    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();
    public SliderAdapterExample1(Context context,List<SliderItem> mSliderItems) {
        this.context = context;
        this.mSliderItems=mSliderItems;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }
    @Override
    public SliderAdapterVH1 onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item1, null);
        return new SliderAdapterVH1(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH1 viewHolder, final int position) {
        SliderItem sliderItem = mSliderItems.get(position);


        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImageUrl())
                .fitCenter()
                .error(R.drawable.itemsimage)
                .into(viewHolder.imageViewBackground);
        viewHolder.mItemName.setText(sliderItem.getDesc());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getCount() {


        return mSliderItems.size();

    }

    class SliderAdapterVH1 extends SliderViewAdapter.ViewHolder {

        View itemView;
        RoundedImageView imageViewBackground;
        ImageView imageGifContainer;
         TextView mItemName;


        public SliderAdapterVH1(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            mItemName = itemView.findViewById(R.id.item_name);

            this.itemView = itemView;

        }
    }
}
