package com.jamaatna.jamaatnaapp.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import com.jamaatna.jamaatnaapp.Activity.AfterAddingServiceActivity;
import com.jamaatna.jamaatnaapp.Activity.HomeActivity;
import com.jamaatna.jamaatnaapp.Activity.LoginActivity;
import com.jamaatna.jamaatnaapp.Activity.MyServiceActivity;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.AppHelper;
import com.jamaatna.jamaatnaapp.Model.Catogories;
import com.jamaatna.jamaatnaapp.Model.ChooseServiceTypeBottomDialog;
import com.jamaatna.jamaatnaapp.Model.DataCallback;
import com.jamaatna.jamaatnaapp.Model.Setting;
import com.jamaatna.jamaatnaapp.Model.SubItem;
import com.jamaatna.jamaatnaapp.Model.VolleyMultipartRequest;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddServiceFragment extends BaseFragment {


    final boolean keepRunning1 = true;
    View root;
    boolean mServicetypeclick = false;
    String Servicetype;
    String type;
    int catogory_id = 1;
    String catogory_nam;
    boolean updateuserImage, updateuserimage, updateName = false;
    File COOKERIMAGEfile;
    File UdateServiceLogo;

    //    public SharedPManger sharedPManger;
    String uploaduserimageename = "";
    String token;
    Dialog CityDialag;

    List<String> citylist = new ArrayList<String>();
    List<SubItem> citiesModelList = new ArrayList<>();
    Dialog dialog;

    String logo;
    Setting setting;

    String tax="0";
    int selectedCityId = 0;
    boolean InternetConnect = false;
    int service_on_off = 0;
    private ImageButton mClose;
    private CircleImageView mImageView;
    private EditText mServivename;
    private TextView mServicetype;
    private EditText mServiceitem;
    private EditText mPhonenumber;
    private Spinner citySpinner;
    private Button mSent;
    private TextView mAddervicetv;
    private Switch mServichecked;
    private LinearLayout mServiceOnOff;
    private Button mSave;
    int my_service_id;
    int uploadimage = 0;

    boolean IsUdateServicelogo;
    private TextView mIsserviceon;
    TextView tVConditionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add, container, false);
        // Inflate the layout for this fragment
        mClose = root.findViewById(R.id.close);
        mImageView = root.findViewById(R.id.imageView);
        mServivename = root.findViewById(R.id.servivename);
        mServicetype = root.findViewById(R.id.servicetype);
        mServiceitem = root.findViewById(R.id.serviceitem);
        mPhonenumber = root.findViewById(R.id.phonenumber);
        mSent = root.findViewById(R.id.sent);
        mAddervicetv = root.findViewById(R.id.addervicetv);
        mServichecked = root.findViewById(R.id.servichecked);
        mServiceOnOff = root.findViewById(R.id.Service_on_off);
        citySpinner = root.findViewById(R.id.citySpinner);
        InternetConnect = CheckInternet();
        token = UtilityApp.getUserToken();
        mSave = root.findViewById(R.id.save);
        mIsserviceon = root.findViewById(R.id.isserviceon);


        if (InternetConnect) {

            getCacheCities();

        } else {
            Toast(getString(R.string.checkInternet));

        }


        citySpinner.setOnItemSelectedListener(new AdapterView.
                OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position > 0) {
                    selectedCityId = citiesModelList.get(position - 1).getId();
                    //  Toast("city id " + selectedCityId);p
                } else {
                    selectedCityId = 0;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mServichecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mServichecked.isChecked()) {
                    service_on_off = 1;
                    Log.e("service_on_offisChecked", String.valueOf(service_on_off) + "");

                } else {
                    service_on_off = 0;
                    Log.e("service_on_offnot", String.valueOf(service_on_off) + "");

                }
            }

        });


        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString(AppConstants.KEY_TYPE);

            if (type.equals(AppConstants.UPDATE_SERVICE_FOR_MENU)) {
                my_service_id = bundle.getInt(AppConstants.my_service_id);

                getSingleService(my_service_id, token);
                mServiceOnOff.setVisibility(View.VISIBLE);
                mAddervicetv.setText(getString(R.string.updateservice));
                mSent.setVisibility(View.GONE);
                mSave.setVisibility(View.VISIBLE);


                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (IsUdateServicelogo) {
                            Log.e("token", token);
                            Log.e("my_service_id", my_service_id + "");
                            Log.e("mServicetype", mServicetype + "");
                            Log.e("token", mServicetype + "");
                            Log.e("catogory_id", catogory_id + "");
                            Log.e("city_id", selectedCityId + "");
                            Log.e("service_on_off", String.valueOf(service_on_off) + "");
                            Log.e("UdateServiceLogo", UdateServiceLogo.getName());

                            if (InternetConnect) {

                                EditService(token, my_service_id, mServivename.getText().toString(), catogory_id + "", mServiceitem.getText().toString(), selectedCityId + "", String.valueOf(service_on_off), UdateServiceLogo);

                            } else {
                                Toast(getString(R.string.checkInternet));


                            }


                        } else {


                            if (InternetConnect) {

                                EditServicewithoutlogo(token, my_service_id, mServivename.getText().toString(), catogory_id + "", mServiceitem.getText().toString(), selectedCityId + "", String.valueOf(service_on_off));

                            } else {
                                Toast(getString(R.string.checkInternet));


                            }

                        }


                    }
                });

            }

        }


//        mCity.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View v) {
//
//                CityDialag = new Dialog(getActivity());
//                CityDialag.setContentView(R.layout.city_dialag);
//                CityDialag.setCancelable(true);
//
//                Spinner city_spinner = CityDialag.findViewById(R.id.CityDialag);
//                ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, citylist);
//                city_spinner.setAdapter(cityadapter);
//                cityadapter.notifyDataSetChanged();
//                city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        city_id = citiesBeanArrayList.get(i).getId();
//                        mCity.setText(citiesBeanArrayList.get(i).getName().toString());
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//                CityDialag.show();
//
//
//            }
//        });


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePermission();

                if (type.equals(AppConstants.UPDATE_SERVICE_FOR_MENU)) {
                    UploadingfromgallaeryUpdate();

                } else {
                    Uploadingfromgallaeryadd();
                }

            }
        });


        mServicetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mServicetypeclick = true;
                ChooseServiceTypeBottomDialog chooseServiceTypeBottomDialog = new ChooseServiceTypeBottomDialog(new DataCallback() {
                    @Override
                    public void dataResult(Object obj, String func, boolean IsSuccess) {
                        Catogories catogories = (Catogories) obj;
                        catogory_id = catogories.getCatogory_id();
                        catogory_nam = catogories.getCatogory_nam();
                        type = catogory_nam;
                        mServicetype.setText(getServiceName(type));


                    }
                });
                chooseServiceTypeBottomDialog.show(getFragmentManager(), chooseServiceTypeBottomDialog.getTag());

            }
        });


        mSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serviceNameStr = mServivename.getText().toString();
                String serviceItemStr = mServiceitem.getText().toString();
                String serviceTypeStr = mServicetype.getText().toString();
                String phoneNumberStr = mPhonenumber.getText().toString();

                if (serviceNameStr.isEmpty()) {
                    mServivename.setError(getString(R.string.mServivename));
                    mServivename.requestFocus();
                    return;
                }

                if (serviceTypeStr.isEmpty()) {
                    mServicetype.setError(getString(R.string.mServicetype));
                    mServicetype.requestFocus();
                    return;
                }

                if (serviceItemStr.isEmpty()) {
                    mServiceitem.setError(getString(R.string.mServiceitem));
                    mServiceitem.requestFocus();
                    return;
                }

                if (phoneNumberStr.isEmpty()) {
                    mPhonenumber.setError(getString(R.string.mPhonenumber));
                    mPhonenumber.requestFocus();
                    return;
                }


                if (selectedCityId == 0) {
                    Toast(getString(R.string.mCity));
                    return;
                }


                if (uploadimage == 0) {
                    Toast(getString(R.string.clicktoadd_photo));
                    return;
                }
                if (UtilityApp.isLogin()) {

                    //  String token = UtilityApp.getUserToken();


                    if (InternetConnect) {
                        ShowTermsAndCondition();


                    } else {
                        Toast(getString(R.string.checkInternet));


                    }


                    // AddingService(token, mServicetype.getText().toString(), catogory_id + "", mServiceitem.getText().toString(), city_id + "");


                } else {
                    Toast.makeText(getActiviy(), "" + getString(R.string.you_must_login_toadd), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActiviy(), LoginActivity.class);
                    startActivity(intent);

                }


            }

        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
        return root;


    }

    public String getServiceName(String type) {
        switch (type) {
            case AppConstants.Family_service_type:
                return getString(R.string.family);
            case AppConstants.Hotles_service_type:
                return getString(R.string.hotles);

            case AppConstants.Cofee_service_type:
                return getString(R.string.cofeee);
            case AppConstants.Resturan_service_type:
                return getString(R.string.resturants);
            default:
                return getString(R.string.family);


        }


    }

    private void getCities() {

        citiesModelList = new ArrayList<>();
        citiesModelList.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.CITIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                citylist.clear();
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {

                        JSONObject data = register_response.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("cities");

                        citylist.add(getString(R.string.choose_city));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            SubItem subItem = new SubItem(name, id);
                            citiesModelList.add(subItem);
                            citylist.add(name);
                        }

                        UtilityApp.setCitiesData(citiesModelList);

                        initCitySpinner(0);

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

                //   Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));
                Toast.makeText(getActiviy(), " " + getString(R.string.error), Toast.LENGTH_SHORT).show();


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

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    private void getCacheCities() {

        citiesModelList = UtilityApp.getCitiesData();
        if (citiesModelList == null) {
            getCities();
        } else {

            citylist.add(getString(R.string.choose_city));
            for (SubItem subItem : citiesModelList) {
                citylist.add(subItem.getSubItemTitle());
            }
            initCitySpinner(0);

        }
    }

    private void initCitySpinner(int pos) {

        ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, citylist);
        citySpinner.setAdapter(cityadapter);
        citySpinner.setSelection(pos);

    }

    public void getSingleService(final int service_id, final String token) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.SIGNAL_SERVICE + service_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                            int service_id = jsonObject.getInt("id");
                            JSONArray list = jsonObject.getJSONArray("list");
                            JSONObject category_id_json = jsonObject.getJSONObject("category_id");
                            catogory_id = category_id_json.getInt("id");
                            String Servicetype = category_id_json.getString("name");
                            //Toast(+catogory_id+Servicetype);
                            if (catogory_id == 1) {
                                mServicetype.setText(getString(R.string.family));
                            }
                            if (catogory_id == 2) {
                                mServicetype.setText(getString(R.string.cofeee));
                            }
                            if (catogory_id == 3) {
                                mServicetype.setText(getString(R.string.hotles));
                            }
                            if (catogory_id == 4) {
                                mServicetype.setText(getString(R.string.resturants));
                            }
                            String name = jsonObject.getString("name");
                            String phone = jsonObject.getString("phone");
                            logo = jsonObject.getString("logo");
                            JSONObject city = jsonArray.getJSONObject(i).getJSONObject("city");
                            selectedCityId = city.getInt("id");
                            int rating = jsonObject.getInt("rating");
                            String city_name = city.getString("name");
                            String tag = jsonObject.getString("tag");
                            boolean Services_status = jsonObject.getBoolean("status");
                            if (Services_status) {
                                mServichecked.setChecked(true);
                                mIsserviceon.setText(getString(R.string.serviceava));
                            } else {
                                mServichecked.setChecked(false);
                                mIsserviceon.setText(getString(R.string.serviceavaoff));

                            }
                            mPhonenumber.setText(phone);
                            mServivename.setText(name);
                            // mCity.setText(city_name);
                            mServiceitem.setText(tag);

                            Picasso.with(getActiviy()).load(logo).error(R.drawable.imagedetails).into(mImageView);


                        }

                        if (citiesModelList != null) {
                            int pos = 0;
                            for (int i1 = 0; i1 < citiesModelList.size(); i1++) {
                                if (selectedCityId == citiesModelList.get(i1).getId()) pos = i1 + 1;
                            }
                            initCitySpinner(pos);
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

                //   Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));
                Toast.makeText(getActiviy(), " " + getString(R.string.error), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();


                return map;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    public void UpdateService(final String token, final String name, final String category_id, final String tag, final String city_id, final String status, final String logo) {
        showProgreesDilaog(getActiviy(), getString(R.string.addervice), getString(R.string.ADDINGSERVICE));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.AddService, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), AfterAddingServiceActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG).show();


                    }
                    hideProgreesDilaog(getActiviy(), getString(R.string.addervice), getString(R.string.ADDINGSERVICE));


                } catch (JSONException e) {
                    e.printStackTrace();
                    // Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgreesDilaog(getActiviy(), getString(R.string.addervice), getString(R.string.ADDINGSERVICE));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(), getString(R.string.addervice), getString(R.string.ADDINGSERVICE));
                Toast.makeText(getActiviy(), " " + getString(R.string.error), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("Authorization", token);
                map.put("name", name);
                map.put("category_id", category_id + "");
                map.put("city_id", city_id + "");
                map.put("tag", tag + "");
                map.put("status", status + "");
                map.put("logo", logo + "");

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


    private void UpdateServices(final String token, final int service_id, final String name, final String category_id, final String tag, final String city_id, final String status, final String logo) {
        ;


        showProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));
        final String id = "1";
        String url = AppConstants.EDITService + service_id + "/edit";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));
                String resultResponse = new String(response.data);
                //Toast.makeText(getActivity(), ""+resultResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    String message = obj.getString("message");
                    Log.e("data Response Update", "data Response Update" + resultResponse);

                    int status = obj.getInt("status");

                    if (status == 1) {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), " " + message, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));


                    //showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));
                Toast.makeText(getActiviy(), " " + getString(R.string.error), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //params.put("id", id);
                params.put("name", name);
                params.put("category_id", category_id + "");
                params.put("tag", tag + "");
                params.put("city_id", city_id + "");
                params.put("logo", logo + "");
                params.put("status", status + "");


                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
//                headers.put("Content-Type", "application/json");
//                headers.put("Accept", "application/json");

                return headers;
            }

            /*@Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }*/

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                params.put("logo", new DataPart(uploaduserimageename, AppHelper.getFileDataFromDrawable(getContext(), mImageView.getDrawable()), "image/*"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(multipartRequest);


    }


    private void AddServiceandlogo(final String token, final String logo, final String name, final String category_id, final String tag, final String city_id) {
        ;
        showProgreesDilaog(getActiviy(), getString(R.string.ADDINGSERVICEtitle), getString(R.string.ADDINGSERVICE));

        final String id = "1";

        String url = AppConstants.AddService;
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                hideProgreesDilaog(getActiviy(), getString(R.string.ADDINGSERVICEtitle), getString(R.string.ADDINGSERVICE));
                String resultResponse = new String(response.data);
                //Toast.makeText(getActivity(), ""+resultResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    String message = obj.getString("message");
                    Log.e("data Response Update", "data Response Update" + resultResponse);

                    int status = obj.getInt("status");

                    if (status == 1) {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), AfterAddingServiceActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(), "" + getString(R.string.adding), Toast.LENGTH_SHORT).show();

                    }

                    hideProgreesDilaog(getActiviy(), getString(R.string.ADDINGSERVICEtitle), getString(R.string.ADDINGSERVICE));


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgreesDilaog(getActiviy(), getString(R.string.ADDINGSERVICEtitle), getString(R.string.ADDINGSERVICE));
                    Toast.makeText(getActivity(), "" + getString(R.string.adding), Toast.LENGTH_SHORT).show();

                    //showUploadSnackBar();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgreesDilaog(getActiviy(), getString(R.string.ADDINGSERVICEtitle), getString(R.string.ADDINGSERVICE));
                Toast.makeText(getActiviy(), " " + getString(R.string.error), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //params.put("id", id);
                params.put("name", name);
                params.put("category_id", category_id + "");
                params.put("tag", tag + "");
                params.put("city_id", city_id + "");
                params.put("logo", logo + "");


                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);


                return headers;
            }

            /*@Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }*/

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                params.put("logo", new DataPart(uploaduserimageename, AppHelper.getFileDataFromDrawable(getContext(), mImageView.getDrawable()), "image/*"));
                return params;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MyApplication.getInstance().addToRequestQueue(multipartRequest);


    }


    public void Uploadingfromgallaeryadd() {
        updateuserImage = true;
        updateuserimage = true;
        uploadimage = 1;

        PickImageDialog.build(new PickSetup().setTitle(getString(R.string.CHOOSE_PHOTO)).setCancelText(getString(R.string.cancelTXT)).setCameraButtonText(getString(R.string.cameratext)).setGalleryButtonText(getString(R.string.gallaytext)).setSystemDialog(false)

        ).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {

                try {
                    COOKERIMAGEfile = new Compressor(getContext()).compressToFile(new File(r.getPath()));
                    Picasso.with(getContext()).
                            load(COOKERIMAGEfile).
                            into(mImageView);
                    uploaduserimageename = COOKERIMAGEfile.getName().toString();

                    if (r.getError() == null) {


                    } else {
                        //Handle possible errors
                        //TODO: do what you have to do with r.getError();
                        Toast.makeText(getActiviy(), " " + getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    Picasso.with(getActivity()).load(r.getUri()).error(R.drawable.profile_image)
                            // .resize(100,100)
                            .into(mImageView);


                }


                //  uploadAvatar();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(uploaduserimageename!=null){
                            uploaduserimageename = COOKERIMAGEfile.getName().toString();

                        }




                    }
                }, 1000);


                if (r.getError() == null) {

                    mImageView.setImageBitmap(r.getBitmap());


                } else {
                    //Handle possible errors
                    //TODO: do what you have to do with r.getError();
                    Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
                //TODO: do what you have to...
            }
        }).setOnPickCancel(new IPickCancel() {
            @Override
            public void onCancelClick() {
                //TODO: do what you have to if user clicked cancel
            }
        }).show(getActivity().getSupportFragmentManager());

    }

    public void UploadingfromgallaeryUpdate() {

        IsUdateServicelogo = true;


        PickImageDialog.build(new PickSetup()).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {

                try {
                    UdateServiceLogo = new Compressor(getContext()).compressToFile(new File(r.getPath()));
                    Picasso.with(getContext()).
                            load(UdateServiceLogo).
                            into(mImageView);
                    if (r.getError() == null) {


                    } else {
                        //Handle possible errors
                        //TODO: do what you have to do with r.getError();
                        Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    Picasso.with(getActivity()).load(r.getUri()).error(R.drawable.profile_image).into(mImageView);

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 1000);


                if (r.getError() == null) {

                    mImageView.setImageBitmap(r.getBitmap());


                } else {
                    //Handle possible errors
                    //TODO: do what you have to do with r.getError();
                    Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
                //TODO: do what you have to...
            }
        }).setOnPickCancel(new IPickCancel() {
            @Override
            public void onCancelClick() {
                //TODO: do what you have to if user clicked cancel
            }
        }).show(getActivity().getSupportFragmentManager());

    }


    public void EditService(String token, int service_id, String name, String category_id, String tag, String city_id, String status, File ImageLogo) {

        Log.e("wafaa", ImageLogo.getName());
        showProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));
        AndroidNetworking.upload(AppConstants.EDITService + service_id + "/edit").addMultipartFile("logo", ImageLogo).addMultipartParameter("name", name).addMultipartParameter("category_id", category_id).addMultipartParameter("tag", tag).addMultipartParameter("city_id", city_id).addMultipartParameter("status", status).addHeaders("Authorization", token).setTag("uploadTest").setPriority(Priority.HIGH).build().setUploadProgressListener(new UploadProgressListener() {
            @Override
            public void onProgress(long bytesUploaded, long totalBytes) {
                // do anything with progress
            }
        }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));


                try {

                    String message = response.getString("message");
                    int status = response.getInt("status");

                    if (status == 1) {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MyServiceActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getActiviy(), " " + message, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));

            }

            @Override
            public void onError(ANError error) {
                Log.e("error wafaa", error.getErrorDetail() + "wafaaaa");
                hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));
                Toast.makeText(getActiviy(), "" + error.getErrorDetail(), Toast.LENGTH_SHORT).show();

                // handle error
            }
        });


    }


    public void EditServicewithoutlogo(final String token, final int service_id, final String name, final String category_id, final String tag, final String city_id, final String status) {
        showProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));

        AndroidNetworking.post(AppConstants.EDITService + service_id + "/edit").addBodyParameter("name", name).addBodyParameter("category_id", category_id).addBodyParameter("tag", tag).addBodyParameter("city_id", city_id).addBodyParameter("status", status).addHeaders("Authorization", token).setTag("uploadTest").setPriority(Priority.HIGH).build().setUploadProgressListener(new UploadProgressListener() {
            @Override
            public void onProgress(long bytesUploaded, long totalBytes) {
                // do anything with progress

            }
        }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));


                try {

                    String message = response.getString("message");
                    int status = response.getInt("status");

                    if (status == 1) {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MyServiceActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActiviy(), " " + message, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));
                Log.e("UPLODAING ", response.toString());

            }

            @Override
            public void onError(ANError error) {
                Log.e("error", error.getErrorDetail() + "");
                hideProgreesDilaog(getActiviy(), getString(R.string.UPDATESERVICETITILE), getString(R.string.UPDATESERVICE));

                // handle error
            }
        });


    }

    public void ShowTermsAndCondition() {
        setting = UtilityApp.getSettingData();
        if (setting != null) {
             tax = setting.getTax();
        }
        else {
            getSetting();

        }
        dialog = new Dialog(getActiviy());
        dialog.setContentView(R.layout.terms_condition_foradding_service);
        Button agree = dialog.findViewById(R.id.agree);
        Button reject = dialog.findViewById(R.id.reject);
        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        tVConditionText=dialog.findViewById(R.id.tVConditionText);
        tVConditionText.setText(tax);
        layoutParams.x = 0; // right margin
        layoutParams.y = 50; // top margin
        dialog.getWindow().setAttributes(layoutParams);
        dialog.setCancelable(true);
                   /*String dataa = "<html><head></head><body>" + text + "</body></html>";
                   Log.e("Wafaa", dataa);*/

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AddServiceandlogo(token, uploaduserimageename, mServivename.getText().toString(), catogory_id + "", mServiceitem.getText().toString(), selectedCityId + "");


            }
        });


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();


    }


    public void takePermission() {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {

                }
                if (report.isAnyPermissionPermanentlyDenied()) {
                    Toast.makeText(getActivity(), "لا يمكنك عمل بث بدون الموافقة على هذه الصلاحيات ", Toast.LENGTH_SHORT).show();

                }



                /* ... */
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                }).onSameThread().check();

    }

    public void getSetting() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.setting , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);
                    if (status == 1) {
                        JSONObject data = register_response.getJSONObject("data");
                         tax = data.getString("tax");

                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }




                } catch (JSONException e) {
                    e.printStackTrace();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();


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







