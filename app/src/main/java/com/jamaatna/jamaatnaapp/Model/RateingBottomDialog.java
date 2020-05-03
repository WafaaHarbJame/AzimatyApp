package com.jamaatna.jamaatnaapp.Model;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.jamaatna.jamaatnaapp.Activity.LoginActivity;
import com.jamaatna.jamaatnaapp.R;
import com.jamaatna.jamaatnaapp.Utlities.UtilityApp;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
    AwesomeProgressDialog awesomeProgressDialog;
    String ratingdate;
    String user_name,user_imag;
    int Rating_value;


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

                if (UtilityApp.isLogin()) {
                    token = UtilityApp.getUserToken();
                    ratingdate = formatDate(Calendar.getInstance().getTime());
                    user_name=UtilityApp.getUserData().name;
                    user_imag=UtilityApp.getUserData().photo;

//                    mRtRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                        @Override
//                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                            if(b){
//                                Rating_value=1;
//                            }
//                            else {
//                                Rating_value=0;
//
//
//                            }
//                        }
//                    });
                    if(mRatingText.getText().toString().isEmpty())
                    {
                        mRatingText.setError(getString(R.string.rateingtv));
                        mRatingText.requestFocus();

                    }

                    else if(mRtRating.getRating()==0)
                    {
                        Toast.makeText(getActivity(), ""+getString(R.string.ratingRequired), Toast.LENGTH_SHORT).show();

                    }
                    else {

                        if (dataCallback != null) {
                            PsersonRating psersonRating = new PsersonRating(user_name, 1, mRatingText.getText().toString(),
                                    ratingdate, user_imag, (int) mRtRating.getRating());
                            dataCallback.dataResult(psersonRating, "amerr", true);
                        }

                        dismiss();
                    }




                }

                else {
                    Toast.makeText(getContext(), ""+getString(R.string.you_must_login), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }



        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });


    }




    public void showProgreesDilaog(Context context, String tittle, String message){

        awesomeProgressDialog = new AwesomeProgressDialog(context);
        awesomeProgressDialog.setTitle(tittle).setMessage(message).
                setColoredCircle(R.color.darkpink).setDialogIconAndColor(R.drawable.ic_dialog_info,
                R.color.white).setCancelable(false).show();
    }

    public void hideProgreesDilaog(Context context,String tittle,String message){

        if(awesomeProgressDialog!=null){
            awesomeProgressDialog.hide();

        }
    }

    public String formatDate(Date date) {
        // Locale locale = new Locale( "ar" , "SA" ) ;  // Arabic language. Saudi Arabia cultural norms.
        SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        customFormat.setLenient(false);
        customFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return customFormat.format(date);
    }


}
