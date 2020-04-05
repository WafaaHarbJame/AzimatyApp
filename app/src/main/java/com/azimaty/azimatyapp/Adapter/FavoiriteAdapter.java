package com.azimaty.azimatyapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Activity.LoginActivity;
import com.azimaty.azimatyapp.Activity.ServiceItemDetails;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.R;
import com.azimaty.azimatyapp.Utlities.UtilityApp;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int i) {
        final Item item = itemList.get(i);
        itemViewHolder.mTvFamilyName.setText(item.getFamily_name());
        itemViewHolder.mLocation.setText(item.getLocation());
        itemViewHolder.mRatingBar.setRating(item.getRatingNumber());
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
        itemViewHolder.mFavoirite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UtilityApp.isLogin()) {
                    String token = UtilityApp.getUserToken();
                   DeleteFromFavorite(item.getService_id(),token,i);


                }
                else {

                    Toast.makeText(context, ""+context.getString(R.string.you_must_login), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }


            }
        });

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   int position=getAdapterPosition();
                    Intent intent=new Intent(context, ServiceItemDetails.class);
                    intent.putExtra(AppConstants.item_id,itemList.get(position).getItem_id());
                    intent.putExtra(AppConstants.list_id,itemList.get(position).getList_id());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void DeleteFromFavorite(int item_id, final String token,final int position) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.delete_favorite+item_id+"/delete"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
//                    JSONObject meta=register_response.getJSONObject("meta");
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        itemList.remove(position);
                        notifyDataSetChanged();
                        notifyItemRemoved(position);


                    } else {
                        Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();

                    }




                } catch (JSONException e) {
                    e.printStackTrace();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();


                return map;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap();
                header.put("Authorization",  token);
                return header;
            }


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

}
