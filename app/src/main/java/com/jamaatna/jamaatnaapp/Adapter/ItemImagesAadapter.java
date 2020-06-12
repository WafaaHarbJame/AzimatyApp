package com.jamaatna.jamaatnaapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Activity.LoginActivity;
import com.jamaatna.jamaatnaapp.Activity.ServiceItemDetails;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.Items_image_service;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemImagesAadapter extends RecyclerView.Adapter<ItemImagesAadapter.MyHolder> {

    public ImageView whitefavoirute;
    int pos;
    String categoryId;
    SharedPreferences sharedPreferences;
    String choosing_langauge;
//    boolean isFavorite;
    IClickListener iClickListener;
    boolean isClickable;
    private List<Items_image_service> items_image_services;
    private Context context;
    private LayoutInflater inflater;

    public ItemImagesAadapter(Context context, List<Items_image_service> items_image_services) {
        this.items_image_services = items_image_services;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    public ItemImagesAadapter(Context context, List<Items_image_service> areas_counseling_models, String categoryId) {
        this.items_image_services = items_image_services;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.categoryId = categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setIClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row_items, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        Picasso.with(context).load(items_image_services.get(position).getImage()).error(R.drawable.image_item).into(holder.image_item);


        if (items_image_services.get(position).getFavooirte() == 1) {
            holder.whitefavoirute.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.favoritred));

        } else {

            holder.whitefavoirute.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.whitefavoirute));

        }


    }

    @Override
    public int getItemCount() {
        return items_image_services.size();
    }

    public void AddToFavorite(int item_id, final String token, int pos) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.add_to_favorite + item_id + "/add", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
//                        isFavorite = true;
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                        items_image_services.get(pos).setFavooirte(1);
                        notifyDataSetChanged();


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
                header.put("Authorization", token);
                return header;
            }


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    public interface IClickListener {
        void onItemClick(int position, List<Items_image_service> areas_counseling_models);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public RoundedImageView image_item;

        public ImageView whitefavoirute;


        public MyHolder(View itemView) {
            super(itemView);
            image_item = itemView.findViewById(R.id.image_item);
            whitefavoirute = itemView.findViewById(R.id.whitefavoirute);
            image_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, ServiceItemDetails.class);
                    intent.putExtra(AppConstants.item_id, items_image_services.get(position).getId());
                    intent.putExtra(AppConstants.list_id, items_image_services.get(position).getList_id());
                    context.startActivity(intent);
                }
            });


            whitefavoirute.setOnClickListener(view -> {
                if (UtilityApp.isLogin()) {
                    String token = UtilityApp.getUserToken();
                    int pos = getAdapterPosition();
                    if  (items_image_services.get(pos).getFavooirte() != 1)
                    AddToFavorite(items_image_services.get(pos).getId(), token, pos);
                    else
                        Toast.makeText(context, context.getString(R.string.this_item_in_favorite), Toast.LENGTH_SHORT).show();


//                    if(isFavorite){
//                        holder.whitefavoirute.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.favoritred));
//
//
//                    }


                } else {

                    Toast.makeText(context, "" + context.getString(R.string.you_must_login_toaddfav), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }


            });


        }


    }

}
