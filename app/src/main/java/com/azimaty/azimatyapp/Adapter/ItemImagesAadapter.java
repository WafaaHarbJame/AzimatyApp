package com.azimaty.azimatyapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azimaty.azimatyapp.Activity.ServiceItemDetails;
import com.azimaty.azimatyapp.Model.Items_image_service;
import com.azimaty.azimatyapp.R;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ItemImagesAadapter extends RecyclerView.Adapter<ItemImagesAadapter.MyHolder>{

    private List<Items_image_service> items_image_services;
    private Context context;
    private LayoutInflater inflater;
    int pos;
    String categoryId;
    SharedPreferences sharedPreferences;
    String choosing_langauge;

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public interface IClickListener{
        void onItemClick(int position, List<Items_image_service> areas_counseling_models);
    }

    public void setIClickListener(IClickListener iClickListener){
        this.iClickListener=iClickListener;
    }


    IClickListener iClickListener;

    public ItemImagesAadapter(Context context, List<Items_image_service> items_image_services){
        this.items_image_services = items_image_services;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public ItemImagesAadapter(Context context, List<Items_image_service> areas_counseling_models, String categoryId){
        this.items_image_services = items_image_services;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.categoryId = categoryId;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row_items,parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    boolean isClickable;

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
       Picasso.with(context)
              .load(items_image_services.get(position).getImage())
               .error(R.drawable.image_item)
               .into(holder.image_item);






    }



    @Override
    public int getItemCount() { return items_image_services.size(); }

    class MyHolder extends RecyclerView.ViewHolder  {
        RoundedImageView image_item;

        ImageView whitefavoirute;


        public MyHolder(View itemView) {
            super(itemView);
            image_item = itemView.findViewById(R.id.image_item);
            whitefavoirute = itemView.findViewById(R.id.whitefavoirute);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ServiceItemDetails.class);
                    context.startActivity(intent);
                }
            });


        }

    }
}
