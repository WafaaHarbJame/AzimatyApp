package com.jamaatna.jamaatnaapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerComponentHolder;
import com.esafirm.imagepicker.features.imageloader.DefaultImageLoader;
import com.esafirm.imagepicker.model.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

public class UploadActivity extends AppCompatActivity {

    private static final int RC_CAMERA = 3000;
    private Button mButton;
    private ArrayList<Image> images = new ArrayList<>();
    private TextView mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImagePicker().start(); // start image picker activity with request code


            }
        });
        mTextView2 = findViewById(R.id.textView2);
    }

    private ImagePicker getImagePicker() {

        ImagePicker imagePicker = ImagePicker.create(this).language("in") // Set image picker language
                .theme(R.style.ImagePickerTheme)
                // set whether pick action or camera action should return immediate
                // result or not. Only works in single mode for image picker
                .toolbarArrowColor(Color.RED) // set toolbar arrow up color
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarDoneButtonText("DONE"); // done button text

        ImagePickerComponentHolder.getInstance().setImageLoader(new DefaultImageLoader());

        imagePicker.multi(); // multi mode (default mode)

        imagePicker.origin(images); // original selected images, used in multi mode

        return imagePicker.limit(4) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
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
           List<File> list = new ArrayList<>();
//            for (Image image : images) {
//                try {
//                    File f =  new Compressor(UploadActivity.this).compressToFile(new File(image.getPath()));
//                    list.add(f);
            File  ImageLogo= null;
            try {
                ImageLogo = new Compressor(UploadActivity.this).compressToFile(new File(images.get(0).getPath()));
                EditService(UtilityApp.getUserToken(),73,"ddgfg",1+"","hhhhhhhh",1+"",1+"",ImageLogo);

            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }


            //Upload(list);
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


            stringBuffer.append(images.get(i).getPath()).append("\n");




        }
        mTextView2.setText(stringBuffer.toString());
    }

    public void Upload(List<File> fileList) {
        AndroidNetworking.upload(AppConstants.ADD_ITEM)
                .addMultipartParameter("service_id", String.valueOf(19))
                .addMultipartParameter("name", "fffff")
                .addMultipartParameter("description", "description")
                .addMultipartFileList("image[]", fileList)
                .addHeaders("Authorization", UtilityApp.getUserToken())
                .setTag("uploadTest").setPriority(Priority.HIGH).build()
                .setUploadProgressListener(new UploadProgressListener() {
            @Override
            public void onProgress(long bytesUploaded, long totalBytes) {
                // do anything with progress

            }
        }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("UPLODAING ", response.toString());
                // do anything with response
            }

            @Override
            public void onError(ANError error) {
                Log.e("error", error.getErrorDetail()+"");
                // handle error
            }
        });
    }


    public void EditService( String token, int service_id,  String name,
                             String category_id,  String tag,
                             String city_id,  String status,  File ImageLogo){

        Log.e("wafaa",ImageLogo.getName());
        AndroidNetworking.upload(AppConstants.EDITService+service_id+"/edit")
                .addMultipartFile("logo",ImageLogo)
                .addMultipartParameter("name", name)
                .addMultipartParameter("category_id", category_id)
                .addMultipartParameter("tag", tag)
                .addMultipartParameter("city_id", city_id)
                .addMultipartParameter("status", status)
                .addHeaders("Authorization", token)
                .setTag("uploadTest").setPriority(Priority.HIGH)
                .build().setUploadProgressListener(new UploadProgressListener() {
            @Override
            public void onProgress(long bytesUploaded, long totalBytes) {
                // do anything with progress
            }
        }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", response.toString());


                try {

                    String message = response.getString("message");
                    int status = response.getInt("status");

                    if (status == 1) {
                        Toast.makeText(UploadActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(UploadActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }

            @Override
            public void onError(ANError error) {
                Log.e("error wafaa", error.getErrorDetail()+"wafaaaa");

                // handle error
            }
        });



    }
}
