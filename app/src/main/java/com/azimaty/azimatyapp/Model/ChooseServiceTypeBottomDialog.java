package com.azimaty.azimatyapp.Model;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.azimaty.azimatyapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ChooseServiceTypeBottomDialog extends BottomSheetDialogFragment {

    View viewDialog;
    Button buttonEditPassword;
    ImageButton dialogButtonCancel;
    EditText dialogOldPassword, dialogNewPassword, dialogConfirmPassword;
    ProgressDialog progressDialog;
    String token;
    SharedPreferences sharedPreferences;
    String choosing_langauge;
    private ImageButton mButtonCancel;
    private TextView mTvChooselang;
    private TextView mTvSave;
    private TextView mFamily;
    private ImageView mSelectedar;
    private TextView mCofee;
    private ImageView mSelectecofee;
    private TextView mHotles;
    private ImageView mSelectehotles;
    private TextView mResturants;
    private ImageView mSelectresturants;
//    boolean selectfamily,selectcofee,selecthotls,selectresturant=false;
    String selectedType;
    DataCallback dataCallback;

    public ChooseServiceTypeBottomDialog(DataCallback callback) {
        this.dataCallback=callback;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        viewDialog = View.inflate(getContext(), R.layout.choose_service_daialg, null);
        dialog.setContentView(viewDialog);
        mButtonCancel = viewDialog.findViewById(R.id.buttonCancel);
        mTvChooselang = viewDialog.findViewById(R.id.tvChooselang);
        mTvSave = viewDialog.findViewById(R.id.tvSave);
        mFamily = viewDialog.findViewById(R.id.family);
        mSelectedar = viewDialog.findViewById(R.id.selectedar);
        mCofee = viewDialog.findViewById(R.id.cofee);
        mSelectecofee = viewDialog.findViewById(R.id.selectecofee);
        mHotles = viewDialog.findViewById(R.id.hotles);
        mSelectehotles = viewDialog.findViewById(R.id.selectehotles);
        mResturants = viewDialog.findViewById(R.id.resturants);
        mSelectresturants = viewDialog.findViewById(R.id.selectresturants);

        mFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType=AppConstants.Family_service_type;
                mSelectedar.setVisibility(View.VISIBLE);
                mSelectecofee.setVisibility(View.GONE);
                mSelectehotles.setVisibility(View.GONE);
                mSelectresturants.setVisibility(View.GONE);



            }
        });
        mCofee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType=AppConstants.Cofee_service_type;
                mSelectecofee.setVisibility(View.VISIBLE);
                mSelectedar.setVisibility(View.GONE);
                mSelectehotles.setVisibility(View.GONE);
                mSelectresturants.setVisibility(View.GONE);


            }
        });
        mHotles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType=AppConstants.Hotles_service_type;
                mSelectehotles.setVisibility(View.VISIBLE);
                mSelectedar.setVisibility(View.GONE);
                mSelectresturants.setVisibility(View.GONE);
                mSelectecofee.setVisibility(View.GONE);


            }
        });
        mResturants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType=AppConstants.Resturan_service_type;
                mSelectresturants.setVisibility(View.VISIBLE);
                mSelectedar.setVisibility(View.GONE);
                mSelectehotles.setVisibility(View.GONE);
                mSelectecofee.setVisibility(View.GONE);

            }
        });
        //GONE=8,VISBLW=0,INVISBLE4
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if(dataCallback!=null){
                    dataCallback.dataResult(selectedType,"amerr",true);
                }

                dismiss();

            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });




    }





}
