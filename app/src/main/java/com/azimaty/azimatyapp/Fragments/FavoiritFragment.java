package com.azimaty.azimatyapp.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azimaty.azimatyapp.Adapter.FavoiriteAdapter;
import com.azimaty.azimatyapp.Adapter.ItemAdapter;
import com.azimaty.azimatyapp.Model.Item;
import com.azimaty.azimatyapp.Model.SubItem;
import com.azimaty.azimatyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoiritFragment extends Fragment {

    private RecyclerView mBestrating;
    View root;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


                root=inflater.inflate(R.layout.fragment_favoirit, container, false);

        mBestrating = root.findViewById(R.id.bestrating);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        FavoiriteAdapter itemAdapter = new FavoiriteAdapter(buildItemList(),getContext());
        mBestrating.setAdapter(itemAdapter);
        mBestrating.setLayoutManager(layoutManager);
        return root;

    }

    private List<Item> buildItemList() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(getString(R.string.item_title),"gg",5,getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title1),"hh",4,getString(R.string.city),
                buildSubItemList2()));
        itemList.add(new Item(getString(R.string.item_title2),"hh",3,getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title3),"hh",2,getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title),"gg",5,getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title1),"hh",4,getString(R.string.city),
                buildSubItemList2()));
        itemList.add(new Item(getString(R.string.item_title2),"hh",3,getString(R.string.city), buildSubItemList()));
        itemList.add(new Item(getString(R.string.item_title3),"hh",2,getString(R.string.city), buildSubItemList()));

        return itemList;
    }


    private List<SubItem> buildSubItemList() {
        List<SubItem> subItemList = new ArrayList<>();
        subItemList.add(new SubItem("مندي",1));
        subItemList.add(new SubItem("مندي",1));



        return subItemList;
    }
    private List<SubItem> buildSubItemList2() {
        List<SubItem> subItemList = new ArrayList<>();
        subItemList.add(new SubItem("عزايم",2));
        subItemList.add(new SubItem("عزايم",2));


        return subItemList;
    }
    private List<SubItem> buildSubItemList3() {
        List<SubItem> subItemList = new ArrayList<>();
        subItemList.add(new SubItem("قاعة الندى",1));
        subItemList.add(new SubItem("قاعة الندى",1));



        return subItemList;
    }
}
