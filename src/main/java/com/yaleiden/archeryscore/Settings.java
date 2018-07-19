package com.yaleiden.archeryscore;

import android.app.backup.BackupManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;

public class Settings extends AppCompatActivity implements
         CustomAlertDialogTwo.CallbacksListener{

    private static final String TAG = "Settings";
    private String[] mMenuTitle;
    private String[] mMenuSummary;
    //private CheckBox checkBoxError;
    private int sound_enabled;
    private int fast_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.action_settings));
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Backup test section
        BackupManager bm = new BackupManager(this);
        bm.dataChanged();

        mMenuTitle = getResources().getStringArray(R.array.settingsMenuTitle);
        mMenuSummary = getResources().getStringArray(
                R.array.settingsMenuSummary);
        ListView setList = (ListView) findViewById(android.R.id.list);
        ListAdapter listAdapter = new SimpleAdapter(this, getListValues(),
                R.layout.list_item_settings, new String[]{"NAME", "DESC"},
                new int[]{R.id.text1, R.id.text2});
        setList.setAdapter(listAdapter);

        setList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position == 0) {
                    ClearLocalDatabase();
                } else if (position == 1) {
                    exportClick();
                } else if (position == 2) {
                    exportArchiveClick();
                } else if (position == 3) {
                    DeleteAutoCompleteNames();
                } else if (position == 4) {
                    changeSound();
                } else if (position == 5) {
                    DeleteCSVFile();
                } else if (position == 6) {
                    FastMode();
                } else {

                    Toast.makeText(Settings.this, "Retry your selection",
                            Toast.LENGTH_LONG).show();
                }

            }

        });

        SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
                0);
        //int reporting = app_preferences.getInt("REPORTING", 1);
        sound_enabled = app_preferences.getInt("SOUND", 1);
        fast_mode =  app_preferences.getInt("FAST", 1);

    }

    private List<Map<String, String>> getListValues() {
        List<Map<String, String>> values = new ArrayList<Map<String, String>>();
        int length = mMenuTitle.length;
        for (int i = 0; i < length; i++) {
            Map<String, String> v = new HashMap<String, String>();
            v.put("NAME", mMenuTitle[i]);
            v.put("DESC", mMenuSummary[i]);
            // v.put(ACTI, mMenuAction[i]);
            values.add(v);
        }
        return values;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_main_page) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (itemId == R.id.menu_archive) {
            //startActivity(new Intent(this, ViewArchiveList.class));
            startActivity(new Intent(this, ViewArchive.class));
        } else if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
        } else if (itemId == R.id.menu_help) {
            startActivity(new Intent(this, Help.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ******* Error reporting check button listener
    /*
    private void addListenerOnChkError() {

        checkBoxError.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // is chkIos checked?
                SharedPreferences app_preferences = getSharedPreferences(
                        "USER_PREFS", 0);
                SharedPreferences.Editor editor = app_preferences.edit();
                if (((CheckBox) v).isChecked()) {

                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.reporting_t))
                            .setAction(getString(R.string.reporting_on))
                                    //.setLabel()
                            .setValue(1)
                            .build());

                    editor.putInt("REPORTING", 1);
                    editor.commit();
                } else {
// Build and send an Event.
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.settings_t))
                            .setAction(getString(R.string.reporting_off))
                                    //.setLabel(getString(R.string.reporting_off))
                            .setValue(-1)
                            .build());
                    editor.putInt("REPORTING", 0);
                    editor.commit();
                }

            }
        });

    }
*/

    private void ClearLocalDatabase() {

        showCustomAlertDialogTwo();
        //getContentResolver().delete(ScoreProvider.SCORE_URI, "_id > -1", null);

    }

    private void DeleteCSVFile() {
        File exportDir = new File(Environment.getExternalStorageDirectory(),
                Constants.EXPORT_FOLDER);
        //Log.d(TAG, " " + exportDir.toString());
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "ArcheryScores.csv");
        //Log.d(TAG, " new File 'try'");
        if (file.exists())
            file.delete();
        Toast.makeText(Settings.this, R.string.csv_deleted, Toast.LENGTH_LONG)
                .show();

    }


    private void DeleteAutoCompleteNames() {
        getContentResolver().delete(ScoreProvider.NAME_URI, null, null);
        Toast.makeText(
                this,
                getApplicationContext().getString(
                        R.string.autocomplete_suggestions_cleared),
                Toast.LENGTH_LONG).show();
    }

    private void exportClick() {

        //Log.d(TAG, " onClick");
        new ExportDatabaseFileTask().execute("");
    }

    private void exportArchiveClick() {

        //Log.d(TAG, " onClick");
        new ExportArchiveFileTask().execute("");
    }

    private void changeSound() {

        SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
                0);

        sound_enabled = app_preferences.getInt("SOUND", 1);

        String msg = null;
        if (sound_enabled == 1) {

            app_preferences = getSharedPreferences("USER_PREFS", 0);
            SharedPreferences.Editor editor = app_preferences.edit();

            editor.putInt("SOUND", 0);
            editor.commit();
            // sound_enabled = 0;
            msg = getResources().getString(R.string.sound_disabled);

        }
        if (sound_enabled == 0) {

            app_preferences = getSharedPreferences("USER_PREFS", 0);
            SharedPreferences.Editor editor = app_preferences.edit();

            editor.putInt("SOUND", 1);
            editor.commit();
            // sound_enabled = 1;

            msg = getResources().getString(R.string.sound_enabled);
        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    private void FastMode(){
        SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
                0);

        fast_mode = app_preferences.getInt("FAST", 1);

        String msg = null;
        if (fast_mode == 1) {

            app_preferences = getSharedPreferences("USER_PREFS", 0);
            SharedPreferences.Editor editor = app_preferences.edit();

            editor.putInt("FAST", 0);
            editor.commit();
            // sound_enabled = 0;
            //msg = getResources().getString(R.string.fast_mode_disabled);

            FirebaseCrash.log("FAST MODE OFF");

        }
        if (fast_mode == 0) {

            app_preferences = getSharedPreferences("USER_PREFS", 0);
            SharedPreferences.Editor editor = app_preferences.edit();

            editor.putInt("FAST", 1);
            editor.commit();
            // sound_enabled = 1;
            FirebaseCrash.log("FAST MODE ON");
            //msg = getResources().getString(R.string.fast_mode_enabled);

        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void emailFile() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                Settings.this.getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder().append("<p><b>").append(Settings.this.getString(R.string.email_text)).append(" ").append(Settings.this.getString(R.string.app_name)).append(".  </p><p>").append(Settings.this.getString(R.string.email_get_app)).append("</b></p></a>").append("<br><br><h2><a href = ").append(Settings.this.getString(R.string.app_link)).append(">").append(Settings.this.getString(R.string.download)).append(" ").append(Settings.this.getString(R.string.app_name)).append("</a></h2>").toString()));
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root + "/" + Constants.EXPORT_FOLDER,
                "ArcheryScores.csv");
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(this,
                    Settings.this.getString(R.string.attachment_error),
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Uri uri = Uri.fromFile(file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Send email..."));

    }

    private void showCustomAlertDialogTwo() {
//Prepare dialog messages
        String title = getString(R.string.confirm_clear);
        String message = getString(R.string.confirm_clear_shoot);
        String positiveString = getString(R.string.yes);
        String negativeString = getString(R.string.no);
        String delete_selection = "CLEAR_ALL";
        CustomAlertDialogTwo.AlertDialogStrings customDialogStrings = new CustomAlertDialogTwo.AlertDialogStrings(title, message, positiveString, negativeString, delete_selection);
        CustomAlertDialogTwo customAlertDialogTwo = CustomAlertDialogTwo.newInstance(customDialogStrings);

        customAlertDialogTwo.show(getSupportFragmentManager(), "customAlertDialogTwoAll");

    }

    @Override
    public void onPositiveButtonClicked(String deleteString) {

        getContentResolver().delete(ScoreProvider.SCORE_URI, "_id > -1", null);

        SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
                0);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("USER_SHOOT", "Not set up");
        editor.putString("USER_COURSE", "Default");
        editor.putString("USER_TARGETS", "20");
        editor.putInt("USER_SCORING", 0);
        editor.putInt("REPORTING", 1);
        editor.commit();
        Toast.makeText(this, "Database cleared", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));

    }

    @Override
    public void onNegativeButtonClicked() {

    }

    class ExportDatabaseFileTask extends AsyncTask<String, Void, Boolean> {
        CSVWriter csvWrite;
        Cursor curCSV;
        File exportDir;
        File file;
        String arrStr[] = null;

        protected Boolean doInBackground(final String... args) {
            if(BuildConfig.DEBUG) {
                Log.d(TAG, " doInBackground");
            }
            // File dbFile = new File(Environment.getDataDirectory() +
            // "/data/data/com.yaleiden.archeryscore/databases/scores.db");

            exportDir = new File(Environment.getExternalStorageDirectory(),
                    Constants.EXPORT_FOLDER);
            if(BuildConfig.DEBUG) {
                Log.d(TAG, " " + exportDir.toString());
            }
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            file = new File(exportDir, "ArcheryScores.csv");
            try {
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, " new File 'try'");
                }
                file.createNewFile();
                csvWrite = new CSVWriter(new FileWriter(file));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            curCSV = getContentResolver().query(ScoreProvider.SCORE_URI, null,
                    "_id > -1", null, "_id");
            if(BuildConfig.DEBUG) {
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
                //if(BuildConfig.DEBUG) {
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
            if(BuildConfig.DEBUG) {
                Log.d(TAG, " curCSV.close");
            }

            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Toast.makeText(Settings.this, R.string.csv_saved, Toast.LENGTH_LONG)
                    .show();
            emailFile();
        }

    }

    private void emailArchive() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                Settings.this.getString(R.string.email_subject_archive));
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder().append("<p><b>").append(Settings.this.getString(R.string.email_text)).append(" ").append(Settings.this.getString(R.string.app_name)).append(".  </p><p>").append(Settings.this.getString(R.string.email_get_app)).append("</b></p></a>").append("<br><br><h2><a href = ").append(Settings.this.getString(R.string.app_link)).append(">").append(Settings.this.getString(R.string.download)).append(" ").append(Settings.this.getString(R.string.app_name)).append("</a></h2>").toString()));
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root + "/" + Constants.EXPORT_FOLDER,
                "ArcheryScoreArchive.csv");
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(this,
                    Settings.this.getString(R.string.attachment_error),
                    Toast.LENGTH_SHORT).show();
            finish();
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
            if(BuildConfig.DEBUG) {
                Log.d(TAG, " doInBackground");
            }
            // File dbFile = new File(Environment.getDataDirectory() +
            // "/data/data/com.yaleiden.archeryscore/databases/scores.db");

            exportDir = new File(Environment.getExternalStorageDirectory(),
                    Constants.EXPORT_FOLDER);
            if(BuildConfig.DEBUG) {
                Log.d(TAG, " " + exportDir.toString());
            }
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            file = new File(exportDir, "ArcheryScoreArchive.csv");
            try {
                if(BuildConfig.DEBUG) {
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
            curCSV = getContentResolver().query(ScoreProvider.ARCHIVE_URI,
                    null, "_id > -1", null, "_id");
            if(BuildConfig.DEBUG) {
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
                //if(BuildConfig.DEBUG) {
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
            if(BuildConfig.DEBUG) {
                Log.d(TAG, " curCSV.close");
            }

            return true;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (!FILE_CREATED) {
                Toast.makeText(Settings.this, R.string.check_sd,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Settings.this, R.string.csv_saved,
                        Toast.LENGTH_LONG).show();
                emailArchive();
            }
        }

    }

    @Override
    public void onStop() {

        super.onStop();

        System.gc();
    }


}
