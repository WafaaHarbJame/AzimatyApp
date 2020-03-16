package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.azimaty.azimatyapp.Api.MyApplication;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TermsAndconditionActivity extends BaseActivity {

    private ImageButton mBack;
    private TextView mConditiontext;
    boolean InternetConnect = false;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_andcondition);
        mBack = findViewById(R.id.back);
        mConditiontext = findViewById(R.id.conditiontext);
        InternetConnect = CheckInternet();
        webView = findViewById(R.id.tvtext);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });

        if (InternetConnect) {
            getSetting();

        } else {
            Toast(getString(R.string.checkInternet));


        }
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
                        String privacy_app = data.getString("privacy_app");
                        String about_app = data.getString("about_app");

//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            mConditiontext.setText(Html.fromHtml(privacy_app, Html.FROM_HTML_MODE_COMPACT));
//                        }
                        String dataa = "<html><head></head><body>" + privacy_app + "</body></html>";
                        Log.e("Wafaa", dataa);
                        webView.loadData(dataa, "text/html", null);


                        //hideProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));


                    } else {
                        Toast.makeText(getActiviy(), "" + message, Toast.LENGTH_LONG).show();

                    }


                    //hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


                } catch (JSONException e) {
                    e.printStackTrace();

                    //hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hideProgreesDilaog(getActiviy(), getString(R.string.logintitle), getString(R.string.loadlogin));


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
