package com.azimaty.azimatyapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoiriteAdapter extends RecyclerView.Adapter<FavoiriteAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Item> itemList;
    private Context context;


    public FavoiriteAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context=context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_favoriteitem, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Item item = itemList.get(i);
        itemViewHolder.mTvFamilyName.setText(item.getFamily_name());
        itemViewHolder.mLocation.setText(item.getLocation());
        itemViewHolder.mRatingBar.setNumStars(item.getRatingNumber());
        Picasso.with(context)
                .load(item.getFamily_image())
                .error(R.drawable.familyimage)
                .into(itemViewHolder.mFamilyimage);

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                itemViewHolder.rvSubItem.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());

        // Create sub item view adapter
        SubItemAdapter subItemAdapter = new SubItemAdapter(item.getSubItemList());
        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
        itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);
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

        private ItemViewHolder(View itemView) {
            super(itemView);
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mTvFamilyName = itemView.findViewById(R.id.tv_family_name);
            mFavoirite = itemView.findViewById(R.id.favoirite);
            mLocation = itemView.findViewById(R.id.location);
            mFamilyimage = itemView.findViewById(R.id.familyimage);
        }
    }
}
