package com.jamaatna.jamaatnaapp.Adapter;


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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Activity.AddServiceActivity;
import com.jamaatna.jamaatnaapp.Activity.LoginActivity;
import com.jamaatna.jamaatnaapp.Activity.MyServicedetailsAactivity;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.Item;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {
        Item item = itemList.get(i);
        itemViewHolder.mTvFamilyName.setText(item.getFamily_name());
        itemViewHolder.mLocation.setText(item.getLocation());
        itemViewHolder.mRatingBar.setRating(item.getRatingNumber());
        Picasso.get().load(item.getFamily_image()).error(R.drawable.familyimage).into(itemViewHolder.mFamilyimage);
        itemViewHolder.mFavoirite.setVisibility(View.GONE);

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(itemViewHolder.rvSubItem.getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
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


//        itemViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (UtilityApp.isLogin()) {
//                    String token = UtilityApp.getUserToken();
//                    DeleteService(itemList.get(0).getService_id(),token);
//
//
//                }
//                else {
//
//                    Toast.makeText(context, ""+context.getString(R.string.you_must_login), Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
//                }
//            }
//        });



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
                    final  int position=getAdapterPosition();
                    Intent intent = new Intent(context, MyServicedetailsAactivity.class);
                    intent.putExtra(AppConstants.service_id,itemList.get(position).getService_id());
                    context.startActivity(intent);

                }
            });


            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if (UtilityApp.isLogin()) {
                        String token = UtilityApp.getUserToken();
                   DeleteService(itemList.get(position).getService_id(),token,position);
                     //  Toast.makeText(context, ""+itemList.get(position).getService_id(), Toast.LENGTH_SHORT).show();


                    }
                    else {

                        Toast.makeText(context, ""+context.getString(R.string.you_must_login), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }

                }
            });
            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent intent = new Intent(context, AddServiceActivity.class);
                    intent.putExtra(AppConstants.KEY_TYPE, AppConstants.UPDATE_SERVICE_FOR_MENU);
                    intent.putExtra(AppConstants.my_service_id,itemList.get(position).getService_id());
                   // Toast.makeText(context, "wafaaa  "+itemList.get(position).getService_id(),
                       //     Toast.LENGTH_SHORT).show();
                    Log.e("WAFAA", ""+itemList.get(position).getService_id());
                    context.startActivity(intent);

                }
            });




        }


    }


    public void DeleteService(int service_id, final String token,final int position) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.DELEET_services+service_id+"/delete"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject DeleteService_response = new JSONObject(response);
                    String message = DeleteService_response.getString("message");
                    int status = DeleteService_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                        AppConstants.refershService=true;

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
