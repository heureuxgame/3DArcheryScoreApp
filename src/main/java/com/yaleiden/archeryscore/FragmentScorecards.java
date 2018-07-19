package com.yaleiden.archeryscore;

import android.app.backup.BackupManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by Yale on 2/29/2016.
 */
public class FragmentScorecards extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "FragmentScorecards";
    private static final String[] FROM = new String[]{"Name", "Total",
            "SortDate", "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9",
            "T10", "T11", "T12", "T13", "T14", "T15", "T16", "T17", "T18",
            "T19", "T20", "T21", "T22", "T23", "T24", "T25", "T26", "T27",
            "T28", "T29", "T30", "T31", "T32", "T33", "T34", "T35", "T36",
            "T37", "T38", "T39", "T40", "T41", "T42", "T43", "T44", "T45",
            "T46", "T47", "T48", "T49", "T50", "_id"};
    private static final int[] TO = new int[]{R.id.textViewAname,
            R.id.textViewtotal, R.id.textViewDate, R.id.textViewT1,
            R.id.textViewT2, R.id.textViewT3, R.id.textViewT4, R.id.textViewT5,
            R.id.textViewT6, R.id.textViewT7, R.id.textViewT8, R.id.textViewT9,
            R.id.textViewT10, R.id.textViewT11, R.id.textViewT12,
            R.id.textViewT13, R.id.textViewT14, R.id.textViewT15,
            R.id.textViewT16, R.id.textViewT17, R.id.textViewT18,
            R.id.textViewT19, R.id.textViewT20, R.id.textViewT21,
            R.id.textViewT22, R.id.textViewT23, R.id.textViewT24,
            R.id.textViewT25, R.id.textViewT26, R.id.textViewT27,
            R.id.textViewT28, R.id.textViewT29, R.id.textViewT30,
            R.id.textViewT31, R.id.textViewT32, R.id.textViewT33,
            R.id.textViewT34, R.id.textViewT35, R.id.textViewT36,
            R.id.textViewT37, R.id.textViewT38, R.id.textViewT39,
            R.id.textViewT40, R.id.textViewT41, R.id.textViewT42,
            R.id.textViewT43, R.id.textViewT44, R.id.textViewT45,
            R.id.textViewT46, R.id.textViewT47, R.id.textViewT48,
            R.id.textViewT49, R.id.textViewT50};
    private static final String SELECTION = "_id > -1";
    private String sName;
    private String Row1;
    private String TotalTargets;
    private String Name;
    private String BowType;
    private String Division;
    // private String Total;
    private String ShootName;
    private String Date;
    private String SortDate;
    private String CourseName;
    private String Targets;
    private String Scoring;
    private String T1;
    private String T2;
    private String T3;
    private String T4;
    private String T5;
    private String T6;
    private String T7;
    private String T8;
    private String T9;
    private String T10;
    private String T11;
    private String T12;
    private String T13;
    private String T14;
    private String T15;
    private String T16;
    private String T17;
    private String T18;
    private String T19;
    private String T20;
    private String T21;
    private String T22;
    private String T23;
    private String T24;
    private String T25;
    private String T26;
    private String T27;
    private String T28;
    private String T29;
    private String T30;
    private String T31;
    private String T32;
    private String T33;
    private String T34;
    private String T35;
    private String T36;
    private String T37;
    private String T38;
    private String T39;
    private String T40;
    private String T41;
    private String T42;
    private String T43;
    private String T44;
    private String T45;
    private String T46;
    private String T47;
    private String T48;
    private String T49;
    private String T50;
    private int iTotal;
    private int totTargs;
    private double Avg;
    private String sAvg;
    private double Percent;
    private String sPercent;
    private int Possible;
    private String UserScoreTweet;
    private int reporting;
    StringBuilder sb;
    private StringBuilder ab;
    private boolean emailAll;

    static final String ROW_ID = "_id"; // intent extra key
    // OnItemLongClickListener myListLongClickListener;
    private SimpleCursorAdapter AllscoreAdapter; // adapter
    private static final int AllScoreLoaderID = 2;
    private ListView scorelist; // activities listView
    private int item_position;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //fragmentLayoutBlank = (LinearLayout) view.findViewById(R.id.fragmentLayoutBlank);
        scorelist = (ListView) view.findViewById(R.id.listviewFragStats);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after setcontentview");
        }
        item_position = 0;
        Bundle extras = getArguments();
        if (extras == null) {
//Keep going as normal
        } else {

            item_position = extras.getInt("position", 0);
            emailAll = extras.getBoolean("emailall", false);

        }

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "item_position: " + item_position);
        }

        getLoaderManager().initLoader(AllScoreLoaderID, null, this);

        AllscoreAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_card_score, null, FROM, TO, 0);

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "before scorelist.setAdapter set");
        }
        scorelist
                .setOnItemLongClickListener(myListLongClickListener);
        scorelist.setAdapter(AllscoreAdapter);
        getLoaderManager().restartLoader(AllScoreLoaderID,
                savedInstanceState, this);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "after getsupporloadermanager");
        }
        //Toast.makeText(getActivity(), R.string.long_click_to_email, Toast.LENGTH_LONG)
        //       .show();
        UserScoreTweet = null;
        //querydb();
        if (emailAll) {
            emailToGroup(null);

        }

    }

    public void querydb() {
        //Log.d(TAG, "querydb");
        int cCount;
        String PROJECTION[] = {"_id", "Name", "BowType", "Division", "Total",
                "ShootName", "Date", "SortDate", "CourseName", "Targets",
                "Scoring"};
        //String select = "_id =" + id;
        Cursor c = getActivity().getContentResolver().query(ScoreProvider.SCORE_URI,
                PROJECTION, null, null, null);
        assert c != null;
        cCount = c.getCount();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after getContentResolver get everything");
        }
        if (c.moveToFirst()) {
            Name = c.getString(1);
            BowType = c.getString(2);
            Division = c.getString(3);
            iTotal = c.getInt(4);
            ShootName = c.getString(5);
            Date = c.getString(6);
            SortDate = c.getString(7);
            CourseName = c.getString(8);
            Targets = c.getString(9);
            Scoring = c.getString(10);

            c.close();
        } else {
            c.close();
        }

        Toast.makeText(getActivity(), "cCount = " + cCount, Toast.LENGTH_LONG).show();
    }

    private void resetPosition() {

        scorelist.post(new Runnable() {
            @Override
            public void run() {
                scorelist.setSelection(item_position);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private final AdapterView.OnItemLongClickListener myListLongClickListener = new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> arg0, View v, int index,
                                       final long arg3) {

            TextView tx = (TextView) v.findViewById(R.id.textViewAname);
            sName = tx.getText().toString();


            String selectCard = "_id = " + arg3;
            showCustomAlertDialog(selectCard);


            return true;

        }
        //

    };

    public void GetEverthing(Long id) {
        String PROJECTION[] = {"_id", "Name", "BowType", "Division", "Total",
                "ShootName", "Date", "SortDate", "CourseName", "Targets",
                "Scoring"};
        String select = "_id =" + id;
        Cursor c = getActivity().getContentResolver().query(ScoreProvider.SCORE_URI,
                PROJECTION, select, null, null);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after getContentResolver get everything");
        }
        assert c != null;
        if (c.moveToFirst()) {
            Name = c.getString(1);
            BowType = c.getString(2);
            Division = c.getString(3);
            iTotal = c.getInt(4);
            ShootName = c.getString(5);
            Date = c.getString(6);
            SortDate = c.getString(7);
            CourseName = c.getString(8);
            Targets = c.getString(9);
            Scoring = c.getString(10);

            c.close();
        } else {
            c.close();
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " parse Targets " + Targets);
        }
        if (Targets != null) {
            // **** The if statements are to handle the old T1, T2 target
            // naming. Now it is 1, 2..
            char ct = Targets.charAt(0);
            if (ct == 'T') {
                totTargs = Integer.valueOf(Targets.substring(1));
            }
            if (ct != 'T') {
                totTargs = Integer.valueOf(Targets);
            }

            // totTargs = Integer.parseInt(Targets.substring(1));
            DecimalFormat df = new DecimalFormat("##.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            Avg = (iTotal / (double) totTargs); // supposed to be avg score +1
            // because Targets is really
            // last target number
            sAvg = df.format(Avg);
            String possible = Scoring.substring(Scoring.lastIndexOf('-') + 1);
            Possible = Integer.valueOf(possible) * totTargs;
            Percent = 100 * (iTotal / (double) Possible);
            sPercent = df.format(Percent);
            UserScoreTweet = Name + " " + getString(R.string.t_scored) + " "
                    + iTotal + "/" + Possible + " "
                    + getString(R.string.t_thru) + " " + totTargs + " "
                    + getString(R.string.t_targets_at) + " " + ShootName
                    + " - " + CourseName + " @3D_ArcheryApp ";

            SharedPreferences app_preferences = getActivity().getSharedPreferences(
                    "USER_PREFS", 0);
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("STATUS_UPDATE", UserScoreTweet);
            editor.commit();

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Percent " + Percent + " iTotal " + iTotal
                        + " Possible " + Possible);
            }
        } else {
            Toast.makeText(getActivity(), R.string.empty_scorecard, Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void emailFile() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getActivity().getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder().append("<p><b>").append(getString(R.string.email_text)).append(" ").append(getString(R.string.app_name)).append(".  </p><p>").append(getString(R.string.email_get_app)).append("</b></p></a>").append("<br><br><h2><a href = ").append(getString(R.string.app_link)).append(">").append(getString(R.string.download)).append(" ").append(getString(R.string.app_name)).append("</a></h2>").toString()));
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root + "/" + Constants.EXPORT_FOLDER,
                "ArcheryScores.csv");
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


    class ExportDatabaseFileTask extends AsyncTask<String, Void, Boolean> {
        CSVWriter csvWrite;
        Cursor curCSV;
        File exportDir;
        File file;
        String arrStr[] = null;

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

            file = new File(exportDir, "ArcheryScores.csv");
            try {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, " new File 'try'");
                }
                file.createNewFile();
                csvWrite = new CSVWriter(new FileWriter(file));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            curCSV = getActivity().getContentResolver().query(ScoreProvider.SCORE_URI, null,
                    "_id > -1", null, "_id");
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
                        curCSV.getString(60)};
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
            Toast.makeText(getActivity(), R.string.csv_saved, Toast.LENGTH_LONG)
                    .show();
            emailFile();
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(getActivity(), ScoreProvider.SCORE_URI, FROM, SELECTION,
                null, "Name");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        AllscoreAdapter.swapCursor(cursor);
        resetPosition();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        AllscoreAdapter.swapCursor(null);

    }

    @Override
    public void onStop() {

        super.onStop();

        System.gc();
    }


    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public void requestBackup() {
        BackupManager bm = new BackupManager(getActivity());
        bm.dataChanged();

        /** Also cache a reference to the Backup Manager */

        /** Set up our file bookkeeping */
        // mDataFile = new File(getFilesDir(), DatabaseSet.DATABASE_NAME);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "mBackupManager.dataChanged()");
        }

        // mBackupManager.dataChanged();
    }


    public void emailOneScorecard() {
        final Intent shareIntent = new Intent(
                Intent.ACTION_SENDTO, Uri
                .parse("mailto:"));
        shareIntent
                .putExtra(
                        Intent.EXTRA_SUBJECT,
                        sName
                                + "'s"
                                + " "
                                + getString(R.string.app_name)
                                + " Scorecard");
        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                Html.fromHtml(new StringBuilder()
                        .append(ab.toString()).append("<p><b>").append(getString(R.string.email_text)).append(" ").append(getString(R.string.app_name)).append(".  ").append(getString(R.string.email_get_app)).append("</b></p></a>").append("<br><br><h2><a href = ").append(getString(R.string.app_link)).append(">").append(getString(R.string.download)).append(" ").append(getString(R.string.app_name)).append("</a></h2>")
                        .toString()));


        // startActivity(shareIntent);
        startActivity(Intent.createChooser(
                shareIntent, "Send email..."));
    }

    public void archiveScorecard(String s) {
        // copy socrecard to archive and calculate
        // Called by function in View Scorecards

        resetTargetValues();

        new ArchiveScoreCardTask().execute(s);

/*

        ContentValues values = new ContentValues();
        values.put("Name", Name);
        values.put("BowType", BowType);
        values.put("Division", Division);
        values.put("Total", iTotal);
        values.put("ShootName", ShootName);
        values.put("Date", Date);
        values.put("SortDate", SortDate);
        values.put("CourseName", CourseName);
        values.put("Targets", Targets);
        values.put("Scoring", Scoring);
        values.put("T1", T1);
        values.put("T2", T2);
        values.put("T3", T3);
        values.put("T4", T4);
        values.put("T5", T5);
        values.put("T6", T6);
        values.put("T7", T7);
        values.put("T8", T8);
        values.put("T9", T9);
        values.put("T10", T10);
        values.put("T11", T11);
        values.put("T12", T12);
        values.put("T13", T13);
        values.put("T14", T14);
        values.put("T15", T15);
        values.put("T16", T16);
        values.put("T17", T17);
        values.put("T18", T18);
        values.put("T19", T19);
        values.put("T20", T20);
        values.put("T21", T21);
        values.put("T22", T22);
        values.put("T23", T23);
        values.put("T24", T24);
        values.put("T25", T25);
        values.put("T26", T26);
        values.put("T27", T27);
        values.put("T28", T28);
        values.put("T29", T29);
        values.put("T30", T30);
        values.put("T31", T31);
        values.put("T32", T32);
        values.put("T33", T33);
        values.put("T34", T34);
        values.put("T35", T35);
        values.put("T36", T36);
        values.put("T37", T37);
        values.put("T38", T38);
        values.put("T39", T39);
        values.put("T40", T40);
        values.put("T41", T41);
        values.put("T42", T42);
        values.put("T43", T43);
        values.put("T44", T44);
        values.put("T45", T45);
        values.put("T46", T46);
        values.put("T47", T47);
        values.put("T48", T48);
        values.put("T49", T49);
        values.put("T50", T50);
        values.put("Average", sAvg);
        values.put("Percentage", sPercent);
        values.put("Possible", Possible);

        getActivity().getContentResolver().insert(
                ScoreProvider.ARCHIVE_URI, values);


        requestBackup();

        Toast.makeText(getActivity(),
                R.string.scores_archived,
                Toast.LENGTH_LONG).show();
        */
    }

    public void sendTextScore() {
        String textMessage = Row1 + " at " + CourseName;

        // SmsManager sms = SmsManager.getDefault();
        // sms.sendTextMessage("99999999999", null,
        // textMessage, null, null);

        Intent sendIntent = new Intent(
                Intent.ACTION_VIEW);
        sendIntent
                .putExtra("sms_body", textMessage);
        sendIntent
                .setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);


    }

    private void showCustomAlertDialog(String selection) {
//Prepare dialog messages
        String title = getString(R.string.email_or_archive_cards);
        String message = sName + "\'s " + getString(R.string.score)
                + "?";
        String positiveString = getString(R.string.email);
        String negativeString = getString(R.string.archive);
        String neutralString = getString(R.string.sms);
        CustomAlertDialog.AlertDialogStrings customDialogStrings =
                new CustomAlertDialog.AlertDialogStrings
                        (title, message, positiveString, negativeString, neutralString, selection);
        CustomAlertDialog customAlertDialog =
                CustomAlertDialog.newInstance(customDialogStrings);
        // Create an instance of the dialog fragment and show it
        customAlertDialog.show(getFragmentManager(), "customAlertDialog");

    }

    //*** method used in AsyncTask
    private String makeOneCard(String selection) {


        String card = "";
        StringBuilder cardBuilder = new StringBuilder();

        String PROJECTION[] = {"_id", "Name", "BowType", "Division", "Total",
                "ShootName", "Date", "SortDate", "CourseName", "Targets",
                "Scoring", "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11",
                "T12", "T13", "T14", "T15", "T16", "T17", "T18", "T19", "T20",
                "T21", "T22", "T23", "T24", "T25", "T26", "T27", "T28", "T29",
                "T30", "T31",
                "T32", "T33", "T34", "T35", "T36", "T37", "T38", "T39", "T40",
                "T41", "T42", "T43", "T44", "T45", "T46", "T47", "T48", "T49",
                "T50"};
        //String select = "_id =" + id;
        Cursor c = getActivity().getContentResolver().query(ScoreProvider.SCORE_URI,
                PROJECTION, selection, null, null);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after getContentResolver get everything");
        }
        if (c != null) {
            c.moveToFirst();
            do {
                {
                    resetTargetValues();

                    Name = c.getString(1);
                    BowType = c.getString(2);
                    Division = c.getString(3);
                    iTotal = c.getInt(4);
                    ShootName = c.getString(5);
                    Date = c.getString(6);
                    SortDate = c.getString(7);
                    CourseName = c.getString(8);
                    Targets = c.getString(9);
                    Scoring = c.getString(10);

                    T1 = checkNotNull(c, 11);
                    T2 = checkNotNull(c, 12);
                    T3 = checkNotNull(c, 13);
                    T4 = checkNotNull(c, 14);
                    T5 = checkNotNull(c, 15);
                    T6 = checkNotNull(c, 16);
                    T7 = checkNotNull(c, 17);
                    T8 = checkNotNull(c, 18);
                    T9 = checkNotNull(c, 19);
                    T10 = checkNotNull(c, 20);
                    T11 = checkNotNull(c, 21);
                    T12 = checkNotNull(c, 22);
                    T13 = checkNotNull(c, 23);
                    T14 = checkNotNull(c, 24);
                    T15 = checkNotNull(c, 25);
                    T16 = checkNotNull(c, 26);
                    T17 = checkNotNull(c, 27);
                    T18 = checkNotNull(c, 28);
                    T19 = checkNotNull(c, 29);
                    T20 = checkNotNull(c, 30);
                    T21 = checkNotNull(c, 31);
                    T22 = checkNotNull(c, 32);
                    T23 = checkNotNull(c, 33);
                    T24 = checkNotNull(c, 34);
                    T25 = checkNotNull(c, 35);
                    T26 = checkNotNull(c, 36);
                    T27 = checkNotNull(c, 37);
                    T28 = checkNotNull(c, 38);
                    T29 = checkNotNull(c, 39);
                    T30 = checkNotNull(c, 40);
                    T31 = checkNotNull(c, 41);
                    T32 = checkNotNull(c, 42);
                    T33 = checkNotNull(c, 43);
                    T34 = checkNotNull(c, 44);
                    T35 = checkNotNull(c, 45);
                    T36 = checkNotNull(c, 46);
                    T37 = checkNotNull(c, 47);
                    T38 = checkNotNull(c, 48);
                    T39 = checkNotNull(c, 49);
                    T40 = checkNotNull(c, 50);
                    T41 = checkNotNull(c, 51);
                    T42 = checkNotNull(c, 52);
                    T43 = checkNotNull(c, 53);
                    T44 = checkNotNull(c, 54);
                    T45 = checkNotNull(c, 55);
                    T46 = checkNotNull(c, 56);
                    T47 = checkNotNull(c, 57);
                    T48 = checkNotNull(c, 58);
                    T49 = checkNotNull(c, 59);
                    T50 = checkNotNull(c, 60);


                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " parse Targets " + Targets);
                    }
                    if (Targets != null) {
                        // **** The if statements are to handle the old T1, T2 target
                        // naming. Now it is 1, 2..
                        char ct = Targets.charAt(0);
                        if (ct == 'T') {
                            totTargs = Integer.valueOf(Targets.substring(1));
                        }
                        if (ct != 'T') {
                            totTargs = Integer.valueOf(Targets);
                        }

                        // totTargs = Integer.parseInt(Targets.substring(1));
                        DecimalFormat df = new DecimalFormat("##.##");
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setDecimalSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
                        Avg = (iTotal / (double) totTargs); // supposed to be avg score +1
                        // because Targets is really
                        // last target number
                        sAvg = df.format(Avg);
                        String possible = Scoring.substring(Scoring.lastIndexOf('-') + 1);
                        Possible = Integer.valueOf(possible) * totTargs;
                        Percent = 100 * (iTotal / (double) Possible);
                        sPercent = df.format(Percent);

                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "Percent " + Percent + " iTotal " + iTotal
                                    + " Possible " + Possible);
                        }
                    } else {
                        //Keep going as normal
                    }


                    String a = null;
                    StringBuilder oneCard = new StringBuilder();

                    Row1 = Name + "'s" + " " + getString(R.string.total_score) + " "
                            + iTotal;
                    String row1a = "Date: " + SortDate;

                    String rowShoot = getString(R.string.shoot_) + " " + ShootName;
                    String rowCourse = getString(R.string.course_) + " " + CourseName;
                    String rowBow = getString(R.string.bow_type) + ": " + BowType;
                    String rowScore = getString(R.string.scoring_) + " 0-" + Scoring;
                    String rowStats = getString(R.string.score_stats) + "<br> " + sPercent
                            + "% " + getString(R.string.scoring_percentage) + "<br>"
                            + sAvg + " " + getString(R.string.per_arrow);
                    String rowTargets = getString(R.string.total_targets) + " " + totTargs;

                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "Row2 = " + T1 + T2 + T3 + T4 + T5
                                + T6 + T7 + T8 + T9 + T10);
                    }

                    String row2 = T1 + T2 + T3 + T4 + T5
                            + T6 + T7 + T8 + T9 + T10;

                    String row3 = T11 + T12 + T13 + T14 + T15
                            + T16 + T17 + T18 + T19 + T20;

                    String row4 = T21 + T22 + T23 + T24 + T25
                            + T26 + T27 + T28 + T29 + T30;

                    String row5 = T31 + T32 + T33 + T34 + T35
                            + T36 + T37 + T38 + T39 + T40;

                    String row6 = T41 + T42 + T43 + T44 + T45
                            + T46 + T47 + T48 + T49 + T50;

                    oneCard.append("<p><h1>").append(Row1).append("</h1></p>");
                    oneCard.append("<h3><p>").append(row1a).append("<br>");
                    oneCard.append(rowShoot).append("<br>").append(rowCourse).append("<br>").append(rowScore).append("<br>").append(rowBow).append("<br></p>");
                    oneCard.append("<p>").append(rowStats).append("<br>");
                    oneCard.append("").append(rowTargets).append("</h3></p>");

                    if (totTargs > 0) {
                        oneCard.append("<h2><p>").append(row2).append("</p>");
                    } else {
                        row2 = "";
                    }
                    if (totTargs > 10) {
                        oneCard.append("<p>").append(row3).append("</p>");
                    } else {
                        row3 = "";
                    }
                    if (totTargs > 20) {
                        oneCard.append("<p>").append(row4).append("</p>");
                    } else {
                        row4 = "";
                    }
                    if (totTargs > 30) {
                        oneCard.append("<p>").append(row5).append("</p>");
                    } else {
                        row5 = "";
                    }
                    if (totTargs > 40) {
                        oneCard.append("<p>").append(row6).append("</p></h2>");
                    } else {
                        row6 = "";
                    }
                    oneCard.append("<p><h4>" + "</h4></p>");
                    a = oneCard.toString();
                    cardBuilder.append(a);


                }


            } while (c.moveToNext());
            c.close();
            //End of cursor loop

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "inside else");
            }
        } else {
            assert c != null;
            c.close();
        }

        card = cardBuilder.toString();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "card " + card);
        }
        return card;
    }

    private ContentValues getArchiveValues(String selection) {
        ContentValues values = new ContentValues();
        String PROJECTION[] = {"_id", "Name", "BowType", "Division", "Total",
                "ShootName", "Date", "SortDate", "CourseName", "Targets",
                "Scoring", "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11",
                "T12", "T13", "T14", "T15", "T16", "T17", "T18", "T19", "T20",
                "T21", "T22", "T23", "T24", "T25", "T26", "T27", "T28", "T29",
                "T30", "T31",
                "T32", "T33", "T34", "T35", "T36", "T37", "T38", "T39", "T40",
                "T41", "T42", "T43", "T44", "T45", "T46", "T47", "T48", "T49",
                "T50"};
        //String select = "_id =" + id;
        Cursor c = getActivity().getContentResolver().query(ScoreProvider.SCORE_URI,
                PROJECTION, selection, null, null);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after getContentResolver get everything");
        }
        if (c != null) {
            c.moveToFirst();
            do {
                {
                    resetTargetValues();


                    Name = c.getString(1);
                    BowType = c.getString(2);
                    Division = c.getString(3);
                    iTotal = c.getInt(4);
                    ShootName = c.getString(5);
                    Date = c.getString(6);
                    SortDate = c.getString(7);
                    CourseName = c.getString(8);
                    Targets = c.getString(9);
                    Scoring = c.getString(10);


                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, " parse Targets " + Targets);
                    }
                    if (Targets != null) {
                        // **** The if statements are to handle the old T1, T2 target
                        // naming. Now it is 1, 2..
                        char ct = Targets.charAt(0);
                        if (ct == 'T') {
                            totTargs = Integer.valueOf(Targets.substring(1));
                        }
                        if (ct != 'T') {
                            totTargs = Integer.valueOf(Targets);
                        }

                        // totTargs = Integer.parseInt(Targets.substring(1));
                        DecimalFormat df = new DecimalFormat("##.##");
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setDecimalSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
                        Avg = (iTotal / (double) totTargs); // supposed to be avg score +1
                        // because Targets is really
                        // last target number
                        sAvg = df.format(Avg);
                        String possible = Scoring.substring(Scoring.lastIndexOf('-') + 1);
                        Possible = Integer.valueOf(possible) * totTargs;
                        Percent = 100 * (iTotal / (double) Possible);
                        sPercent = df.format(Percent);

                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "Percent " + Percent + " iTotal " + iTotal
                                    + " Possible " + Possible);
                        }
                    } else {
                        //Keep going as normal
                    }

                    values.put("Name", Name);
                    values.put("BowType", BowType);
                    values.put("Division", Division);
                    values.put("Total", iTotal);
                    values.put("ShootName", ShootName);
                    values.put("Date", Date);
                    values.put("SortDate", SortDate);
                    values.put("CourseName", CourseName);
                    values.put("Targets", Targets);
                    values.put("Scoring", Scoring);



                    values.put("T1", checkIntNotNull(c, 11));
                    values.put("T2", checkIntNotNull(c, 12));
                    values.put("T3", checkIntNotNull(c, 13));
                    values.put("T4", checkIntNotNull(c, 14));
                    values.put("T5", checkIntNotNull(c, 15));
                    values.put("T6", checkIntNotNull(c, 16));
                    values.put("T7", checkIntNotNull(c, 17));
                    values.put("T8", checkIntNotNull(c, 18));
                    values.put("T9", checkIntNotNull(c, 19));
                    values.put("T10", checkIntNotNull(c, 20));
                    values.put("T11", checkIntNotNull(c, 21));
                    values.put("T12", checkIntNotNull(c, 22));
                    values.put("T13", checkIntNotNull(c, 23));
                    values.put("T14", checkIntNotNull(c, 24));
                    values.put("T15", checkIntNotNull(c, 25));
                    values.put("T16", checkIntNotNull(c, 26));
                    values.put("T17", checkIntNotNull(c, 27));
                    values.put("T18", checkIntNotNull(c, 28));
                    values.put("T19", checkIntNotNull(c, 29));
                    values.put("T20", checkIntNotNull(c, 30));
                    values.put("T21", checkIntNotNull(c, 31));
                    values.put("T22", checkIntNotNull(c, 32));
                    values.put("T23", checkIntNotNull(c, 33));
                    values.put("T24", checkIntNotNull(c, 34));
                    values.put("T25", checkIntNotNull(c, 35));
                    values.put("T26", checkIntNotNull(c, 36));
                    values.put("T27", checkIntNotNull(c, 37));
                    values.put("T28", checkIntNotNull(c, 38));
                    values.put("T29", checkIntNotNull(c, 39));
                    values.put("T30", checkIntNotNull(c, 40));
                    values.put("T31", checkIntNotNull(c, 41));
                    values.put("T32", checkIntNotNull(c, 42));
                    values.put("T33", checkIntNotNull(c, 43));
                    values.put("T34", checkIntNotNull(c, 44));
                    values.put("T35", checkIntNotNull(c, 45));
                    values.put("T36", checkIntNotNull(c, 46));
                    values.put("T37", checkIntNotNull(c, 47));
                    values.put("T38", checkIntNotNull(c, 48));
                    values.put("T39", checkIntNotNull(c, 49));
                    values.put("T40", checkIntNotNull(c, 50));
                    values.put("T41", checkIntNotNull(c, 51));
                    values.put("T42", checkIntNotNull(c, 52));
                    values.put("T43", checkIntNotNull(c, 53));
                    values.put("T44", checkIntNotNull(c, 54));
                    values.put("T45", checkIntNotNull(c, 55));
                    values.put("T46", checkIntNotNull(c, 56));
                    values.put("T47", checkIntNotNull(c, 57));
                    values.put("T48", checkIntNotNull(c, 58));
                    values.put("T49", checkIntNotNull(c, 59));
                    values.put("T50", checkIntNotNull(c, 60));


                    values.put("Average", sAvg);
                    values.put("Percentage", sPercent);
                    values.put("Possible", Possible);


                }


            } while (c.moveToNext());
            c.close();
            //End of cursor loop

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "inside else");
            }
        } else {
            assert c != null;
            c.close();
        }

        return values;
    }

    public void emailToGroup(String s) {
        new EmailAllFileTask().execute(s);
    }

    private class EmailAllFileTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result;
            String selection = params[0];
            result = makeOneCard(selection);

            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPreExecute ");
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPostExecute ");
            }
            EmailGroupScores(s);

        }
    }

    private class ArchiveScoreCardTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String selection = params[0];

            getActivity().getContentResolver().insert(
                    ScoreProvider.ARCHIVE_URI, getArchiveValues(selection));
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPreExecute ");
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            BackupManager bm = new BackupManager(getActivity());
            bm.dataChanged();
            Toast.makeText(getActivity(), R.string.scores_archived, Toast.LENGTH_LONG)
                    .show();
        }


    }

    private void EmailGroupScores(String body) {
        View itemView = null;

        ab = new StringBuilder();

        ab.append(body);
        final Intent shareIntent = new Intent(
                Intent.ACTION_SENDTO, Uri
                .parse("mailto:"));
        shareIntent
                .putExtra(
                        Intent.EXTRA_SUBJECT,
                        getString(R.string.app_name)
                                + " Scorecards");
        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                Html.fromHtml(ab.append("<p><b>").append(getString(R.string.email_text)).append(" ").append(getString(R.string.app_name)).append(".  ").append(getString(R.string.email_get_app)).append("</b></p></a>").append("<br><br><h2><a href = ").append(getString(R.string.app_link)).append(">").append(getString(R.string.download)).append(" ").append(getString(R.string.app_name)).append("</a></h2>")
                        .toString()));

        startActivity(Intent.createChooser(
                shareIntent, "Send email..."));

    }

    private String checkNotNull(Cursor c, int val) {

        Integer result = c.getInt(val);
        String ans = "";
        if (!c.isNull(val)) {
            //Log.d(TAG, "NOT checkNotNull");
            if (result != null) {
                ans = String.valueOf(result) + ", ";
            } else {
                ans = "";
            }


        }
        return ans;
    }

    private String checkIntNotNull(Cursor c, int val) {

        Integer result = c.getInt(val);
        String ans = "";
        if (!c.isNull(val)) {
            //Log.d(TAG, "NOT checkNotNull");
            if (result != null) {
                ans = String.valueOf(result);
            } else {
                ans = null;
            }


        }
        return ans;
    }

    private void resetTargetValues() {
        T1 = null;
        T2 = null;
        T3 = null;
        T4 = null;
        T5 = null;
        T6 = null;
        T7 = null;
        T8 = null;
        T9 = null;
        T10 = null;
        T11 = null;
        T12 = null;
        T13 = null;
        T14 = null;
        T15 = null;
        T16 = null;
        T17 = null;
        T18 = null;
        T19 = null;
        T20 = null;
        T21 = null;
        T22 = null;
        T23 = null;
        T24 = null;
        T25 = null;
        T26 = null;
        T27 = null;
        T28 = null;
        T29 = null;
        T30 = null;
        T31 = null;
        T32 = null;
        T33 = null;
        T34 = null;
        T35 = null;
        T36 = null;
        T37 = null;
        T38 = null;
        T39 = null;
        T40 = null;
        T41 = null;
        T42 = null;
        T43 = null;
        T44 = null;
        T45 = null;
        T46 = null;
        T47 = null;
        T48 = null;
        T49 = null;
        T50 = null;

    }
}



