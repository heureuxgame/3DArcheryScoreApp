package com.yaleiden.archeryscore;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomDialogClass extends Dialog implements
		android.view.View.OnClickListener {

	public Dialog d;

	public CustomDialogClass(Activity a) {
		super(a);
		// TODO Auto-generated constructor stub
		Activity c = a;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);
		//yes = (Button) findViewById(R.id.btn_yes);
		Button no = (Button) findViewById(R.id.btn_no);
		//yes.setOnClickListener(this);
		no.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//case R.id.btn_yes:
		//	c.finish();
		//	break;
		case R.id.btn_no:
			dismiss();
			break;
		default:
			break;
		}
		dismiss();
	}
}