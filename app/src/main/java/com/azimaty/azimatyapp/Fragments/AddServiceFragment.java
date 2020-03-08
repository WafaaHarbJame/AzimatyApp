package com.azimaty.azimatyapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.azimaty.azimatyapp.Activity.AfterAddingServiceActivity;
import com.azimaty.azimatyapp.Activity.HomeActivity;
import com.azimaty.azimatyapp.Model.AppConstants;
import com.azimaty.azimatyapp.Model.ChooseServiceTypeBottomDialog;
import com.azimaty.azimatyapp.Model.DataCallback;
import com.azimaty.azimatyapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddServiceFragment extends Fragment {


    private ImageButton mClose;
    private ImageView mImageView;
    private EditText mServivename;
    private TextView mServicetype;
    private EditText mServiceitem;
    private EditText mPhonenumber;
    private EditText mCity;
    private Button mSent;
    View root;
    boolean mServicetypeclick = false;
    String Servicetype;
    final boolean keepRunning1 = true;
    String type;
    private TextView mAddervicetv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add, container, false);
        // Inflate the layout for this fragment
        mClose = root.findViewById(R.id.close);
        mImageView = root.findViewById(R.id.imageView);
        mServivename = root.findViewById(R.id.servivename);
        mServicetype = root.findViewById(R.id.servicetype);
        mServiceitem = root.findViewById(R.id.serviceitem);
        mPhonenumber = root.findViewById(R.id.phonenumber);
        mCity = root.findViewById(R.id.city);
        mSent = root.findViewById(R.id.sent);
        mAddervicetv =  root.findViewById(R.id.addervicetv);

        Bundle bundle = getArguments();
        if(bundle != null){
            String type = bundle.getString(AppConstants.KEY_TYPE);
            if(type.equals(AppConstants.UPDATE_SERVICE_FOR_MENU)){
                mAddervicetv.setText(getString(R.string.updateservice));
                mSent.setText(getString(R.string.save));

            }

        }
        mServicetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mServicetypeclick = true;
                ChooseServiceTypeBottomDialog chooseServiceTypeBottomDialog = new ChooseServiceTypeBottomDialog(new DataCallback() {
                    @Override
                    public void dataResult(Object obj, String func, boolean IsSuccess) {
                        type = (String) obj;
                        mServicetype.setText(getServiceName(type));


                    }
                });
                chooseServiceTypeBottomDialog.show(getFragmentManager(), chooseServiceTypeBottomDialog.getTag());

            }
        });





        mSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mServivename.getText().toString().equals(null) || mServivename.getText().toString().equals("")) {

                    mServivename.setError(getString(R.string.mServivename));
                    mServivename.requestFocus();

                } else if (mServicetypeclick == false) {
                    mServicetype.setError(getString(R.string.mServicetype));
                    mServicetype.requestFocus();


                } else if (mServiceitem.getText().toString().equals(null) || mServiceitem.getText().toString().equals("")) {
                    mServiceitem.setError(getString(R.string.mServiceitem));
                    mServiceitem.requestFocus();
                } else if (mPhonenumber.getText().toString().equals(null) || mPhonenumber.getText().toString().equals("")) {
                    mPhonenumber.setError(getString(R.string.mPhonenumber));
                    mPhonenumber.requestFocus();
                } else if (mCity.getText().toString().equals(null) || mCity.getText().toString().equals("")) {
                    mCity.setError(getString(R.string.mCity));
                    mCity.requestFocus();
                } else {

                    Intent intent = new Intent(getActivity(), AfterAddingServiceActivity.class);
                    startActivity(intent);
                }
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
        return root;

    }

    public String getServiceName(String type) {
        switch (type) {
            case AppConstants.Family_service_type:
                return getString(R.string.family);
            case AppConstants.Hotles_service_type:
                return getString(R.string.hotles);

            case AppConstants.Cofee_service_type:
                return getString(R.string.cofeee);
            case AppConstants.Resturan_service_type:
                return getString(R.string.resturants);
            default:
                return getString(R.string.family);


        }


    }


}
