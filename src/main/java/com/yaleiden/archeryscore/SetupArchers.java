package com.yaleiden.archeryscore;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SetupArchers extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "SetupArchers";
    private String USER_SHOOT;
    private String USER_COURSE;
    private String user_scoring;
    private AutoCompleteTextView AutotextView;
    private static final String[] FROM = new String[]{"Name", "_id"};
    private static final int[] TO = new int[]{R.id.text};
    private SimpleCursorAdapter NameAdapter; // adapter
    private static final int NameLoaderID = 2;
    private String filterSelect;
    private Cursor filterCursor;
    private ScrollView ScrollView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_archers);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.archer_setup));

        ScrollView1 = (ScrollView) findViewById(R.id.ScrollView1);
        BackgroundSetter aSetter = new BackgroundSetter(this, ScrollView1);
        aSetter.applyBkg();

        SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
                0);
        USER_SHOOT = app_preferences.getString("USER_SHOOT", "Default");
        USER_COURSE = app_preferences.getString("USER_COURSE", "Default");
        String USER_TARGETS = app_preferences.getString("USER_TARGETS", "20");
        int USER_SCORING = app_preferences.getInt("USER_SCORING", 0);
        TextView textShoot = (TextView) findViewById(R.id.textViewshoot);
        textShoot
                .setText(this.getString(R.string.shoot_) + " " + USER_SHOOT);
        TextView textCourse = (TextView) findViewById(R.id.textViewcourse);
        textCourse.setText(this.getString(R.string.course_) + " "
                + USER_COURSE);
        TextView textTargets = (TextView) findViewById(R.id.textViewtargets);
        textTargets.setText(this.getString(R.string.number_of_targets) + ": "
                + USER_TARGETS);
        ScoreArrays scoreArrays = new ScoreArrays();
        user_scoring = scoreArrays.getScoreText(USER_SCORING);

        TextView textScoring = (TextView) findViewById(R.id.textViewScoring);
        textScoring.setText(this.getString(R.string.scoring_) + " "
                + user_scoring);
        AutotextView = (AutoCompleteTextView) findViewById(R.id.AutoCompleteArcher);

        getSupportLoaderManager().initLoader(NameLoaderID, null, this);


        NameAdapter = new SimpleCursorAdapter(this,
                R.layout.list_item_custom_autocomplete, null, FROM, TO, 1);
        NameAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
            public CharSequence convertToString(Cursor cursor) {
                return cursor.getString(0);
            }
        });

        NameAdapter.setFilterQueryProvider(new FilterQueryProvider() {

            @Override
            public Cursor runQuery(CharSequence constraint) {
                filterSelect = "Name LIKE '" + constraint + "%'";
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "filterSelect " + filterSelect);
                }

                filterCursor = getContentResolver().query(
                        ScoreProvider.NAME_URI, FROM, filterSelect, null,
                        null);

                return filterCursor;
            }

        });

        AutotextView.setAdapter(NameAdapter);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "after Autotext set NameAdapter");
        }
        getSupportLoaderManager().restartLoader(NameLoaderID,
                null, SetupArchers.this);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "after getsupporloadermanager");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public void saveArcher(View v) {
        String name = AutotextView.getText().toString();
        // editTextArcher = (EditText) findViewById(R.id.editTextArcher);
        Spinner spinnerBowType = (Spinner) findViewById(R.id.spinnerBowType);
        Spinner spinnerDivision = (Spinner) findViewById(R.id.spinnerDivision);
        // String name = editTextArcher.getText().toString();
        String bowType = spinnerBowType.getSelectedItem().toString();
        String division = spinnerDivision.getSelectedItem().toString();
        SimpleDateFormat ndf = new SimpleDateFormat("MM-dd-yy");
        String sortDate = ndf.format(new Date());
        Date date = new Date();
        String dateString = date.toString();
        ContentValues newScore = new ContentValues();
        newScore.put("Name", name);
        newScore.put("BowType", bowType);
        newScore.put("Division", division);
        newScore.put("SortDate", sortDate);
        newScore.put("ShootName", USER_SHOOT);
        newScore.put("CourseName", USER_COURSE);
        newScore.put("Date", dateString);
        newScore.put("Scoring", user_scoring);

        // send to local db via content provider
        getContentResolver().insert(ScoreProvider.SCORE_URI, newScore);

        ContentValues newName = new ContentValues();
        newName.put("Name", name);
        newName.put("BowType", bowType);
        newName.put("Division", "ARCHER");
        getContentResolver().insert(ScoreProvider.NAME_URI, newName);
        removeDuplicates();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "after getcontentresolver..MainActivity intent next");
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    private int removeDuplicates() {

        int rowsDeleted = 0;
        String DSELECTION = "Division = 'ARCHER' AND _id NOT IN (SELECT MIN(_id) FROM " + DatabaseSet.NAME_TABLE + " GROUP BY Name)";
        //String DSELECTION = "Number = " + 1;
        rowsDeleted = getContentResolver().delete(ScoreProvider.NAME_URI, DSELECTION, null);

        return rowsDeleted;
    }
/*
    @SuppressLint("NewApi")
    public void applyBkg(int bkg_selected) {
        //int appBkg =0;
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "applyBkg " + bkg_selected);
        }

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ScrollView1.setBackgroundDrawable(getResources().getDrawable(bkg_selected));
        } else {
            ScrollView1.setBackground(getResources().getDrawable(bkg_selected));
        }
    }
*/
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

        String SELECTION = "_id > -1";
        return new CursorLoader(this, ScoreProvider.NAME_URI, FROM, SELECTION, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {

        NameAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {

        NameAdapter.swapCursor(null);

    }
}
