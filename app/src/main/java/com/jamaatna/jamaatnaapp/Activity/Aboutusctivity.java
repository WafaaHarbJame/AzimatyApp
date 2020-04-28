package com.jamaatna.jamaatnaapp.Activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jamaatna.jamaatnaapp.Api.MyApplication;
import com.jamaatna.jamaatnaapp.Model.AppConstants;
import com.jamaatna.jamaatnaapp.Model.Setting;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Aboutusctivity extends BaseActivity {

    private ImageButton mBack;
    private TextView mAboutustext;
    boolean InternetConnect = false;
    WebView webView;
    View lyt_failed;
    private Button mFailedRetry;
    Setting setting;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutusctivity);
        mBack = findViewById(R.id.back);
       // mAboutustext = findViewById(R.id.aboutustext);
        InternetConnect = CheckInternet();

        webView = findViewById(R.id.tvtext);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptEnabled(true);
        lyt_failed = findViewById(R.id.failed_home);
        mFailedRetry = lyt_failed.findViewById(R.id.failed_retry);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();



            }
        });



        setting = UtilityApp.getSettingData();
        if (setting != null) {
            String dataa = "<html><head></head><body>" + setting.getAbout_app() + "</body></html>";
            Log.e("Wafaa", dataa);
            webView.loadData(dataa, "text/html", null);


        } else {
            if (InternetConnect) {
                lyt_failed.setVisibility(View.GONE);

                getSetting();

            } else {
                lyt_failed.setVisibility(View.VISIBLE);


            }
        }








        mFailedRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyt_failed.setVisibility(View.GONE);
                getSetting();

            }
        });


    }
    public void getSetting() {
        showProgreesDilaog(getActiviy(), getString(R.string.load_data_tittle), getString(R.string.load_data));

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


                        String dataa = "<html><head></head><body>" + about_app + "</body></html>";
                        Log.e("Wafaa", dataa);
                        webView.loadData(dataa, "text/html", null);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                           // mAboutustext.setText(Html.fromHtml(about_app, Html.FROM_HTML_MODE_COMPACT));
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

               // Toast.makeText(getActiviy(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
