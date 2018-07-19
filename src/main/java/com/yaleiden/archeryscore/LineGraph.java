package com.yaleiden.archeryscore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class LineGraph {
    private static final String TAG = "LineGraph";

    private double yVals[];
    private String xdate[];

    public Intent getIntent(Context context) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " LineGraph Intent");
        }
        double[] y = GetArchiveData(context);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " afterGetArchiveData");
        }
        //int x[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
        //int y[] = {75,79,81,95,68,89,75,74,71,69,91,78,75 };
        XYMultipleSeriesDataset dataset;
        XYMultipleSeriesRenderer mRenderer;
        if (y[0] == 999.99) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " in y==0 if stmt");
            }
            Toast.makeText(context, "No archived scores are available", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
            //context.startActivity(intent);
            return intent;
        } else {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " before TimeSeries");
            }
            TimeSeries series = new TimeSeries("Scoring Percentage");
            int[] x = new int[y.length];
            try {
                for (int i = 0; i < y.length; i++) {
                    x[i] = i;
                    series.add(x[i], y[i]);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " After TimeSeries");
            }
            dataset = new XYMultipleSeriesDataset();
            dataset.addSeries(series);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " After addSeries " + series.toString());
            }
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setColor(Color.YELLOW);
            renderer.setLineWidth(2);

            mRenderer = new XYMultipleSeriesRenderer();
            mRenderer.addSeriesRenderer(renderer);
            mRenderer.setBackgroundColor(Color.BLACK);
            mRenderer.setApplyBackgroundColor(true);
            mRenderer.setYAxisMin(0);
            mRenderer.setYAxisMax(100);
            mRenderer.setShowGridX(true);
            mRenderer.setScale(10);
            //mRenderer.setLabelsTextSize(24);
            mRenderer.setLegendTextSize(30);
            mRenderer.setLabelsColor(Color.YELLOW);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " After Renderers");
            }

        }
        Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Tournament Lite - Archived com.yaleiden.archeryscorecloud.Scores");

        return intent;


    }

    private double[] GetArchiveData(Context context) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " GetArchiveData");
        }
        // Queries the user dictionary and returns results
        final String[] mProjection = {"Percentage"};
        final String mSelectionClause = "_id > -1";
        final String[] mSelectionArgs = null;
        final String mSortOrder = "_id ASC";
        Cursor mCursor = context.getApplicationContext().getContentResolver().query(
                ScoreProvider.ARCHIVE_URI,   // The content URI of the words table
                mProjection,                        // The columns to return for each row
                mSelectionClause,                    // Selection criteria
                mSelectionArgs,                     // Selection criteria
                mSortOrder);                        // The sort order for the returned rows
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after cursor");
        }
        int yi = 0;
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after yi " + yi);
        }
        assert mCursor != null;
        int count = mCursor.getCount();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after count " + count);
        }
        if (count == 0) {
            yVals = new double[1];
            yVals[0] = 999.99;  //indicates a failure to find data in cursor
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " after yVals " + yVals[0]);
            }
            return yVals;
        } else {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Count " + count);
            }
            if (mCursor.moveToFirst()) {
                yVals = new double[count];
                do {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " begin do");
                    }
                    String word = mCursor.getString(0);
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " after word " + word);
                    }
                    double percent = Double.parseDouble(word);
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " after double percent is " + percent);
                        Log.d(TAG, " yi = " + yi);
                    }
                    try {
                        yVals[yi] = percent;

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " yVals = " + yVals[yi]);
                    }
                    yi++;
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " finish do");
                    }
                } while (mCursor.moveToNext());
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, " after while");
                }
            }

        }
        mCursor.close();
        return yVals;
    }

}
