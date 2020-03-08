package com.azimaty.azimatyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azimaty.azimatyapp.Adapter.ItemAdapter;
import com.azimaty.azimatyapp.Adapter.MyServiceAdapter;
import com.azimaty.azimatyapp.Fragments.AddServiceFragment;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyServiceActivity extends BaseActivity {

    private TextView mServicename;
    private ImageButton mAddservice;
    private ImageButton mMenu;
    private ImageView mBack;
    private RecyclerView mMyservicerecycler;
    ItemTouchHelper swipeToDismissTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);
        mServicename = findViewById(R.id.servicename);
        mAddservice = findViewById(R.id.addservice);
        mMenu = findViewById(R.id.menu);
        mBack = findViewById(R.id.back);
        mMyservicerecycler = findViewById(R.id.myservicerecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActiviy());
        final MyServiceAdapter myServiceAdapter = new MyServiceAdapter(buildItemList(), getActiviy());
        mMyservicerecycler.setAdapter(myServiceAdapter);
        mMyservicerecycler.setLayoutManager(layoutManager);


/*        swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // callback for drag-n-drop, false to skip this feature
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                buildItemList().remove(viewHolder.getAdapterPosition());
                myServiceAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });

        swipeToDismissTouchHelper.attachToRecyclerView(mMyservicerecycler);*/


        mAddservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyServiceActivity.this, AddServiceActivity.class);
                intent.putExtra(AppConstants.KEY_TYPE,AppConstants.ADD_SERVICE_FOR_MENU);
                startActivity(intent);
                finish();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });






    }




    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(getString(R.string.item_title), "gg", 5, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 4, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 3, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "gg", 5, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 4, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 3, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title), "hh", 2, getString(R.string.city), buildSubItemList()));

        return itemList;
    }
    private List<SubItem> buildSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));

        return subItemList;
    }
}
