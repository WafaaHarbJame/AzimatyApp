package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.azimaty.azimatyapp.Fragments.MenuFragment;
import com.azimaty.azimatyapp.R;

public class Aboutusctivity extends BaseActivity {

    private ImageButton mBack;
    private TextView mAboutustext;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutusctivity);
        mBack = findViewById(R.id.back);
        mAboutustext = findViewById(R.id.aboutustext);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();



            }
        });
    }


}
