package com.yaleiden.archeryscore;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Yale on 2/1/2017.
 */

public class ProgressDialogCustom  extends ProgressDialog {

    //TextView tv;
    //private Context mContext;

    public ProgressDialogCustom(Context context) {
        super(context);
        // mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog_custom);
        // tv = (TextView) findViewById(R.id.textView);
    }
/*
    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);

        tv.setText(message);
    }
*/
}