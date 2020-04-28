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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Activity.EditItemActivity;
import com.jamaatna.jamaatnaapp.Activity.MyserviceItemDetailsActivity;
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


public class MyServiceItemImagesAadapter extends RecyclerView.Adapter<MyServiceItemImagesAadapter.MyHolder>{

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

    public MyServiceItemImagesAadapter(Context context, List<Items_image_service> items_image_services){
        this.items_image_services = items_image_services;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public MyServiceItemImagesAadapter(Context context, List<Items_image_service> areas_counseling_models, String categoryId){
        this.items_image_services = items_image_services;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.categoryId = categoryId;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row_items_myservice,parent,false);
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

        ImageView mEditblue,mDelete;


        public MyHolder(View itemView) {
            super(itemView);
            image_item = itemView.findViewById(R.id.image_item);
            mEditblue = itemView.findViewById(R.id.editblue);
            mDelete = itemView.findViewById(R.id.delete);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent intent=new Intent(context, MyserviceItemDetailsActivity.class);
                    intent.putExtra(AppConstants.item_id,items_image_services.get(position).getId());
                    intent.putExtra(AppConstants.list_id,items_image_services.get(position).getList_id());
                    context.startActivity(intent);
                }
            });


            mEditblue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Intent intent=new Intent(context, EditItemActivity.class);
                    intent.putExtra(AppConstants.item_id,items_image_services.get(position).getId());
                    intent.putExtra(AppConstants.list_id,items_image_services.get(position).getList_id());
                    context.startActivity(intent);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if (UtilityApp.isLogin()) {
                        String token = UtilityApp.getUserToken();
                        DeleteImage(items_image_services.get(position).getImage_id(),token,position);



                    }
//                    else {
//
//                        Toast.makeText(context, ""+context.getString(R.string.you_must_login), Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(context, LoginActivity.class);
//                        context.startActivity(intent);
//                    }

                }
            });





        }




    }

    public void DeleteImage(int item_id, final String token,final int position) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.DELEET_IMAGE+item_id+"/delete"
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
                        notifyDataSetChanged();
                        items_image_services.remove(position);
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
