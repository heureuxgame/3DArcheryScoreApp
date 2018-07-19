package com.yaleiden.archeryscore;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

public class ViewArchiveCards extends android.support.v4.app.Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ViewArchiveCards";
    private static final String[] FROM = new String[]{"Name", "SortDate", "Total",
            "Possible", "CourseName",
            "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11",
            "T12", "T13", "T14", "T15", "T16", "T17", "T18", "T19", "T20",
            "T21", "T22", "T23", "T24", "T25", "T26", "T27", "T28", "T29",
            "T30", "T31",
            "T32", "T33", "T34", "T35", "T36", "T37", "T38", "T39", "T40",
            "T41", "T42", "T43", "T44", "T45", "T46", "T47", "T48", "T49",
            "T50",
            "Targets", "Percentage", "Average", "ShootName", "_id"};
    private static final int[] TO = new int[]{R.id.textViewAname, R.id.textViewDate,
            R.id.textViewtotal, R.id.textViewCourse, R.id.textViewT1,
            R.id.textViewT2, R.id.textViewT3, R.id.textViewT4, R.id.textViewT5,
            R.id.textViewT6, R.id.textViewT7, R.id.textViewT8, R.id.textViewT9,
            R.id.textViewT10, R.id.textViewT11, R.id.textViewT12,
            R.id.textViewT13, R.id.textViewT14, R.id.textViewT15,
            R.id.textViewT16, R.id.textViewT17, R.id.textViewT18,
            R.id.textViewT19, R.id.textViewT20, R.id.textViewT21,
            R.id.textViewT22, R.id.textViewT23, R.id.textViewT24,
            R.id.textViewT25, R.id.textViewT26, R.id.textViewT27,
            R.id.textViewT28, R.id.textViewT29, R.id.textViewT30,
            R.id.textViewT31, R.id.textViewT32,
            R.id.textViewT33, R.id.textViewT34, R.id.textViewT35,
            R.id.textViewT36, R.id.textViewT37, R.id.textViewT38,
            R.id.textViewT39, R.id.textViewT40, R.id.textViewT41,
            R.id.textViewT42, R.id.textViewT43, R.id.textViewT44,
            R.id.textViewT45, R.id.textViewT46, R.id.textViewT47,
            R.id.textViewT48, R.id.textViewT49, R.id.textViewT50,
            R.id.textViewB1, R.id.textViewB2, R.id.textViewB3};
    private static final String SELECTION = "_id > -1";
    private int item_position;

    static final String ROW_ID = "_id"; // intent extra key
    // OnItemLongClickListener cardListLongClickListener;
    // SimpleCursorAdapter ArchiveCardAdapter; // adapter
    //private CursorAdapter ArchiveCardAdapter;
    private CursorAdapterArchiveCards ArchiveCardAdapter;
    private static final int ArchiveCardLoaderID = 2;
    private ListView cardlist; // activities listView
    //private TextView textViewMainTop;
    private RelativeLayout relativeLayoutBlank;
    private String delete_selection;
    // Identifier for the permission request
    private static final int READ_STORAGE_PERMISSIONS_REQUEST = 1;
    private static final int WRITE_STORAGE_PERMISSIONS_REQUEST = 2;


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
            return null;
        } else {
            // Inflate the layout for this fragment
            view = inflater
                    .inflate(R.layout.blank_fragment_list, container, false);
        }

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardlist = (ListView) view.findViewById(R.id.listviewFragStats); // consider the
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onViewCreated");
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onActivityCreated");
        }
        getLoaderManager().initLoader(ArchiveCardLoaderID, null, this);

        item_position = 0;
        boolean export = false;
        Bundle extras = getArguments();
        if (extras == null) {
//Keep going as normal
        } else {

            item_position = extras.getInt("position", 0);
            export = extras.getBoolean("export", false);
        }

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "item_position: " + item_position);
        }

        ArchiveCardAdapter = new CursorAdapterArchiveCards(getActivity(),
                R.layout.list_item_card_archive, null, FROM, TO);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "before cardlist.setAdapter set");
        }
        //ScoreObserver scoreObserver = new ScoreObserver(new Handler(), getActivity());

        cardlist.setOnItemLongClickListener(cardListLongClickListener);
        cardlist.setAdapter(ArchiveCardAdapter);

        //resetPosition();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "after getsupporloadermanager");
        }

        getLoaderManager().restartLoader(ArchiveCardLoaderID,
                savedInstanceState, this);
        if (export) {

            //new ExportArchiveFileTask().execute("");
            //checkPermissionReadStorage();
            exportArchive();
        }
    }
/*
    public void checkPermissionReadStorage(){
        Toast.makeText(getActivity(), "Checking permissions", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(), "Write to storage permission?", Toast.LENGTH_SHORT).show();
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_STORAGE_PERMISSIONS_REQUEST);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == WRITE_STORAGE_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Write to storage permission granted", Toast.LENGTH_SHORT).show();
                new ExportArchiveFileTask().execute("");
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                boolean showRationale = shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (showRationale) {
                    // do something here to handle degraded mode
                } else {
                    Toast.makeText(getActivity(), "Write to storage  permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
*/
    @Override
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onResume");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " onPause");
        }
    }


    private void resetPosition() {

        cardlist.post(new Runnable() {
            @Override
            public void run() {
                cardlist.setSelection(item_position);
            }
        });
    }

    private final OnItemLongClickListener cardListLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View v, int index,
                                       final long arg3) {
            //
            //String DELETE_SELECTION = "_id =" + arg3;
            delete_selection = "_id =" + arg3;
            showCustomAlertDialogTwo();
            item_position = index; //trying to keep position after deleting card
            return true;

        }
        //

    };


    private void deleteArchivedScore() {

    }


    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(getActivity(), ScoreProvider.ARCHIVE_URI, FROM,
                SELECTION, null, "_id DESC");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        ArchiveCardAdapter.swapCursor(cursor);
        resetPosition();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        ArchiveCardAdapter.swapCursor(null);
    }

    @Override
    public void onStop() {
        cardlist = null;
        super.onStop();

        System.gc();
    }


    private void emailArchive() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                ViewArchiveCards.this.getString(R.string.email_subject_archive));
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder().append("<p><b>").append(ViewArchiveCards.this.getString(R.string.email_text)).append(" ").append(ViewArchiveCards.this.getString(R.string.app_name)).append(".  </p><p>").append(ViewArchiveCards.this.getString(R.string.email_get_app)).append("</b></p></a>").append("<br><br><h2><a href = ").append(ViewArchiveCards.this.getString(R.string.app_link)).append(">").append(ViewArchiveCards.this.getString(R.string.download)).append(" ").append(ViewArchiveCards.this.getString(R.string.app_name)).append("</a></h2>").toString()));
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

    public void onPositiveButtonClicked(String deleteString) {
        getActivity().getContentResolver().delete(
                ScoreProvider.ARCHIVE_URI,
                deleteString, null);

    }

    public void exportArchive() {

        //checkPermissionReadStorage();
        new ExportArchiveFileTask().execute("");
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

    private void showCustomAlertDialogTwo() {
//Prepare dialog messages
        String title = getString(R.string.confirm_delete);
        String message = getString(R.string.delete_archive_scorecard);
        String positiveString = getString(R.string.yes);
        String negativeString = getString(R.string.no);

        CustomAlertDialogTwo.AlertDialogStrings customDialogStrings = new CustomAlertDialogTwo.AlertDialogStrings(title, message, positiveString, negativeString, delete_selection);
        CustomAlertDialogTwo customAlertDialogTwo = CustomAlertDialogTwo.newInstance(customDialogStrings);

        customAlertDialogTwo.show(getFragmentManager(), "customAlertDialogTwo");

    }
}
