package com.yaleiden.archeryscore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

public class ViewArchiveStats extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>  {

    private static final String TAG = "ViewArchiveStats";
    private static final int ColorLoaderID = 1;
    private ListView mylist; // activities listView
    //private CursorAdapterResult colorAdapter; // adapter
    private CursorAdapterResult colorAdapter;
    private static final String[] FROM = new String[]{"Name",
            "BowType",
            "Division",
            "Total",
            "ShootName",
            "Date",
            "SortDate",
            "CourseName",
            "Targets",
            "Scoring",
            "T1",
            "T2",
            "T3",
            "T4",
            "T5",
            "T6",
            "T7",
            "T8",
            "T9",
            "T10",
            "T11",
            "T12",
            "T13",
            "T14",
            "T15",
            "T16",
            "T17",
            "T18",
            "T19",
            "T20",
            "T21",
            "T22",
            "T23",
            "T24",
            "T25",
            "T26",
            "T27",
            "T28",
            "T29",
            "T30",
            "T31",
            "T32",
            "T33",
            "T34",
            "T35",
            "T36",
            "T37",
            "T38",
            "T39",
            "T40",
            "T41",
            "T42",
            "T43",
            "T44",
            "T45",
            "T46",
            "T47",
            "T48",
            "T49",
            "T50",
            "Average",
            "Percentage",
            "Possible",
            "_id"};

    private static final int[] TO = new int[]{R.id.textViewResult1, R.id.textViewResult1,
            R.id.textViewResult1};
    private static final String SELECTION = "_id > -1";
    //private LinearLayout fragmentLayoutBlank;
    private OnStatSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnStatSelectedListener {
        void OnStatSelected(int position, long id);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        //setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        // TODO Auto-generated method stub
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnStatSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnArcherSelectedListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(BuildConfig.DEBUG) {
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
            return null;
        }else {
            // Inflate the layout for this fragment
            view = inflater
                    .inflate(R.layout.blank_fragment_list, container, false);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fragmentLayoutBlank = (LinearLayout) view.findViewById(R.id.fragmentLayoutBlank);
        //ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView1Shoot);
        //imageView1.setImageResource(R.drawable.ba_list_g);
        mylist = (ListView) view.findViewById(R.id.listviewFragStats);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (BuildConfig.DEBUG) {
            android.util.Log.d(TAG, " onActivityCreated");
        }
        getLoaderManager().initLoader(ColorLoaderID,
                null, this);
        ScoreObserver scoreObserver = new ScoreObserver(new Handler(), getActivity());

        colorAdapter = new CursorAdapterResult(getActivity(), R.layout.list_item_result, null, FROM, TO);

        mylist.setAdapter(colorAdapter);
        mylist.setOnItemClickListener(myItemClickListener);
        getLoaderManager().restartLoader(ColorLoaderID, savedInstanceState, this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(getActivity(), ScoreProvider.ARCHIVE_URI, FROM,
                SELECTION, null, "_id DESC");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        colorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        colorAdapter.swapCursor(null);

    }

    private final AdapterView.OnItemClickListener myItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {


            mCallback.OnStatSelected(position, id);
            /*
            Intent intent = new Intent(ViewArchiveStats.this, ViewArchiveCards.class);
            intent.putExtra("position", position);
            intent.putExtra("id", id);
            startActivity(intent);
            */
        }
    };



    @Override
    public void onStart() {
        super.onStart();
        //getContentResolver().registerContentObserver(ScoreProvider.ARCHIVE_URI,
        //		true, scoreObserver);
        // Add this method.
        //EasyTracker.getInstance(this).activityStart(this); // Add this method.
    }

    @Override
    public void onStop() {

        super.onStop();

        System.gc();
        // getContentResolver().unregisterContentObserver(scoreObserver);

    }

    private void emailArchive() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                ViewArchiveStats.this.getString(R.string.email_subject_archive));
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder().append("<p><b>").append(ViewArchiveStats.this.getString(R.string.email_text)).append(" ").append(ViewArchiveStats.this.getString(R.string.app_name)).append(".  </p><p>").append(ViewArchiveStats.this.getString(R.string.email_get_app)).append("</b></p></a>").append("<br><br><h2><a href = ").append(ViewArchiveStats.this.getString(R.string.app_link)).append(">").append(ViewArchiveStats.this.getString(R.string.download)).append(" ").append(ViewArchiveStats.this.getString(R.string.app_name)).append("</a></h2>").toString()));
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root + "/" + Constants.EXPORT_FOLDER,
                "ArcheryScoreArchive.csv");
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(getActivity(),
                    getString(R.string.attachment_error),
                    Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        Uri uri = Uri.fromFile(file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Send email..."));
    }

    class ExportArchiveFileTask extends AsyncTask<String, Void, Boolean> {
        CSVWriter csvWrite;
        Cursor curCSV;
        File exportDir;
        File file;
        String arrStr[] = null;
        boolean FILE_CREATED = true;

        protected Boolean doInBackground(final String... args) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " doInBackground");
            }
            // File dbFile = new File(Environment.getDataDirectory() +
            // "/data/data/com.yaleiden.archeryscore/databases/scores.db");

            exportDir = new File(Environment.getExternalStorageDirectory(),
                    Constants.EXPORT_FOLDER);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " " + exportDir.toString());
            }
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            file = new File(exportDir, "ArcheryScoreArchive.csv");
            try {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, " new File 'try'");
                }
                file.createNewFile();
                csvWrite = new CSVWriter(new FileWriter(file));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                FILE_CREATED = false;
                return true;
            }
            curCSV = getActivity().getContentResolver().query(ScoreProvider.ARCHIVE_URI,
                    null, "_id > -1", null, "_id");
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " getContentResolver");
            }
            assert curCSV != null;
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext())

            {
                String[] arrStr = {curCSV.getString(0), curCSV.getString(1),
                        curCSV.getString(2), curCSV.getString(3),
                        curCSV.getString(4), curCSV.getString(5),
                        curCSV.getString(6), curCSV.getString(7),
                        curCSV.getString(8), curCSV.getString(9),
                        curCSV.getString(10), curCSV.getString(11),
                        curCSV.getString(12), curCSV.getString(13),
                        curCSV.getString(14), curCSV.getString(15),
                        curCSV.getString(16), curCSV.getString(17),
                        curCSV.getString(18), curCSV.getString(19),
                        curCSV.getString(20), curCSV.getString(21),
                        curCSV.getString(22), curCSV.getString(23),
                        curCSV.getString(24), curCSV.getString(25),
                        curCSV.getString(26), curCSV.getString(27),
                        curCSV.getString(28), curCSV.getString(29),
                        curCSV.getString(30), curCSV.getString(31),
                        curCSV.getString(32), curCSV.getString(33),
                        curCSV.getString(34), curCSV.getString(35),
                        curCSV.getString(36), curCSV.getString(37),
                        curCSV.getString(38), curCSV.getString(39),
                        curCSV.getString(40), curCSV.getString(41),
                        curCSV.getString(42), curCSV.getString(43),
                        curCSV.getString(44), curCSV.getString(45),
                        curCSV.getString(46), curCSV.getString(47),
                        curCSV.getString(48), curCSV.getString(49),
                        curCSV.getString(50), curCSV.getString(51),
                        curCSV.getString(52), curCSV.getString(53),
                        curCSV.getString(54), curCSV.getString(55),
                        curCSV.getString(56), curCSV.getString(57),
                        curCSV.getString(58), curCSV.getString(59),
                        curCSV.getString(60), curCSV.getString(61),
                        curCSV.getString(62), curCSV.getString(63)};
                csvWrite.writeNext(arrStr);
                //if (BuildConfig.DEBUG) {
                //    Log.d(TAG, " arrStr " + arrStr.toString());
                //}
            }
            try {
                csvWrite.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            curCSV.close();
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " curCSV.close");
            }

            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (!FILE_CREATED) {
                Toast.makeText(getActivity(), R.string.check_sd,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), R.string.csv_saved,
                        Toast.LENGTH_LONG).show();
                emailArchive();
            }
        }

    }
}