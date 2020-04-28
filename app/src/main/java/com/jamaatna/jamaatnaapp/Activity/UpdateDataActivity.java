package com.jamaatna.jamaatnaapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.AppHelper;
import com.jamaatna.jamaatnaapp.Model.MemberModel;
import com.jamaatna.jamaatnaapp.Model.VolleyMultipartRequest;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class UpdateDataActivity extends BaseActivity {

    private ImageButton mClose;
    private CircleImageView mImageView;
    private EditText mName;
    private TextView mPhonenumber;
    private TextView mPassword;
    boolean updateuserImage, updateuserimage, updateName = false;
    File COOKERIMAGEfile;
//    public SharedPManger sharedPManger;
    String uploaduserimageename = "";
    String token;
    boolean InternetConnect = false;
    private Button mUpdateDatat;

    MemberModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        mClose = findViewById(R.id.close);
        mImageView = findViewById(R.id.imageView);
        mName = findViewById(R.id.name);
        mPhonenumber = findViewById(R.id.phonenumber);
        mPassword = findViewById(R.id.password);
//        sharedPManger = new SharedPManger(getActiviy());
        token = UtilityApp.getUserToken();
        mUpdateDatat = findViewById(R.id.UpdateDatat);

        InternetConnect = CheckInternet();
        user= UtilityApp.getUserData();

        initData();


        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        mName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                updateName = true;
                return false;
            }
        });
        mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateName = true;

            }
        });

        mUpdateDatat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateName||updateuserImage){
                    UpdateData(token,mName.getText().toString());
                }
                else {

                    Toast(getString(R.string.nodatachanged));
                }

            }
        });


        mPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActiviy(), UpdatePasswardActivity.class);
                startActivity(intent);
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateuserImage = true;
                updateuserimage = true;

                PickImageDialog.build(new PickSetup().setTitle(getString(R.string.CHOOSE_PHOTO))
                        .setCancelText(getString(R.string.cancelTXT))
                        .setCameraButtonText(getString(R.string.cameratext))
                        .setGalleryButtonText(getString(R.string.gallaytext))
                        .setSystemDialog(false)

                ).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {


                        try {
                            COOKERIMAGEfile = new Compressor(UpdateDataActivity.this).compressToFile(new File(r.getPath()));

                            Picasso.with(UpdateDataActivity.this).
                                    load(COOKERIMAGEfile).
                                    into(mImageView);

                            if (r.getError() == null) {

                            } else {
                                //Handle possible errors
                                //TODO: do what you have to do with r.getError();
                               // Toast.makeText(UpdateDataActivity.this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                            Picasso.with(UpdateDataActivity.this).load(r.getUri()).error(R.drawable.profile_image).into(mImageView);

                        }

                        //  uploadAvatar();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                uploaduserimageename = COOKERIMAGEfile.getName();

                                Uploadfile(token, uploaduserimageename);

                            }
                        }, 1000);


                        if (r.getError() == null) {

                            mImageView.setImageBitmap(r.getBitmap());


                        } else {
                            //Handle possible errors
                            //TODO: do what you have to do with r.getError();
                          //  Toast.makeText(UpdateDataActivity.this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        //TODO: do what you have to...
                    }
                }).setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        //TODO: do what you have to if user clicked cancel
                    }
                }).show(UpdateDataActivity.this.getSupportFragmentManager());

            }

        });




    }

    private void Uploadfile(final String token, final String image) {

       final String id = "1";
        String url = AppConstants.UPDATE_PROFILE;
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);

                try {

                    JSONObject register_response = new JSONObject(resultResponse);

                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");

                    if (status == 1) {
                    //    Toast(message);
                        JSONObject data = register_response.getJSONObject("data");
                        JSONObject userJson = data.getJSONObject("user");
                        String photo = userJson.getString("photo");
                       // Toast("Log photo "+photo);
                        user.photo=photo;
                        UtilityApp.setUserData(user);


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
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("image", image + "");


                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if (mImageView == null) {
                }
                params.put("image", new DataPart(uploaduserimageename,
                        AppHelper.getFileDataFromDrawable(getActiviy(), mImageView.getDrawable()),
                        "image/jpg"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(multipartRequest);


    }


    public void UpdateData(final String token, final String name) {

        showProgreesDilaog(getActiviy(), getString(R.string.updatedata), getString(R.string.updatedataa));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.UPDATE_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("UpdateData", response);

                    if (status == 1) {

                        Toast(message);
                        JSONObject data = register_response.getJSONObject("data");
                        JSONObject userJson = data.getJSONObject("user");
                        String photo = userJson.getString("photo");
                        user.name=name;
                        user.photo=photo;
                        UtilityApp.setUserData(user);


                    } else {
                        Toast(message);

                    }
                    hideProgreesDilaog(getActiviy(), getString(R.string.updatedata), message);


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgreesDilaog(getActiviy(), getString(R.string.updatedata), getString(R.string.updatedataa));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideProgreesDilaog(getActiviy(), getString(R.string.updatedata), getString(R.string.updatedataa));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("name", name + "");

                return map;

            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap();
                header.put("Authorization",token);
                return header;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }


    public void getProfile(final String token) {
       showProgreesDilaog(getActiviy() ,getString(R.string.profiledata), getString(R.string.load_data));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.PROFILE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");
                        Log.e("id", user + "");
                        Log.e("id", data + "");
                        int id = user.getInt("id");
                        String name = user.getString("name");
                        String phone = user.getString("phone");

                        String photo = user.getString("photo");
                        Log.e("id", id + "");
                        Log.e("name", name + "");
                        Log.e("phone", phone + "");

                        mName.setText(name);
                        mPhonenumber.setText(phone);
                        Glide.with(getActiviy()).load(photo).centerCrop().placeholder(R.drawable.profile_image).into(mImageView);
                        hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));

                    } else {
                        Toast(message);

                    }

                    hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));


                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(getActiviy(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));
              //  Toast(error.getMessage());


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



    public void initData(){

        if(UtilityApp.isLogin()) {
            mName.setText(user.name);
            mPhonenumber.setText(user.phone);
            Picasso.with(UpdateDataActivity.this).
                    load(user.photo)
                    .placeholder(R.drawable.profile_image)
                    .into(mImageView);

//            Glide.with(getActiviy()).load(user.photo).centerCrop().placeholder(R.drawable.profile_image).into(mImageView);
        }

    }

}
