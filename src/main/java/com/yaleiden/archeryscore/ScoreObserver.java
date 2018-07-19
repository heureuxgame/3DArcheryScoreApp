package com.yaleiden.archeryscore;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

public class ScoreObserver extends ContentObserver {
@Override
	public void onChange(boolean selfChange, Uri uri) {
		// TODO Auto-generated method stub
		super.onChange(selfChange, uri);
	}
private final Context context;
    public ScoreObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

}