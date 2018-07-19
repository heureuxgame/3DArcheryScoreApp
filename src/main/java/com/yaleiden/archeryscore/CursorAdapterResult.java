package com.yaleiden.archeryscore;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CursorAdapterResult extends SimpleCursorAdapter {

	private final ScoreArrays resultScoreArray = new ScoreArrays();
	private int[] userResultArray;
	RelativeLayout.LayoutParams params;
	Context context;
	Cursor c;
	public static String[] green;       // String array to hold button state
	public static String[] cRow;         // Matching string array to hold db rowId

	public CursorAdapterResult(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to, 0);
		this.c = c;
		this.context = context;

		/*
		green = new String[c.getCount()];  // initialize bull progressbar state array
		cRow = new String[c.getCount()];    // initialize db rowId array

		c.moveToFirst();
		int i = 0;
		while (c.isAfterLast() == false) {
			if (c.getString(9) == null) {  // if state is null, set to " "
				green[i] = " ";

			} else {
				//green[i] = c.getString(9);  // set state to state saved in db
				String userScoring = c.getString(9); // SET returns text of scoring
				int userScoreInt = resultScoreArray.getScoreInt(userScoring);
				String[] userScoreArray = resultScoreArray.getScoringWithoutCustom(userScoreInt);
				green[i] = String.valueOf(userScoreArray.length);
			}
			cRow[i] = c.getString(0);     // set the rowId from the db
			i++;
			c.moveToNext();
		}
		*/
	}



	//@Override
	public void bindView(View view, Context context, Cursor cursor) {
	//public void newView(View view, Context context, Cursor cursor) {

		params = getBarWidth(context);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		Drawable bullDrawable = ContextCompat.getDrawable(context, R.drawable.progress_green);
		/**
		 * creates a histogram of user scores with progressbars.
		 */

		TextView nameView = (TextView) view.findViewById(R.id.textViewResult1);
		TextView dateView = (TextView) view.findViewById(R.id.textViewResult2);
		TextView totalView = (TextView) view.findViewById(R.id.textViewResult3);
		TextView courseView = (TextView) view
				.findViewById(R.id.textViewResult4);
		String TAG = "CursorAdapterResult";
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "before first query");
		}
		String Name = cursor.getString(0);
		int Total = cursor.getInt(3);
		String SortDate = cursor.getString(6);
		String Course = cursor.getString(7);
		String NumTargets = cursor.getString(8).trim(); // SET

		String userScoring = cursor.getString(9); // SET returns text of scoring
		if(BuildConfig.DEBUG) {                                    // format
			Log.d(TAG, "userScoring " + userScoring);
		}
		// SET put all user scores into an array
		int[] allUserScores = { cursor.getInt(10), cursor.getInt(11),
				cursor.getInt(12), cursor.getInt(13), cursor.getInt(14),
				cursor.getInt(15), cursor.getInt(16), cursor.getInt(17),
				cursor.getInt(18), cursor.getInt(19), cursor.getInt(20),
				cursor.getInt(21), cursor.getInt(22), cursor.getInt(23),
				cursor.getInt(24), cursor.getInt(25), cursor.getInt(26),
				cursor.getInt(27), cursor.getInt(28), cursor.getInt(29),
				cursor.getInt(30), cursor.getInt(31), cursor.getInt(32),
				cursor.getInt(33), cursor.getInt(34), cursor.getInt(35),
				cursor.getInt(36), cursor.getInt(37), cursor.getInt(38),
				cursor.getInt(39), cursor.getInt(40), cursor.getInt(41),
				cursor.getInt(42), cursor.getInt(43), cursor.getInt(44),
				cursor.getInt(45), cursor.getInt(46), cursor.getInt(47),
				cursor.getInt(48), cursor.getInt(49), cursor.getInt(50),
				cursor.getInt(51), cursor.getInt(52), cursor.getInt(53),
				cursor.getInt(54), cursor.getInt(55), cursor.getInt(56),
				cursor.getInt(57), cursor.getInt(58), cursor.getInt(59) };
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "after allUserScores");
		}
		int Possible = cursor.getInt(62);

		// SET returns index value of scoring format
		int userScoreInt = resultScoreArray.getScoreInt(userScoring);
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "userScoreInt " + userScoreInt);
		}
		// returns String array of score values
		String[] userScoreArray = resultScoreArray.getScoringWithoutCustom(userScoreInt);
		//if(BuildConfig.DEBUG) {
		//	Log.d(TAG, "after userScoreArray " + userScoreArray.toString());
		//}
		// max score value
		String maxUserScore = resultScoreArray.getBullScore(userScoreInt);
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "maxUserScore " + maxUserScore);
		}
		int numScoreValues = userScoreArray.length;
												// unique score values
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "numScoreValues " + numScoreValues);
		}
		int totalTargets = Integer.parseInt(NumTargets);
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "totalTargets " + totalTargets);
		}

		userResultArray = new int[numScoreValues];
		for (int a = 0; a < numScoreValues; a++) {
			int scoreCount = 0;
			// Log.d(TAG, "scoreCount " + scoreCount);
			int currentScore = Integer.valueOf(userScoreArray[a]);
			for (int b = 0; b < totalTargets; b++) {
				if (currentScore == allUserScores[b])
					scoreCount = scoreCount + 1;
				// Log.d(TAG, "scoreCount " + scoreCount);
				userResultArray[a] = scoreCount;
			}
		}
		if(BuildConfig.DEBUG) {
			Log.d(TAG, "after for loop to fill userResultArray");
		}

		nameView.setText(Name);
		dateView.setText(SortDate);
		totalView.setText(Total + "/" + Possible);
		courseView.setText(Course);

		if (numScoreValues > 9) {
			RelativeLayout row9 = (RelativeLayout) view
					.findViewById(R.id.tableRowP9);
			row9.setVisibility(View.VISIBLE);
			ProgressBar progressBar9 = (ProgressBar) view
					.findViewById(R.id.progressBar9);
			progressBar9.setMax(totalTargets);
			progressBar9.setProgress(userResultArray[9]);
			progressBar9.setLayoutParams(params);
			TextView textViewP9 = (TextView) view.findViewById(R.id.textViewP9);
			textViewP9.setText(userScoreArray[9]);
			TextView textViewN9 = (TextView) view.findViewById(R.id.textViewN9);
			textViewN9.setText(getNonZero(9));
		} else {
			RelativeLayout row9 = (RelativeLayout) view
					.findViewById(R.id.tableRowP9);
			row9.setVisibility(View.GONE);
		}
		if (numScoreValues > 8) {
			RelativeLayout row8 = (RelativeLayout) view
					.findViewById(R.id.tableRowP8);
			row8.setVisibility(View.VISIBLE);
			ProgressBar progressBar8 = (ProgressBar) view
					.findViewById(R.id.progressBar8);
		//	if(numScoreValues == 9){
			//	progressBar8.setProgressDrawable(bullDrawable);
			//}
			progressBar8.setMax(totalTargets);
			progressBar8.setProgress(userResultArray[8]);
			progressBar8.setLayoutParams(params);

			TextView textViewP8 = (TextView) view.findViewById(R.id.textViewP8);
			textViewP8.setText(userScoreArray[8]);
			TextView textViewN8 = (TextView) view.findViewById(R.id.textViewN8);
			textViewN8.setText(getNonZero(8));
		} else {
			RelativeLayout row8 = (RelativeLayout) view
					.findViewById(R.id.tableRowP8);
			row8.setVisibility(View.GONE);
		}
		if (numScoreValues > 7) {
			RelativeLayout row7 = (RelativeLayout) view
					.findViewById(R.id.tableRowP7);
			row7.setVisibility(View.VISIBLE);
			ProgressBar progressBar7 = (ProgressBar) view
					.findViewById(R.id.progressBar7);
			//if(numScoreValues == 8){
			//	progressBar7.setProgressDrawable(bullDrawable);
			//}
			progressBar7.setMax(totalTargets);
			progressBar7.setProgress(userResultArray[7]);
			progressBar7.setLayoutParams(params);

			TextView textViewP7 = (TextView) view.findViewById(R.id.textViewP7);
			textViewP7.setText(userScoreArray[7]);
			TextView textViewN7 = (TextView) view.findViewById(R.id.textViewN7);
			textViewN7.setText(getNonZero(7));
		} else {
			RelativeLayout row7 = (RelativeLayout) view
					.findViewById(R.id.tableRowP7);
			row7.setVisibility(View.GONE);
		}
		if (numScoreValues > 6) {
			RelativeLayout row6 = (RelativeLayout) view
					.findViewById(R.id.tableRowP6);
			row6.setVisibility(View.VISIBLE);
			ProgressBar progressBar6 = (ProgressBar) view
					.findViewById(R.id.progressBar6);
			//if(numScoreValues == 7){
			//	progressBar6.setProgressDrawable(bullDrawable);
			//}
			progressBar6.setMax(totalTargets);
			progressBar6.setProgress(userResultArray[6]);
			progressBar6.setLayoutParams(params);

			TextView textViewP6 = (TextView) view.findViewById(R.id.textViewP6);
			textViewP6.setText(userScoreArray[6]);
			TextView textViewN6 = (TextView) view.findViewById(R.id.textViewN6);
			textViewN6.setText(getNonZero(6));
		} else {
			RelativeLayout row6 = (RelativeLayout) view
					.findViewById(R.id.tableRowP6);
			row6.setVisibility(View.GONE);
		}
		if (numScoreValues > 5) {
			RelativeLayout row5 = (RelativeLayout) view
					.findViewById(R.id.tableRowP5);
			row5.setVisibility(View.VISIBLE);
			ProgressBar progressBar5 = (ProgressBar) view
					.findViewById(R.id.progressBar5);
			//if(numScoreValues == 6){
			//	progressBar5.setProgressDrawable(bullDrawable);
		//	}
			progressBar5.setMax(totalTargets);
			progressBar5.setProgress(userResultArray[5]);
			progressBar5.setLayoutParams(params);

			TextView textViewP5 = (TextView) view.findViewById(R.id.textViewP5);
			textViewP5.setText(userScoreArray[5]);
			TextView textViewN5 = (TextView) view.findViewById(R.id.textViewN5);
			textViewN5.setText(getNonZero(5));
		} else {
			RelativeLayout row5 = (RelativeLayout) view
					.findViewById(R.id.tableRowP5);
			row5.setVisibility(View.GONE);
		}
		if (numScoreValues > 4) {
			RelativeLayout row4 = (RelativeLayout) view
					.findViewById(R.id.tableRowP4);
			row4.setVisibility(View.VISIBLE);
			ProgressBar progressBar4 = (ProgressBar) view
					.findViewById(R.id.progressBar4);
			//if(numScoreValues == 5){
			//	progressBar4.setProgressDrawable(bullDrawable);
			//}
			progressBar4.setMax(totalTargets);
			progressBar4.setProgress(userResultArray[4]);
			progressBar4.setLayoutParams(params);

			TextView textViewP4 = (TextView) view.findViewById(R.id.textViewP4);
			textViewP4.setText(userScoreArray[4]);
			TextView textViewN4 = (TextView) view.findViewById(R.id.textViewN4);
			textViewN4.setText(getNonZero(4));
		} else {
			RelativeLayout row4 = (RelativeLayout) view
					.findViewById(R.id.tableRowP4);
			row4.setVisibility(View.GONE);
		}
		if (numScoreValues > 3) {
			RelativeLayout row3 = (RelativeLayout) view
					.findViewById(R.id.tableRowP3);
			row3.setVisibility(View.VISIBLE);
			ProgressBar progressBar3 = (ProgressBar) view
					.findViewById(R.id.progressBar3);
		//	if(numScoreValues == 4){
		//		progressBar3.setProgressDrawable(bullDrawable);
		//	}
			progressBar3.setMax(totalTargets);
			progressBar3.setProgress(userResultArray[3]);
			progressBar3.setLayoutParams(params);

			TextView textViewP3 = (TextView) view.findViewById(R.id.textViewP3);
			textViewP3.setText(userScoreArray[3]);
			TextView textViewN3 = (TextView) view.findViewById(R.id.textViewN3);
			textViewN3.setText(getNonZero(3));
		} else {
			RelativeLayout row3 = (RelativeLayout) view
					.findViewById(R.id.tableRowP3);
			row3.setVisibility(View.GONE);
		}

		if (numScoreValues > 2) {
			ProgressBar progressBar2 = (ProgressBar) view
					.findViewById(R.id.progressBar2);
			progressBar2.setMax(totalTargets);
		//	if(numScoreValues == 3){
		//		progressBar2.setProgressDrawable(bullDrawable);
		//	}
			progressBar2.setProgress(userResultArray[2]);
			progressBar2.setLayoutParams(params);

			TextView textViewP2 = (TextView) view.findViewById(R.id.textViewP2);
			textViewP2.setText(userScoreArray[2]);
			TextView textViewN2 = (TextView) view.findViewById(R.id.textViewN2);
			textViewN2.setText(getNonZero(2));
		}

		if (numScoreValues > 1) {
			ProgressBar progressBar1 = (ProgressBar) view
					.findViewById(R.id.progressBar1);
			progressBar1.setMax(totalTargets);
			progressBar1.setProgress(userResultArray[1]);
			progressBar1.setLayoutParams(params);
			TextView textViewP1 = (TextView) view.findViewById(R.id.textViewP1);
			textViewP1.setText(userScoreArray[1]);
			TextView textViewN1 = (TextView) view.findViewById(R.id.textViewN1);
			textViewN1.setText(getNonZero(1));
		}

		if (numScoreValues > 0) {
			ProgressBar progressBar0 = (ProgressBar) view
					.findViewById(R.id.progressBar0);
			progressBar0.setMax(totalTargets);
			progressBar0.setProgress(userResultArray[0]);
			progressBar0.setLayoutParams(params);
			TextView textViewP0 = (TextView) view.findViewById(R.id.textViewP0);
			textViewP0.setText(userScoreArray[0]);
			TextView textViewN0 = (TextView) view.findViewById(R.id.textViewN0);
			textViewN0.setText(getNonZero(0));
		}
		// this is what I changed
		TextView textViewB1 = (TextView) view.findViewById(R.id.textViewB1);
		TextView textViewB2 = (TextView) view.findViewById(R.id.textViewB2);
		TextView textViewB3 = (TextView) view.findViewById(R.id.textViewB3);
		TextView textViewC1 = (TextView) view.findViewById(R.id.textViewC1);
		
		textViewB1.setText(cursor.getString(8));
		textViewB2.setText(cursor.getString(61) + "%");
		textViewB3.setText(cursor.getString(60));
		textViewC1.setText(cursor.getString(4));
	}

	private String getNonZero(int n){
		String string = "";
		if (userResultArray[n] == 0){
			//return string;
		}else {
			string = String.valueOf(userResultArray[n]);
		}
		return string;
	}



	@SuppressWarnings("deprecation")
	private static RelativeLayout.LayoutParams getBarWidth(Context context) {

		int measuredWidth;
		int returnWidth = 140;
		//RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getBarWidth(context), 28);
		context.getResources().getBoolean(R.bool.dual_pane);

		WindowManager w = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

			Point size = new Point();
			w.getDefaultDisplay().getSize(size);
			measuredWidth = size.x;

		} else {
			Display d = w.getDefaultDisplay();
			measuredWidth = d.getWidth();
		}

		returnWidth = (int) (measuredWidth/2.5);
		return new RelativeLayout.LayoutParams(returnWidth, getDensityName(context));
	}

	private static int getDensityName(Context context) {
		float density = context.getResources().getDisplayMetrics().density;
		Double DENSITY_FACTOR = 1d;
		if (density >= 4.0) {
			DENSITY_FACTOR = 4d;
			//return "xxxhdpi";
		}
		else if (density >= 3.0) {
			DENSITY_FACTOR = 3d;
			//return "xxhdpi";
		}
		else if (density >= 2.0) {
			DENSITY_FACTOR = 2d;
			//return "xhdpi";
		}
		else if (density >= 1.5) {
			DENSITY_FACTOR = 1.5;
			//return "hdpi";
		}
		else if (density >= 1.0) {
			DENSITY_FACTOR = 1d;
			//return "mdpi";
		}
		//return DENSITY_FACTOR;
		return (int)(DENSITY_FACTOR + 0.5d)*10;
	}
}
