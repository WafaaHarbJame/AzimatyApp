package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.azimaty.azimatyapp.R;

public class TermsAndconditionActivity extends BaseActivity {

    private ImageButton mBack;
    private TextView mConditiontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_andcondition);
        mBack = findViewById(R.id.back);
        mConditiontext = findViewById(R.id.conditiontext);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });
    }
}
