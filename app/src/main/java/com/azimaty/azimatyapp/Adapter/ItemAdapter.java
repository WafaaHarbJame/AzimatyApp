package com.azimaty.azimatyapp.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azimaty.azimatyapp.Activity.ServicedetailsAactivity;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Item> itemList;
    private Context context;


    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rating_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Item item = itemList.get(i);
        itemViewHolder.mTvFamilyName.setText(item.getFamily_name());
        itemViewHolder.mLocation.setText(item.getLocation());
        itemViewHolder.mRatingBar.setRating(item.getRatingNumber());

        //itemViewHolder.mRatingBar2.setNumStars(3);

        Picasso.with(context).load(item.getFamily_image()).error(R.drawable.familyimage)
                .into(itemViewHolder.mFamilyimage);

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemViewHolder.rvSubItem.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());

        // Create sub item view adapter
        itemViewHolder.rvSubItem.setHasFixedSize(true);
        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
        SubItemAdapter subItemAdapter = new SubItemAdapter(item.getSubItemList());
        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
        itemViewHolder.rvSubItem.scrollToPosition(0);
       // itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);
//        itemViewHolder.rvSubItem.setHorizontalScrollBarEnabled(true);


    }

    @Override
    public int getItemCount() {
        return itemList.size();


    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvSubItem;
        private RatingBar mRatingBar;
        private TextView mTvFamilyName;
        private RecyclerView mRvSubItem;
        private ImageView mFavoirite;
        private TextView mLocation;
        private ImageView mFamilyimage;
        private ConstraintLayout container;

        private ItemViewHolder(View itemView) {
            super(itemView);
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mTvFamilyName = itemView.findViewById(R.id.tv_family_name);
            mFavoirite = itemView.findViewById(R.id.favoirite);
            mLocation = itemView.findViewById(R.id.location);
            mFamilyimage = itemView.findViewById(R.id.familyimage);
            container = itemView.findViewById(R.id.container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final  int position=getAdapterPosition();
                    Intent intent = new Intent(context, ServicedetailsAactivity.class);
                    intent.putExtra(AppConstants.service_id,itemList.get(position).getService_id());
                    context.startActivity(intent);

                }
            });


        }
    }
}
