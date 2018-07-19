package com.yaleiden.archeryscore;

//import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class ViewArchiveChart extends android.support.v4.app.Fragment {
    private static final String TAG = "ViewArchiveChart";
    private double yVals[];
    private String xLabels[];
    private static double DENSITY_FACTOR;
    //private GraphicalView mChartView;
    double[] y;
    private LinearLayout linearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onCreateView");
        }
        View view;
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " container == null");
            }
            return null;
        } else {
            // Inflate the layout for this fragment
            view = inflater
                    .inflate(R.layout.chart_layout, container, false);
            //layout = (ViewGroup) view.findViewById(R.id.chart);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout = (LinearLayout) getActivity().findViewById(R.id.chart);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        y = GetArchiveData();

        // ** 99999 is the tag in case there is no data in ARCHIVIE_TABLE
        if (y[0] == 99999) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onActivityCreated no Data");
            }
            Toast.makeText(getActivity(), "No archived scores are available",
                    Toast.LENGTH_LONG).show();

        }else {
            linearLayout.addView(creatChart());

        }


    }

    private GraphicalView creatChart() {
        GraphicalView graphicalView = null;
        getDensityName(getActivity());
        if (BuildConfig.DEBUG) {
            Log.d(TAG, getDensityName(getActivity()));
        }
        //double[] y = GetArchiveData(); disabled since I am grabbing it earlier
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " afterGetArchiveData");
        }
        // ** 99999 is the tag in case there is no data in ARCHIVIE_TABLE
        if (y[0] == 99999) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " in y==0 if stmt");
            }
            Toast.makeText(getActivity(), "No archived scores are available",
                    Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(this, MainActivity.class);
            //startActivity(intent);
        } else {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " before TimeSeries");
            }
            TimeSeries series = new TimeSeries(getString(R.string.scoring_percentage));
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

            XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
            dataset.addSeries(series);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " After addSeries " + series.toString());
            }
            // int[] colors = new int[] { Color.YELLOW };
            // PointStyle[] styles = new PointStyle[] { PointStyle.DIAMOND };
            double max = y.length + 0.2 - 1;
            XYSeriesRenderer renderer = new XYSeriesRenderer();
            renderer.setPointStyle(PointStyle.DIAMOND);
            renderer.setColor(Color.YELLOW);
            renderer.setFillPoints(true); // trying to add points to line
            renderer.setLineWidth((float) DENSITY_FACTOR * 3f); //Data line width

            String chartTitle = getString(
                    R.string.scoring_percentage);

            XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
            mRenderer.setXAxisMin(-0.2);
            mRenderer.setXAxisMax(max);
            mRenderer.addSeriesRenderer(renderer);
            //mRenderer.setBackgroundColor(Color.BLACK);
            //mRenderer.setApplyBackgroundColor(true);
            mRenderer.setChartTitle(chartTitle);

            mRenderer.setYAxisMin(0);
            mRenderer.setYAxisMax(100);
            mRenderer.setYLabels(11);
            mRenderer.setShowGridY(true);
            mRenderer.setShowGridX(true);
            mRenderer.setPointSize((float) (6 * DENSITY_FACTOR)); //Data point marker size
            mRenderer.setScale(10);
            mRenderer.setXLabels(0);
            mRenderer.setLabelsTextSize((float) (DENSITY_FACTOR) * 20f); //XY labels size
            mRenderer.setChartTitleTextSize(mRenderer.getLabelsTextSize());
            mRenderer.setXLabelsAngle(80);
            mRenderer.setXLabelsColor(Color.WHITE);
            mRenderer.setShowLegend(false);
            mRenderer.setLabelsColor(Color.WHITE);
            mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
            int botMargin = (int) (mRenderer.getLabelsTextSize() * 2);
            int leftMargin = (int) (mRenderer.getLabelsTextSize());
            int rightMargin = (int) (mRenderer.getLabelsTextSize());
            int topMargin = (int) (mRenderer.getLabelsTextSize() * 2);
            int margins[] = {topMargin, leftMargin, botMargin, rightMargin}; // T,L,B,R
            mRenderer.setMargins(margins);
            for (int i = 0; i < y.length; i++) {
                String s = xLabels[i].substring(0, 5);
                mRenderer.addXTextLabel(i, s);
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, " After addXTextLabel s= " + s);
                }
            }
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " After Renderers");
            }

            graphicalView = ChartFactory.getTimeChartView(getActivity(), dataset,
                    mRenderer, "MM-dd");

        }
        return graphicalView;
    }


    private double[] GetArchiveData() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " GetArchiveData");
        }
        // Queries the ARCHIVE TABLE
        String[] mProjection = {"Percentage", "SortDate"};
        String mSelectionClause = "_id > -1";
        String[] mSelectionArgs = null;
        String mSortOrder = "_id ASC";
        Cursor mCursor = getActivity().getContentResolver().query(
                ScoreProvider.ARCHIVE_URI, // The content URI of the words table
                mProjection, // The columns to return for each row
                mSelectionClause, // Selection criteria
                mSelectionArgs, // Selection criteria
                mSortOrder); // The sort order for the returned rows
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
            yVals[0] = 99999; // indicates a failure to find data in cursor
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " after yVals " + yVals[0]);
            }
            mCursor.close();
            return yVals;
        } else {

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Count " + count);
            }
            if (mCursor.moveToFirst()) {
                yVals = new double[count];
                xLabels = new String[count];
                do {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " begin do");
                    }
                    String word = mCursor.getString(0);
                    xLabels[yi] = mCursor.getString(1);
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " after word " + word);
                    }
                    double percent;
                    try {
                        percent = Double.parseDouble(word);
                    } catch (NumberFormatException e1) {
                        // for Locatlization error where ',' is used for decimal marker
                        e1.printStackTrace();
                        word = word.replace(".", ",");
                        percent = Double.parseDouble(word);
                    }
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

    @Override
    public void onStop() {

        super.onStop();

        //System.gc();
    }


    private static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        DENSITY_FACTOR = 1;
        if (density >= 4.0) {
            DENSITY_FACTOR = 4;
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            DENSITY_FACTOR = 3;
            return "xxhdpi";
        }
        if (density >= 2.0) {
            DENSITY_FACTOR = 2;
            return "xhdpi";
        }
        if (density >= 1.5) {
            DENSITY_FACTOR = 1.5;
            return "hdpi";
        }
        if (density >= 1.0) {
            DENSITY_FACTOR = 1;
            return "mdpi";
        }
        return "ldpi";
    }

}
