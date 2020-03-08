package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.azimaty.azimatyapp.R;

public class AfterAddingServiceActivity extends AppCompatActivity {

    private TextView mTvadvertisawahats;
    private TextView mSucessaddtv;
    private ImageView mGotohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_adding_service);
        mTvadvertisawahats = findViewById(R.id.Tvadvertisawahats);
        mSucessaddtv = findViewById(R.id.sucessaddtv);
        mGotohome = findViewById(R.id.gotohome);
        mGotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AfterAddingServiceActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
