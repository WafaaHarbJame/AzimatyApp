package com.jamaatna.jamaatnaapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Activity.Aboutusctivity;
import com.jamaatna.jamaatnaapp.Activity.ContactUsActivity;
import com.jamaatna.jamaatnaapp.Activity.HomeActivity;
import com.jamaatna.jamaatnaapp.Activity.LoginActivity;
import com.jamaatna.jamaatnaapp.Activity.MyServiceActivity;
import com.jamaatna.jamaatnaapp.Activity.TermsAndconditionActivity;
import com.jamaatna.jamaatnaapp.Activity.UpdateDataActivity;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.MemberModel;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuFragment extends BaseFragment {


//    public SharedPManger sharedPManger;
//    boolean ISLOGIN = false;
    String token;
    boolean InternetConnect = false;
    private CircleImageView mMenuImage;
    private TextView mTvFullNameMenu;
    private TextView mTvPhonenumber;
    private ImageButton mEditData;
    private TextView mTvMyservice;
    private TextView mTvContactUs;
    private TextView mTvAboutus;
    private TextView mTvTermsconditionApp;
    private TextView mTvLogout;
    private ConstraintLayout mUserlogindata;

    MemberModel user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_mwnu, container, false);
        mMenuImage = root.findViewById(R.id.menuImage);
        mTvFullNameMenu = root.findViewById(R.id.tvFullNameMenu);
        mTvPhonenumber = root.findViewById(R.id.tv_Phonenumber);
        mEditData = root.findViewById(R.id.EditData);
        mTvMyservice = root.findViewById(R.id.tvMyservice);
        mTvContactUs = root.findViewById(R.id.tvContactUs);
        mTvAboutus = root.findViewById(R.id.tvAboutus);
        mTvTermsconditionApp = root.findViewById(R.id.tvTermsconditionApp);
        mTvLogout = root.findViewById(R.id.tvLogout);
        mUserlogindata = root.findViewById(R.id.userlogindata);
        InternetConnect = CheckInternet();

       token = UtilityApp.getUserToken();


        if (UtilityApp.isLogin()) {
            initData();
            mTvLogout.setText(getString(R.string.logout));
            mUserlogindata.setVisibility(View.VISIBLE);
            mTvMyservice.setVisibility(View.VISIBLE);


            mTvLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (InternetConnect) {

                        Logout(token);

                    } else {
                        Toast(getString(R.string.checkInternet));


                    }
                }
            });


        } else {

            mUserlogindata.setVisibility(View.GONE);
            mTvMyservice.setVisibility(View.GONE);
            mTvLogout.setText(getString(R.string.logintxt));
            mTvLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mUserlogindata.setVisibility(View.GONE);
                    mTvMyservice.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });

        }

        mTvMyservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyServiceActivity.class);
                startActivity(intent);
            }
        });


        mEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateDataActivity.class);
                startActivity(intent);
            }
        });

        mTvAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Aboutusctivity.class);
                startActivity(intent);
            }
        });

        mTvContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
        mTvTermsconditionApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TermsAndconditionActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void Logout(final String token) {
        showProgreesDilaog(getActiviy(), getString(R.string.logouttitle), getString(R.string.loadexit));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.LOGOUT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject register_response = new JSONObject(response);
                    String message = register_response.getString("message");
                    int status = register_response.getInt("status");
                    Log.e("WAFAA", response);

                    if (status == 1) {
                        UtilityApp.logOut();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_LONG).show();


                    }
                    hideProgreesDilaog(getActiviy(), getString(R.string.logouttitle), getString(R.string.loadexit));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgreesDilaog(getActiviy(), getString(R.string.logouttitle), getString(R.string.loadexit));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                hideProgreesDilaog(getActiviy(), getString(R.string.logouttitle), getString(R.string.loadexit));


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("Authorization",token);

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
                        mTvFullNameMenu.setText(name);
                        mTvPhonenumber.setText(phone);
                       Glide.with(getActivity()).load(photo).centerCrop().placeholder(R.drawable.profile_image).into(mMenuImage);
                        hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));

                    } else {
                        Toast(message);

                    }

                    hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));


                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            //    Toast(error.getMessage());

                hideProgreesDilaog(getActiviy(), getString(R.string.profiledata), getString(R.string.load_data));


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
                header.put("Authorization", "Bearer" + " " + token);
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
            user=UtilityApp.getUserData();
            mTvFullNameMenu.setText(user.name);
            mTvPhonenumber.setText(user.phone);

            Glide.with(getActivity())
                    .asBitmap()
                    .load(user.photo)
                    .placeholder(R.drawable.profile_image)
                    .into(mMenuImage);

//            Picasso.with(getActiviy())
//                    .load(user.photo).error(R.drawable.profile_image)
//                    .placeholder(R.drawable.profile_image).
//                    into(mMenuImage);
        }

    }


}
