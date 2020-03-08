package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.azimaty.azimatyapp.Fragments.MenuFragment;
import com.azimaty.azimatyapp.R;

public class ContactUsActivity extends BaseActivity {

    private ImageButton mMBack;
    private TextView mPhonenumber;
    private TextView mEmail;
    private TextView mFacebook;
    private TextView mTwitter;
    private TextView mInstagram;
    private TextView mTvadvertisawahats;
    private TextView mAdvertisawahats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        mMBack = findViewById(R.id.mBack);
        mPhonenumber = findViewById(R.id.phonenumber);
        mEmail = findViewById(R.id.email);
        mFacebook = findViewById(R.id.facebook);
        mTwitter = findViewById(R.id.twitter);
        mInstagram = findViewById(R.id.instagram);
        mTvadvertisawahats = findViewById(R.id.Tvadvertisawahats);
        mAdvertisawahats = findViewById(R.id.advertisawahats);
        mMBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();


            }
        });

        mPhonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailus("55555555555");
            }
        });
        mTvadvertisawahats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContatWhats("55555555555");
            }
        });
    }
}
