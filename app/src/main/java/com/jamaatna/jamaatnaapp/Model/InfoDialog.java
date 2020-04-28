package com.jamaatna.jamaatnaapp.Model;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jamaatna.jamaatnaapp.R;


public class InfoDialog extends Dialog {

    TextView messageTxt;
    Activity activity;
    DataFetcherCallBack dataFetcherCallBack;
    private LinearLayout okBtn;
    private TextView mText;
    private TextView mSign;
    private TextView mImage;
    private TextView mBarcode;
    private TextView mFrame;
    private TextView mCancel;


    public InfoDialog(Activity activity,
                      String message, boolean isHtml, final DataFetcherCallBack dataFetcherCallBack) {
        super(activity);

        this.activity = activity;

        this.dataFetcherCallBack = dataFetcherCallBack;

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        setContentView(R.layout.add_layout_dialag);
        try {
            show();
        } catch (Exception e) {
            dismiss();
        }



    }

    private InfoDialog getDialog() {
        return this;
    }

}
