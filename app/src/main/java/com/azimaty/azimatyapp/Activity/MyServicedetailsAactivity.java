package com.azimaty.azimatyapp.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.azimaty.azimatyapp.Adapter.CityAdapter;
import com.azimaty.azimatyapp.Adapter.ItemAdapter;
import com.azimaty.azimatyapp.Adapter.MyServiceItemImagesAadapter;
import com.azimaty.azimatyapp.Adapter.SubItemAdapterWhiteText;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.AppHelper;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.Items_image_service;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.Model.VolleyMultipartRequest;
import com.azimaty.azimatyapp.R;
import com.azimaty.azimatyapp.UploadActivity;
import com.azimaty.azimatyapp.Utlities.UtilityApp;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerComponentHolder;
import com.esafirm.imagepicker.features.imageloader.DefaultImageLoader;
import com.esafirm.imagepicker.model.Image;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class MyServicedetailsAactivity extends BaseActivity {

    private static final int RC_CAMERA = 3000;
    GridLayoutManager gridLayoutManager;
    MyServiceItemImagesAadapter myServiceItemImagesAadapter;
    List<Items_image_service> items_image_services;
    SubItemAdapterWhiteText subItemAdapterWhiteText;
    LinearLayoutManager linearLayoutManager;
    Dialog AddItemdialog;
    CircleImageView mImageView;
    List<SubItem> subItemList = new ArrayList<>();
    List<Item> itemList = new ArrayList<>();
    List<SubItem> subItemListCity;
    List<SubItem> buildcityList;
    int list_id;
    int favorite_int;
    int Service_id;
    String token;

    private ImageButton mdeleteservice;
    private ImageButton mEdit;
    private ImageButton mVisble;
    private ImageView mBack;
    private ImageView mBackfround;
    private ImageView mProfileserviceimage;
    private RatingBar mRatingBar;
    private TextView mServicename;
    private RecyclerView mRvSubItem;
    private TextView mPhonenumber;
    private TextView mLocation;
    private RecyclerView mRvitems;
    private ImageButton mDeleteservice;
    private Switch mServichecked;
    private TextView mAdditem;
    private ImageButton mButtonCancel;
    private TextView mTvRatingItem;
    private TextView mItemImage;
    private EditText mItemName;
    private EditText mItemDesc;
    private Button mAddbutton;
    private TextView mIsserviceon;
    private ArrayList<Image> UplodedImages = new ArrayList<>();
    private ArrayList<Image> images = new ArrayList<>();
    public   List<File> list = new ArrayList<>();;
    boolean Item_image_uploded=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_servicedetails_aactivity);
        mdeleteservice = findViewById(R.id.deleteservice);
        mEdit = findViewById(R.id.edit);
        mVisble = findViewById(R.id.visble);
        mBack = findViewById(R.id.back);
        mServicename = findViewById(R.id.servicename);
        mPhonenumber = findViewById(R.id.phonenumber);
        mLocation = findViewById(R.id.location);
        mRvitems = findViewById(R.id.Rvitems);
        mDeleteservice = findViewById(R.id.deleteservice);
        mBackfround = findViewById(R.id.backfround);
        mProfileserviceimage = findViewById(R.id.profileserviceimage);
        mRatingBar = findViewById(R.id.ratingBar);
        mServichecked = findViewById(R.id.servichecked);
        mAdditem = findViewById(R.id.additem);
        mRvSubItem = findViewById(R.id.rv_sub_item);
        mRvitems = findViewById(R.id.Rvitems);
        mIsserviceon = findViewById(R.id.isserviceon);
        mRvSubItem.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActiviy(), 3);
        mRvitems.setLayoutManager(gridLayoutManager);
        mRvitems.setHasFixedSize(true);
        mRvitems.setNestedScrollingEnabled(false);
        subItemList = new ArrayList<>();
        items_image_services = new ArrayList<>();
        myServiceItemImagesAadapter = new MyServiceItemImagesAadapter(MyServicedetailsAactivity.this, items_image_services);
        linearLayoutManager = new LinearLayoutManager(getActiviy());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvSubItem.setLayoutManager(linearLayoutManager);
        subItemAdapterWhiteText = new SubItemAdapterWhiteText(subItemList);
        mRvSubItem.setAdapter(subItemAdapterWhiteText);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        token = UtilityApp.getUserToken();

        if (getIntent().getExtras() != null) {
            Service_id = getIntent().getIntExtra(AppConstants.service_id, 0);
           // Toast(Service_id + "");
            getMyServiceDetails(token, Service_id);

        }

        mAdditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemdialog = new Dialog(getActiviy());
                AddItemdialog.setContentView(R.layout.adding_item_dialag);
                mButtonCancel = AddItemdialog.findViewById(R.id.buttonCancel);
                mTvRatingItem = AddItemdialog.findViewById(R.id.tv_rating_item);
                mItemImage = AddItemdialog.findViewById(R.id.item_image);
                mItemName = AddItemdialog.findViewById(R.id.item_name);
                mItemDesc = AddItemdialog.findViewById(R.id.item_desc);
                mAddbutton = AddItemdialog.findViewById(R.id.addbutton);
                mImageView = AddItemdialog.findViewById(R.id.imageView);

                AddItemdialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                WindowManager.LayoutParams p = AddItemdialog.getWindow().getAttributes();
                p.width = ViewGroup.LayoutParams.MATCH_PARENT;
                p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                AddItemdialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.END);



                mItemImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Item_image_uploded=true;
                        getImagePicker().start();
                    }
                });


                mAddbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(!Item_image_uploded){
                            Toast(getString(R.string.addingphoto));
                        }
                       else if(mItemName.getText().toString().equals(null)||mItemName.getText().toString().equals("")){
                            mItemName.setError(getString(R.string.item_namrequired));
                            mItemName.requestFocus();
                        }
                       else if(mItemDesc.getText().toString().equals(null)||mItemDesc.getText().toString().equals("")){
                            mItemDesc.setError(getString(R.string.mItemDescrEQUIRED));
                            mItemDesc.requestFocus();
                        }


                        else {
                            AddListItems(list,token, mItemName.getText().toString(), mItemDesc.getText().toString(),
                                    Service_id);


                        }


                    }
                });


                AddItemdialog.setCancelable(true);


                AddItemdialog.show();

                mButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddItemdialog.dismiss();
                    }
                });


            }
        });

        mdeleteservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteService(Service_id, token);


            }
        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyServicedetailsAactivity.this, AddServiceActivity.class);
                intent.putExtra(AppConstants.KEY_TYPE, AppConstants.UPDATE_SERVICE_FOR_MENU);
                intent.putExtra(AppConstants.my_service_id, Service_id);
                startActivity(intent);


            }
        });
        mVisble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyServicedetailsAactivity.this, ServicedetailsAactivity.class);
                intent.putExtra(AppConstants.service_id, Service_id);

                startActivity(intent);

            }
        });


        ;
    }

    public void getMyServiceDetails(final String token, final int service_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.SIGNAL_SERVICE + service_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                subItemList.clear();
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("Service");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONArray list = jsonObject.getJSONArray("list");
                            list_id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String phone = jsonObject.getString("phone");
                            String logo = jsonObject.getString("logo");
                            JSONObject city = jsonArray.getJSONObject(i).getJSONObject("city");
//                            boolean favorite=jsonObject.getBoolean("favorite");
                            int city_id = city.getInt("id");
                            int rating = jsonObject.getInt("rating");
                            String city_name = city.getString("name");
                            mRatingBar.setRating(rating);
                            String tag = jsonObject.getString("tag");
                            String[] items = tag.split("-");
                            for (String item : items) {
//                                System.out.println("item = " + item);
                                subItemList.add(new SubItem(item, i));
                            }
                            mRvSubItem.setAdapter(subItemAdapterWhiteText);

                            boolean Services_status = jsonObject.getBoolean("status");
                            if (Services_status) {
                                mServichecked.setChecked(true);
                                mIsserviceon.setText(getString(R.string.serviceava));
                            } else {
                                mServichecked.setChecked(false);
                                mIsserviceon.setText(getString(R.string.serviceavaoff));


                            }
                            for (int j = 0; j < list.length(); j++) {
                                {
                                    JSONObject jsonObjectlist = list.getJSONObject(j);
                                    JSONArray item_images = jsonObjectlist.getJSONArray("image");
                                    JSONArray item_comments = jsonObjectlist.getJSONArray("comment");
                                    boolean favorite = jsonObjectlist.getBoolean("favorite");
                                    if (favorite) {
                                        favorite_int = 1;
                                    } else {
                                        favorite_int = 0;

                                    }

                                    int item_id = jsonObjectlist.getInt("id");
                                    String item_name = jsonObjectlist.getString("name");
                                    String item_descriptione = jsonObjectlist.getString("description");

                                    for (int k = 0; k < item_images.length(); k++) {
                                        JSONObject jsonObjectitem_images = item_images.getJSONObject(k);
                                        int image_id = jsonObjectitem_images.getInt("id");
                                        String image_url = jsonObjectitem_images.getString("name");
                                        items_image_services.add(
                                                new Items_image_service(item_id, favorite_int, image_url, list_id,image_id));

                                    }

                                    for (int l = 0; l < item_comments.length(); l++) {

                                        JSONObject jsonObjectitem_comments = item_comments.getJSONObject(l);
                                        int comment_id = jsonObjectitem_comments.getInt("id");
                                        int comment_rating = jsonObjectitem_comments.getInt("rating");
                                        String comment_date = jsonObjectitem_comments.getString("date");

                                        JSONObject user_comment = jsonObjectitem_comments.getJSONObject("user");
                                        String user_comment_name = user_comment.getString("name");
                                        String user_comment_photo = user_comment.getString("photo");


                                    }


                                }
                            }


                            mRvitems.setAdapter(myServiceItemImagesAadapter);
                            mPhonenumber.setText(phone);
                            mServicename.setText(name);
                            mLocation.setText(city_name);
                            JSONObject user = jsonArray.getJSONObject(i).getJSONObject("user");
                            int user_id = user.getInt("id");
                            String user_name = user.getString("name");
                            boolean userStatus = user.getBoolean("status");
                            String user_phone = user.getString("phone");
                            String user_photo = user.getString("photo");

                            Picasso.with(getActiviy()).load(logo).error(R.drawable.imagedetails).into(mBackfround);

                            Picasso.with(getActiviy()).load(user_photo).error(R.drawable.imageservice).into(mProfileserviceimage);


                            mRvSubItem.setAdapter(subItemAdapterWhiteText);
                            subItemAdapterWhiteText.notifyDataSetChanged();


                        }


                        hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


                } catch (JSONException e) {
                    e.printStackTrace();

                    hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

              //  Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


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

    public void DeleteService(int service_id, final String token) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.DELEET_services + service_id + "/delete", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject DeleteService_response = new JSONObject(response);
                    String message = DeleteService_response.getString("message");
                    int status = DeleteService_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        Toast(message);
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActiviy(), MyServiceActivity.class);
                        startActivity(intent);

                    } else {
                        Toast(message);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

              //  Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();


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

    private void AddListItems(final  List<File> fileList,final String token, final String name,
                          final String description, final int service_id) {
        showProgreesDilaog(getActiviy(), getString(R.string.additem_title), getString(R.string.add_item));
        AndroidNetworking.upload(AppConstants.ADD_ITEM)
                .addMultipartParameter("service_id", String.valueOf(service_id))
                .addMultipartParameter("name", name)
                .addMultipartParameter("description", description)
                .addMultipartFileList("image[]", fileList)
                .addHeaders("Authorization", token)
                .setTag("uploadTest").setPriority(Priority.HIGH).build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress

                    }
                }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgreesDilaog(getActiviy(), getString(R.string.additem_title), getString(R.string.add_item));

                try {

                    String message = response.getString("message");
                    int status = response.getInt("status");

                    if (status == 1) {
                        Toast.makeText(MyServicedetailsAactivity.this, "" + message, Toast.LENGTH_SHORT).show();
                        myServiceItemImagesAadapter.notifyDataSetChanged();
                        AddItemdialog.dismiss();
                        getMyServiceDetails(token, Service_id);

                    } else {

                        Toast.makeText(MyServicedetailsAactivity.this, " "+getString(R.string.noadding), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActiviy(), " "+getString(R.string.error), Toast.LENGTH_SHORT).show();

                }



                hideProgreesDilaog(getActiviy(), getString(R.string.additem_title), getString(R.string.add_item));
                Log.e("UPLODAING ", response.toString());

            }

            @Override
            public void onError(ANError error) {
                Log.e("error", error.getErrorDetail()+"");
                hideProgreesDilaog(getActiviy(), getString(R.string.additem_title), getString(R.string.add_item));
                Toast.makeText(getActiviy(), " "+getString(R.string.error), Toast.LENGTH_SHORT).show();

                // handle error
            }
        });


    }
    private ImagePicker getImagePicker() {

        ImagePicker imagePicker = ImagePicker.create(this).language("ar") // Set image picker language
                .theme(R.style.ImagePickerTheme)
                // set whether pick action or camera action should return immediate
                // result or not. Only works in single mode for image picker
                .toolbarArrowColor(Color.RED) // set toolbar arrow up color
                .toolbarFolderTitle("مجلد") // folder selection title
                .toolbarImageTitle("اختر صور") // image selection title
                .toolbarDoneButtonText("تم"); // done button text

        ImagePickerComponentHolder.getInstance().setImageLoader(new DefaultImageLoader());

        imagePicker.multi(); // multi mode (default mode)

        imagePicker.origin(UplodedImages); // original selected images, used in multi mode

        return imagePicker.limit(4)
                .language("ar")
                // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")

                // captured image directory name ("Camera" folder by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().getPath()); // can be full path
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RC_CAMERA) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            images = (ArrayList<Image>) ImagePicker.getImages(data);

            for (Image image : images) {
                try {
                    File f =  new Compressor(getActiviy()).compressToFile(new File(image.getPath()));
                    list.add(f);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            printImages(images);

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void start() {
        getImagePicker().start(); // start image picker activity with request code
    }

    private void captureImage() {
        ImagePicker.cameraOnly().start(this);
    }

    private void printImages(List<Image> images) {
        if (images == null) return;

        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0, l = images.size(); i < l; i++) {
            stringBuffer.append(images.get(i).getName()).append("\n");

        }

        mItemImage.setText(stringBuffer.toString());
       // Toast.makeText(this, "" + stringBuffer.toString(), Toast.LENGTH_SHORT).show();
    }



}
