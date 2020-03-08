package com.azimaty.azimatyapp.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azimaty.azimatyapp.Adapter.CityAdapter;
import com.azimaty.azimatyapp.Adapter.ItemImagesAadapter;
import com.azimaty.azimatyapp.Adapter.SubItemAdapter;
import com.azimaty.azimatyapp.Adapter.SubItemAdapterWhiteText;
import com.azimaty.azimatyapp.Model.DataCallback;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.Items_image_service;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;

import java.util.ArrayList;
import java.util.List;

public class ServicedetailsAactivity extends BaseActivity {

    GridLayoutManager gridLayoutManager;
    ItemImagesAadapter itemImagesAadapter;
    List<Items_image_service> items_image_services = new ArrayList<>();
    SubItemAdapterWhiteText subItemAdapterWhiteText;
    List<SubItem> subItemList;
    LinearLayoutManager linearLayoutManager;
    private ImageButton mMBack;
    private TextView mPhonenumber;
    private TextView mLocation;
    private RecyclerView mRvitems;
    private ImageView mBackfround;
    private ImageView mProfileserviceimage;
    private RatingBar mRatingBar;
    private TextView mServicename;
    private RecyclerView mRvSubItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicedetails_aactivity);
        mMBack = findViewById(R.id.mBack);
        mPhonenumber = findViewById(R.id.phonenumber);
        mLocation = findViewById(R.id.location);
        mRvitems = findViewById(R.id.Rvitems);
        mMBack = findViewById(R.id.mBack);
        mBackfround = findViewById(R.id.backfround);
        mProfileserviceimage = findViewById(R.id.profileserviceimage);
        mRatingBar = findViewById(R.id.ratingBar);
        mServicename = findViewById(R.id.servicename);
        mRvSubItem = findViewById(R.id.rv_sub_item);
        mPhonenumber = findViewById(R.id.phonenumber);
        mLocation = findViewById(R.id.location);
        mRvitems = findViewById(R.id.Rvitems);


        mRvSubItem.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActiviy(), 3);
        mRvitems.setLayoutManager(gridLayoutManager);
        mRvitems.setHasFixedSize(true);
        mPhonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dailus("55555555555");
            }
        });
        mRvitems.setNestedScrollingEnabled(false);
        items_image_services.add(new Items_image_service(1, 1, "image"));
        items_image_services.add(new Items_image_service(2, 1, "image"));
        items_image_services.add(new Items_image_service(3, 1, "image"));
        items_image_services.add(new Items_image_service(4, 1, "image"));
        items_image_services.add(new Items_image_service(5, 1, "image"));
        items_image_services.add(new Items_image_service(1, 1, "image"));
        items_image_services.add(new Items_image_service(2, 1, "image"));
        items_image_services.add(new Items_image_service(3, 1, "image"));
        items_image_services.add(new Items_image_service(4, 1, "image"));
        items_image_services.add(new Items_image_service(5, 1, "image"));
        items_image_services.add(new Items_image_service(1, 1, "image"));
        items_image_services.add(new Items_image_service(2, 1, "image"));
        items_image_services.add(new Items_image_service(3, 1, "image"));
        items_image_services.add(new Items_image_service(4, 1, "image"));
        items_image_services.add(new Items_image_service(5, 1, "image"));

        itemImagesAadapter = new ItemImagesAadapter(getActiviy(), items_image_services);
        mRvitems.setAdapter(itemImagesAadapter);

        subItemList = new ArrayList<>();
        subItemList.add(new SubItem("ذبائح",1));


        linearLayoutManager = new LinearLayoutManager(getActiviy());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvSubItem.setLayoutManager(linearLayoutManager);

        subItemAdapterWhiteText = new SubItemAdapterWhiteText(subItemList);
        mRvSubItem.setAdapter(subItemAdapterWhiteText);

        mMBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
