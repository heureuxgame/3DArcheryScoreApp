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

public class CursorAdapterResult2 extends SimpleCursorAdapter {

    private static final String TAG = "CursorAdapterResult2";
    private final ScoreArrays resultScoreArray = new ScoreArrays();
    private int[] userResultArray;
    RelativeLayout.LayoutParams params;
    Context context;
    Cursor c;
    public static String[] green;       // String array to hold button state
    public static String[] cRow;         // Matching string array to hold db rowId

    public CursorAdapterResult2(Context context, int layout, Cursor c,
                                String[] from, int[] to) {
        super(context, layout, c, from, to, 0);
        this.c = c;
        this.context = context;

    }


    //@Override
    public void bindView(View view, Context context, Cursor cursor) {
        //public void newView(View view, Context context, Cursor cursor) {

        if (BuildConfig.DEBUG) {
            Log.d(TAG, " bindView");
        }

        String TAG = "CursorAdapterResult";
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "before first query");
        }
        //String Name = cursor.getString(0);

        //String SortDate = cursor.getString(6);
        //String Course = cursor.getString(7);
        String NumTargets = cursor.getString(8).trim(); // SET

        String userScoring = cursor.getString(9); // SET returns text of scoring
        if (BuildConfig.DEBUG) {                                    // format
            Log.d(TAG, "userScoring " + userScoring);
        }
        // SET put all user scores into an array
        int[] allUserScores = {cursor.getInt(10), cursor.getInt(11),
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
                cursor.getInt(57), cursor.getInt(58), cursor.getInt(59)};
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "after allUserScores");
        }


        // SET returns index value of scoring format
        int userScoreInt = resultScoreArray.getScoreInt(userScoring);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "userScoreInt " + userScoreInt);
        }
        // returns String array of score values
        String[] userScoreArray = resultScoreArray.getScoringWithoutCustom(userScoreInt);
        //if (BuildConfig.DEBUG) {
        //    Log.d(TAG, "after userScoreArray " + userScoreArray.toString());
        //}
        // max score value
        String maxUserScore = resultScoreArray.getBullScore(userScoreInt);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "maxUserScore " + maxUserScore);
        }
        int numScoreValues = userScoreArray.length;
        // unique score values
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "numScoreValues " + numScoreValues);
        }
        int totalTargets = Integer.parseInt(NumTargets);
        if (BuildConfig.DEBUG) {
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
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "after for loop to fill userResultArray");
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null ) {
        //if (holder == null || holder != null && holder.scores != numScoreValues) {
            holder = new ViewHolder();
            holder.progressBar0 = (ProgressBar) view.findViewById(R.id.progressBar0);
            holder.progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
            holder.progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);
            holder.progressBar3 = (ProgressBar) view.findViewById(R.id.progressBar3);
            holder.progressBar4 = (ProgressBar) view.findViewById(R.id.progressBar4);
            holder.progressBar5 = (ProgressBar) view.findViewById(R.id.progressBar5);
            holder.progressBar6 = (ProgressBar) view.findViewById(R.id.progressBar6);
            holder.progressBar7 = (ProgressBar) view.findViewById(R.id.progressBar7);
            holder.progressBar8 = (ProgressBar) view.findViewById(R.id.progressBar8);
            holder.progressBar9 = (ProgressBar) view.findViewById(R.id.progressBar9);
            holder.tableRowP0 = (RelativeLayout) view.findViewById(R.id.tableRowP0);
            holder.tableRowP1 = (RelativeLayout) view.findViewById(R.id.tableRowP1);
            holder.tableRowP2 = (RelativeLayout) view.findViewById(R.id.tableRowP2);
            holder.tableRowP3 = (RelativeLayout) view.findViewById(R.id.tableRowP3);
            holder.tableRowP4 = (RelativeLayout) view.findViewById(R.id.tableRowP4);
            holder.tableRowP5 = (RelativeLayout) view.findViewById(R.id.tableRowP5);
            holder.tableRowP6 = (RelativeLayout) view.findViewById(R.id.tableRowP6);
            holder.tableRowP7 = (RelativeLayout) view.findViewById(R.id.tableRowP7);
            holder.tableRowP8 = (RelativeLayout) view.findViewById(R.id.tableRowP8);
            holder.tableRowP9 = (RelativeLayout) view.findViewById(R.id.tableRowP9);
            holder.textViewP0 = (TextView) view.findViewById(R.id.textViewP0);
            holder.textViewP1 = (TextView) view.findViewById(R.id.textViewP1);
            holder.textViewP2 = (TextView) view.findViewById(R.id.textViewP2);
            holder.textViewP3 = (TextView) view.findViewById(R.id.textViewP3);
            holder.textViewP4 = (TextView) view.findViewById(R.id.textViewP4);
            holder.textViewP5 = (TextView) view.findViewById(R.id.textViewP5);
            holder.textViewP6 = (TextView) view.findViewById(R.id.textViewP6);
            holder.textViewP7 = (TextView) view.findViewById(R.id.textViewP7);
            holder.textViewP8 = (TextView) view.findViewById(R.id.textViewP8);
            holder.textViewP9 = (TextView) view.findViewById(R.id.textViewP9);
            holder.textViewN0 = (TextView) view.findViewById(R.id.textViewN0);
            holder.textViewN1 = (TextView) view.findViewById(R.id.textViewN1);
            holder.textViewN2 = (TextView) view.findViewById(R.id.textViewN2);
            holder.textViewN3 = (TextView) view.findViewById(R.id.textViewN3);
            holder.textViewN4 = (TextView) view.findViewById(R.id.textViewN4);
            holder.textViewN5 = (TextView) view.findViewById(R.id.textViewN5);
            holder.textViewN6 = (TextView) view.findViewById(R.id.textViewN6);
            holder.textViewN7 = (TextView) view.findViewById(R.id.textViewN7);
            holder.textViewN8 = (TextView) view.findViewById(R.id.textViewN8);
            holder.textViewN9 = (TextView) view.findViewById(R.id.textViewN9);
            holder.textViewC1 = (TextView) view.findViewById(R.id.textViewC1);
            holder.textViewB3 = (TextView) view.findViewById(R.id.textViewB3);
            holder.textViewB2 = (TextView) view.findViewById(R.id.textViewB2);
            holder.textViewB1 = (TextView) view.findViewById(R.id.textViewB1);
            holder.textViewResult1 = (TextView) view.findViewById(R.id.textViewResult1);
            holder.textViewResult2 = (TextView) view.findViewById(R.id.textViewResult2);
            holder.textViewResult3 = (TextView) view.findViewById(R.id.textViewResult3);
            holder.textViewResult4 = (TextView) view.findViewById(R.id.textViewResult4);
            view.setTag(holder);
        }

        params = getBarWidth(context);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        Drawable bullDrawable = ContextCompat.getDrawable(context, R.drawable.progress_green);
        /**
         * creates a histogram of user scores with progressbars.
         */

        int Possible = cursor.getInt(62);
        int Total = cursor.getInt(3);
        holder.scores = numScoreValues;
        holder.textViewResult1.setText(cursor.getString(0)); //Name
        holder.textViewResult2.setText(cursor.getString(6)); //SortDate
        holder.textViewResult3.setText(Total + "/" + Possible);//Total of possible
        holder.textViewResult4.setText(cursor.getString(7));//Course



        //nameView.setText(Name);
        //dateView.setText(SortDate);
        //totalView.setText(Total + "/" + Possible);
        //courseView.setText(Course);

        if (numScoreValues > 9) {
            holder.tableRowP9.setVisibility(View.VISIBLE);
            holder.progressBar9.setMax(totalTargets);
            holder.progressBar9.setProgress(userResultArray[9]);
            holder.progressBar9.setLayoutParams(params);
            holder.textViewP9.setText(userScoreArray[9]);
            holder.textViewN9.setText(getNonZero(9));
        } else {
            holder.tableRowP9.setVisibility(View.GONE);
        }
        if (numScoreValues > 8) {
            holder.tableRowP8.setVisibility(View.VISIBLE);
            if (numScoreValues == 9) {
                holder.progressBar8.setProgressDrawable(bullDrawable);
            }
            holder.progressBar8.setMax(totalTargets);
            holder.progressBar8.setProgress(userResultArray[8]);
            holder.progressBar8.setLayoutParams(params);
            holder.textViewP8.setText(userScoreArray[8]);
            holder.textViewN8.setText(getNonZero(8));
        } else {
            holder.tableRowP8.setVisibility(View.GONE);
        }
        if (numScoreValues > 7) {
            holder.tableRowP7.setVisibility(View.VISIBLE);
            if (numScoreValues == 8) {
                holder.progressBar7.setProgressDrawable(bullDrawable);
            }
            holder.progressBar7.setMax(totalTargets);
            holder.progressBar7.setProgress(userResultArray[7]);
            holder.progressBar7.setLayoutParams(params);
            holder.textViewP7.setText(userScoreArray[7]);
            holder.textViewN7.setText(getNonZero(7));
        } else {
            holder.tableRowP7.setVisibility(View.GONE);
        }
        if (numScoreValues > 6) {
            holder.tableRowP6.setVisibility(View.VISIBLE);
            if (numScoreValues == 7) {
                holder.progressBar6.setProgressDrawable(bullDrawable);
            }
            holder.progressBar6.setMax(totalTargets);
            holder.progressBar6.setProgress(userResultArray[6]);
            holder.progressBar6.setLayoutParams(params);
            holder.textViewP6.setText(userScoreArray[6]);
            holder.textViewN6.setText(getNonZero(6));
        } else {
            holder.tableRowP6.setVisibility(View.GONE);
        }
        if (numScoreValues > 5) {
            holder.tableRowP5.setVisibility(View.VISIBLE);
            if (numScoreValues == 6) {
                holder.progressBar5.setProgressDrawable(bullDrawable);
            }
            holder.progressBar5.setMax(totalTargets);
            holder.progressBar5.setProgress(userResultArray[5]);
            holder.progressBar5.setLayoutParams(params);
            holder.textViewP5.setText(userScoreArray[5]);
            holder.textViewN5.setText(getNonZero(5));
        } else {
            holder.tableRowP5.setVisibility(View.GONE);
        }
        if (numScoreValues > 4) {
            holder.tableRowP4.setVisibility(View.VISIBLE);
            if (numScoreValues == 5) {
                holder.progressBar4.setProgressDrawable(bullDrawable);
            }
            holder.progressBar4.setMax(totalTargets);
            holder.progressBar4.setProgress(userResultArray[4]);
            holder.progressBar4.setLayoutParams(params);
            holder.textViewP4.setText(userScoreArray[4]);
            holder.textViewN4.setText(getNonZero(4));
        } else {
            holder.tableRowP4.setVisibility(View.GONE);
        }
        if (numScoreValues > 3) {
            holder.tableRowP3.setVisibility(View.VISIBLE);
            if (numScoreValues == 4) {
                holder.progressBar3.setProgressDrawable(bullDrawable);
            }
            holder.progressBar3.setMax(totalTargets);
            holder.progressBar3.setProgress(userResultArray[3]);
            holder.progressBar3.setLayoutParams(params);
            holder.textViewP3.setText(userScoreArray[3]);
            holder.textViewN3.setText(getNonZero(3));
        } else {
            holder.tableRowP3.setVisibility(View.GONE);
        }
        if (numScoreValues > 2) {
            holder.tableRowP2.setVisibility(View.VISIBLE);
            if (numScoreValues == 3) {
                holder.progressBar2.setProgressDrawable(bullDrawable);
            }
            holder.progressBar2.setMax(totalTargets);
            holder.progressBar2.setProgress(userResultArray[2]);
            holder.progressBar2.setLayoutParams(params);
            holder.textViewP2.setText(userScoreArray[2]);
            holder.textViewN2.setText(getNonZero(2));
        }
        if (numScoreValues > 1) {
            holder.progressBar1.setMax(totalTargets);
            holder.progressBar1.setProgress(userResultArray[1]);
            holder.progressBar1.setLayoutParams(params);
            holder.textViewP1.setText(userScoreArray[1]);
            holder.textViewN1.setText(getNonZero(1));
        }
        if (numScoreValues > 0) {
            holder.progressBar0.setMax(totalTargets);
            holder.progressBar0.setProgress(userResultArray[0]);
            holder.progressBar0.setLayoutParams(params);
            holder.textViewP0.setText(userScoreArray[0]);
            holder.textViewN0.setText(getNonZero(0));
        }
        holder.textViewB1.setText(cursor.getString(8));
        holder.textViewB2.setText(cursor.getString(61) + "%");
        holder.textViewB3.setText(cursor.getString(60));
        holder.textViewC1.setText(cursor.getString(4));
    }

    private String getNonZero(int n) {
        String string = "";
        if (userResultArray[n] == 0) {
            //return string;
        } else {
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

        returnWidth = (int) (measuredWidth / 2.5);
        return new RelativeLayout.LayoutParams(returnWidth, getDensityName(context));
    }

    private static int getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        Double DENSITY_FACTOR = 1d;
        if (density >= 4.0) {
            DENSITY_FACTOR = 4d;
            //return "xxxhdpi";
        } else if (density >= 3.0) {
            DENSITY_FACTOR = 3d;
            //return "xxhdpi";
        } else if (density >= 2.0) {
            DENSITY_FACTOR = 2d;
            //return "xhdpi";
        } else if (density >= 1.5) {
            DENSITY_FACTOR = 1.5;
            //return "hdpi";
        } else if (density >= 1.0) {
            DENSITY_FACTOR = 1d;
            //return "mdpi";
        }
        //return DENSITY_FACTOR;
        return (int) (DENSITY_FACTOR + 0.5d) * 10;
    }

    static class ViewHolder {
        ProgressBar progressBar0;
        ProgressBar progressBar1;
        ProgressBar progressBar2;
        ProgressBar progressBar3;
        ProgressBar progressBar4;
        ProgressBar progressBar5;
        ProgressBar progressBar6;
        ProgressBar progressBar7;
        ProgressBar progressBar8;
        ProgressBar progressBar9;
        RelativeLayout tableRowP0;
        RelativeLayout tableRowP1;
        RelativeLayout tableRowP2;
        RelativeLayout tableRowP3;
        RelativeLayout tableRowP4;
        RelativeLayout tableRowP5;
        RelativeLayout tableRowP6;
        RelativeLayout tableRowP7;
        RelativeLayout tableRowP8;
        RelativeLayout tableRowP9;
        TextView textViewP0;
        TextView textViewP1;
        TextView textViewP2;
        TextView textViewP3;
        TextView textViewP4;
        TextView textViewP5;
        TextView textViewP6;
        TextView textViewP7;
        TextView textViewP8;
        TextView textViewP9;
        TextView textViewN0;
        TextView textViewN1;
        TextView textViewN2;
        TextView textViewN3;
        TextView textViewN4;
        TextView textViewN5;
        TextView textViewN6;
        TextView textViewN7;
        TextView textViewN8;
        TextView textViewN9;
        TextView textViewC1;
        TextView textViewB3;
        TextView textViewB2;
        TextView textViewB1;
        TextView textViewResult1;
        TextView textViewResult2;
        TextView textViewResult3;
        TextView textViewResult4;
        int scores;

    }
}
