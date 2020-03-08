package com.azimaty.azimatyapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azimaty.azimatyapp.Adapter.MyServiceItemImagesAadapter;
import com.azimaty.azimatyapp.Adapter.SubItemAdapterWhiteText;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Items_image_service;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyServicedetailsAactivity extends BaseActivity {

    private ImageButton mdeleteservice;
    private ImageButton mEdit;
    private ImageButton mVisble;
    private ImageView mBack;
    private ImageView mBackfround;
    private ImageView mProfileserviceimage;
    private RatingBar mRatingBar;
    private TextView mServicename;
    private RecyclerView mRvSubItem;
    private TextView mPhonenumber;
    private TextView mLocation;
    private RecyclerView mRvitems;
    GridLayoutManager gridLayoutManager;
    MyServiceItemImagesAadapter myServiceItemImagesAadapter;
    List<Items_image_service> items_image_services = new ArrayList<>();
    SubItemAdapterWhiteText subItemAdapterWhiteText;
    List<SubItem> subItemList;
    LinearLayoutManager linearLayoutManager;
    private ImageButton mDeleteservice;
    private Switch mServichecked;
    private TextView mAdditem;
    Dialog AddItemdialog;
    private ImageButton mButtonCancel;
    private TextView mTvRatingItem;
    private TextView mItemImage;
    private EditText mItemName;
    private EditText mItemDesc;
    private Button mAddbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_servicedetails_aactivity);
        mdeleteservice = findViewById(R.id.deleteservice);
        mEdit = findViewById(R.id.edit);
        mVisble = findViewById(R.id.visble);
        mBack = findViewById(R.id.back);
        mServicename = findViewById(R.id.servicename);
        mPhonenumber = findViewById(R.id.phonenumber);
        mLocation = findViewById(R.id.location);
        mRvitems = findViewById(R.id.Rvitems);
        mDeleteservice = findViewById(R.id.deleteservice);
        mBackfround = findViewById(R.id.backfround);
        mProfileserviceimage = findViewById(R.id.profileserviceimage);
        mRatingBar = findViewById(R.id.ratingBar);
        mServichecked = findViewById(R.id.servichecked);
        mAdditem = findViewById(R.id.additem);
        mRvSubItem=findViewById(R.id.rv_sub_item);

        mRvSubItem.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActiviy(), 3);
        mRvitems.setLayoutManager(gridLayoutManager);
        mRvitems.setHasFixedSize(true);
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

        myServiceItemImagesAadapter = new MyServiceItemImagesAadapter(getActiviy(), items_image_services);
        mRvitems.setAdapter(myServiceItemImagesAadapter);

        subItemList = new ArrayList<>();
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));

        linearLayoutManager = new LinearLayoutManager(getActiviy());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvSubItem.setLayoutManager(linearLayoutManager);

        subItemAdapterWhiteText = new SubItemAdapterWhiteText(subItemList);
        mRvSubItem.setAdapter(subItemAdapterWhiteText);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAdditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemdialog = new Dialog(getActiviy());
                AddItemdialog.setContentView(R.layout.adding_item_dialag);
                mButtonCancel = AddItemdialog.findViewById(R.id.buttonCancel);
                mTvRatingItem = AddItemdialog.findViewById(R.id.tv_rating_item);
                mItemImage = AddItemdialog.findViewById(R.id.item_image);
                mItemName = AddItemdialog.findViewById(R.id.item_name);
                mItemDesc = AddItemdialog.findViewById(R.id.item_desc);
                mAddbutton = AddItemdialog.findViewById(R.id.addbutton);
                AddItemdialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                WindowManager.LayoutParams p = AddItemdialog.getWindow().getAttributes();
                p.width = ViewGroup.LayoutParams.MATCH_PARENT;
                p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                AddItemdialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.END);

                /*WindowManager.LayoutParams layoutParams = AddItemdialog.getWindow().getAttributes();
                layoutParams.x = 0; // right margin
                layoutParams.y = 50; // top margin
                AddItemdialog.getWindow().setAttributes(layoutParams);*/
                AddItemdialog.setCancelable(true);


                AddItemdialog.show();

                mButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddItemdialog.dismiss();
                    }
                });


            }
        });

        mdeleteservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyServicedetailsAactivity.this, AddServiceActivity.class);
                intent.putExtra(AppConstants.KEY_TYPE, AppConstants.UPDATE_SERVICE_FOR_MENU);
                startActivity(intent);



            }
        });
        mVisble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyServicedetailsAactivity.this, ServicedetailsAactivity.class);
                startActivity(intent);

            }
        });


        mRvitems = findViewById(R.id.Rvitems);
    }
}
