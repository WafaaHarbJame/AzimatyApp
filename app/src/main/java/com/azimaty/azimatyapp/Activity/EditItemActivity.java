package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.azimaty.azimatyapp.Adapter.MyServiceIEdittemImagesAadapter;
import com.azimaty.azimatyapp.Adapter.MyServiceItemImagesAadapter;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Items_image_service;
import com.azimaty.azimatyapp.Model.PsersonRating;
import com.azimaty.azimatyapp.Model.SliderItem;
import com.azimaty.azimatyapp.R;
import com.azimaty.azimatyapp.Utlities.UtilityApp;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerComponentHolder;
import com.esafirm.imagepicker.features.imageloader.DefaultImageLoader;
import com.esafirm.imagepicker.model.Image;

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

public class EditItemActivity extends BaseActivity {

    private ImageButton mButtonCancel;
    private TextView mTvRatingItem;
    private TextView mHintimage;
    private TextView mItemImage;
    private EditText mItemName;
    private EditText mItemDesc;
    private Button mUpdate;
    private RecyclerView mRvSubItem;
    private CircleImageView mImageView;
    private ArrayList<Image> UplodedImages = new ArrayList<>();
    private ArrayList<Image> images = new ArrayList<>();
    public   List<File> list = new ArrayList<>();;
    private static final int RC_CAMERA = 3000;
    LinearLayoutManager linearLayoutManager;
    String token;
    int Service_id;
    MyServiceIEdittemImagesAadapter myServiceIEdittemImagesAadapter;
    List<Items_image_service> items_image_services;
    int favorite_int;
    int item_id,list_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        mButtonCancel = findViewById(R.id.buttonCancel);
        mTvRatingItem = findViewById(R.id.tv_rating_item);
        mHintimage = findViewById(R.id.hintimage);
        mItemImage = findViewById(R.id.item_image);
        mItemName = findViewById(R.id.item_name);
        mItemDesc = findViewById(R.id.item_desc);
        mUpdate = findViewById(R.id.update);
        mRvSubItem = findViewById(R.id.rv_sub_item);
        mImageView = findViewById(R.id.imageView);

        token = UtilityApp.getUserToken();
        item_id = getIntent().getIntExtra(AppConstants.item_id, 0);
        list_id = getIntent().getIntExtra(AppConstants.list_id, 0);


        linearLayoutManager = new LinearLayoutManager(getActiviy());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRvSubItem.setLayoutManager(linearLayoutManager);
        mRvSubItem.setHasFixedSize(true);
        mRvSubItem.setNestedScrollingEnabled(false);
        items_image_services = new ArrayList<>();

        myServiceIEdittemImagesAadapter = new MyServiceIEdittemImagesAadapter
                (getActiviy(), items_image_services);
        getSingleItem(item_id);
        mItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImagePicker().start();
            }
        });


        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateListItems(list,token,item_id, mItemName.getText().toString(), mItemDesc.getText().toString(),
                        list_id);


            }
        });





        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        Toast.makeText(this, "" + stringBuffer.toString(), Toast.LENGTH_SHORT).show();
    }
    private void UpdateListItems(final  List<File> fileList,final String token,final int item_id, final String name,
                              final String description, final int service_id) {
        showProgreesDilaog(getActiviy(), getString(R.string.DELETEITEMTITLE), getString(R.string.DELETEITEM));
        AndroidNetworking.upload(AppConstants.EDITlist+item_id+"/edit")
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
                hideProgreesDilaog(getActiviy(), getString(R.string.DELETEITEMTITLE), getString(R.string.DELETEITEM));

                try {

                    String message = response.getString("message");
                    int status = response.getInt("status");

                    if (status == 1) {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_SHORT).show();
                       // myServiceItemImagesAadapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(getActiviy(), " " + message, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                hideProgreesDilaog(getActiviy(), getString(R.string.DELETEITEMTITLE), getString(R.string.DELETEITEM));
                Log.e("UPLODAING ", response.toString());

            }

            @Override
            public void onError(ANError error) {
                Log.e("error", error.getErrorDetail()+"");
                hideProgreesDilaog(getActiviy(), getString(R.string.DELETEITEMTITLE), getString(R.string.DELETEITEM));

                // handle error
            }
        });


    }
    public void getSingleItem(int list_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConstants.items_details + list_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray list = data.getJSONArray("list");
                        for (int j = 0; j < list.length(); j++) {
                            JSONObject jsonObjectlist = list.getJSONObject(j);
                            String name= jsonObjectlist.getString("name");
                            JSONArray item_images = jsonObjectlist.getJSONArray("image");
                            JSONArray item_comments = jsonObjectlist.getJSONArray("comment");
                            int countRating = jsonObjectlist.getInt("countRating");
                            boolean favorite = jsonObjectlist.getBoolean("favorite");
                            int item_id = jsonObjectlist.getInt("id");
                           String item_name = jsonObjectlist.getString("name");
                            String item_descriptione = jsonObjectlist.getString("description");
                            mItemDesc.setText(item_descriptione);
                            mItemName.setText(name);

                            if (favorite) {
                                favorite_int = 1;
                            } else {
                                favorite_int = 0;

                            }

                            for (int k = 0; k < item_images.length(); k++) {
                                JSONObject jsonObjectitem_images = item_images.getJSONObject(k);
                                int image_id = jsonObjectitem_images.getInt("id");
                                String image_url = jsonObjectitem_images.getString("name");
                                items_image_services.add(new Items_image_service(item_id, favorite_int, image_url, list_id));

                            }
                            hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

                        }

                        mRvSubItem.setAdapter(myServiceIEdittemImagesAadapter);

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

                Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();


                return map;

            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {


                return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }


}
