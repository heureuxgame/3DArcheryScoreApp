package com.yaleiden.archeryscore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.yaleiden.cloud.BaseActivity;

/**
 * Created by Yale on 2/1/2017.
 */

public class MainCloud  extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maincloud_activity);

        Toast.makeText(this, "MAIN", Toast.LENGTH_LONG).show();
    }
}
