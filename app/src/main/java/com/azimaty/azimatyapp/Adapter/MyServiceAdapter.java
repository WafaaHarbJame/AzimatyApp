package com.azimaty.azimatyapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azimaty.azimatyapp.Activity.AddServiceActivity;
import com.azimaty.azimatyapp.Activity.HomeActivity;
import com.azimaty.azimatyapp.Activity.MyServicedetailsAactivity;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyServiceAdapter extends RecyclerSwipeAdapter<MyServiceAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Item> itemList;
    private Context context;


    public MyServiceAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.swip_layout, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Item item = itemList.get(i);
        itemViewHolder.mTvFamilyName.setText(item.getFamily_name());
        itemViewHolder.mLocation.setText(item.getLocation());
        itemViewHolder.mRatingBar.setNumStars(item.getRatingNumber());
        Picasso.with(context).load(item.getFamily_image()).error(R.drawable.familyimage).into(itemViewHolder.mFamilyimage);
        itemViewHolder.mFavoirite.setVisibility(View.GONE);

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemViewHolder.rvSubItem.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size());

        // Create sub item view adapter
        SubItemAdapter subItemAdapter = new SubItemAdapter(item.getSubItemList());
        itemViewHolder.rvSubItem.setLayoutManager(layoutManager);
        itemViewHolder.rvSubItem.setAdapter(subItemAdapter);
        itemViewHolder.rvSubItem.setRecycledViewPool(viewPool);





        itemViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        itemViewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
              //  YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.edit));
            }
        });
        itemViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
            }
        });


        itemViewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddServiceActivity.class);
                intent.putExtra(AppConstants.KEY_TYPE, AppConstants.UPDATE_SERVICE_FOR_MENU);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
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
        private ImageView mEdit;
        private ImageView mDelete;
        private SwipeLayout swipeLayout;
        ConstraintLayout constraintLayout;


        private ItemViewHolder(View itemView) {
            super(itemView);
            rvSubItem = itemView.findViewById(R.id.rv_sub_item);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mTvFamilyName = itemView.findViewById(R.id.tv_family_name);
            mFavoirite = itemView.findViewById(R.id.favoirite);
            mLocation = itemView.findViewById(R.id.location);
            mFamilyimage = itemView.findViewById(R.id.familyimage);
            container = itemView.findViewById(R.id.container);
            mEdit = itemView.findViewById(R.id.edit);
            mDelete = itemView.findViewById(R.id.delete);
            swipeLayout = itemView.findViewById(R.id.swipe);
            constraintLayout = itemView.findViewById(R.id.container);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyServicedetailsAactivity.class);
                    context.startActivity(intent);

                }
            });


        }


    }
}
