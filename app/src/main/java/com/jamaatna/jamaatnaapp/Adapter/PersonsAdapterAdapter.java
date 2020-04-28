package com.jamaatna.jamaatnaapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jamaatna.jamaatnaapp.Model.PsersonRating;
import com.jamaatna.jamaatnaapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonsAdapterAdapter extends RecyclerView.Adapter<PersonsAdapterAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<PsersonRating> psersonRatingList;
    private Context context;


    public PersonsAdapterAdapter(List<PsersonRating> psersonRatingList, Context context) {
        this.psersonRatingList = psersonRatingList;
        this.context=context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.person_make_rating_item, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        PsersonRating item = psersonRatingList.get(i);
        itemViewHolder.mTvUserName.setText(item.getUsername());
        itemViewHolder.mRatingdate.setText(item.getRating_date());
        itemViewHolder.mRatingBar.setRating(item.getRatingnumber());
        Picasso.with(context)
                .load(item.getUse_image())
                .error(R.drawable.familyimage)
                .into(itemViewHolder.mUserimage);
        itemViewHolder.mTvRatingText.setText(item.getRating_text());
    }

    @Override
    public int getItemCount() {
        return psersonRatingList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private RatingBar mRatingBar;
        private TextView mTvUserName;
        private TextView mTvRatingText;
        private TextView mRatingdate;
        private CircleImageView mUserimage;
        private ConstraintLayout mContainer;



        private ItemViewHolder(View itemView) {
            super(itemView);

            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mTvUserName = itemView.findViewById(R.id.tv_user_name);
            mTvRatingText = itemView.findViewById(R.id.tv_rating_text);
            mRatingdate = itemView.findViewById(R.id.ratingdate);
            mUserimage = itemView.findViewById(R.id.userimage);
            mContainer = itemView.findViewById(R.id.container);



        }
    }
}
