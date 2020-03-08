package com.azimaty.azimatyapp.Model;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.azimaty.azimatyapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Date;

public class RateingBottomDialog extends BottomSheetDialogFragment {

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
    private TextView mTvRatingItem;
    private RatingBar mRtRating;
    private EditText mRatingText;
    private Button mButoonrate;

    public RateingBottomDialog(DataCallback callback) {
        this.dataCallback = callback;

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        viewDialog = View.inflate(getContext(), R.layout.making_rating_dialag, null);
        dialog.setContentView(viewDialog);
        mButtonCancel = viewDialog.findViewById(R.id.buttonCancel);
        mTvRatingItem = viewDialog.findViewById(R.id.tv_rating_item);
        mRtRating = viewDialog.findViewById(R.id.Rt_rating);
        mRatingText = viewDialog.findViewById(R.id.ratingText);
        mButoonrate = viewDialog.findViewById(R.id.butoonrate);
        Date d = new Date();
        final CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
        mButoonrate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (dataCallback != null) {
                    PsersonRating psersonRating=new PsersonRating("",2,mRatingText.getText().toString(), (String) s,"ll",mRtRating.getNumStars());
                    dataCallback.dataResult(psersonRating, "amerr", true);
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
