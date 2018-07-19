package com.yaleiden.archeryscore;

import android.app.backup.BackupManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

public class ViewScoreCards extends AppCompatActivity implements CustomAlertDialog.CallbacksListener, FragmentStats.OnScoreStatSelectedListener {

    private static final String TAG = "ViewScoreCards";

    boolean mIsDualPane;
    //boolean mThirdFragment;
    boolean mIsContainer;
    private boolean mSingleFragment;
    private boolean mLeftFragment;
    private boolean mRightFragment;

    private FragmentScorecards fragmentScorecards;
    private FragmentStats fragmentStats;
    private ImageView imageViewCard;
    private ImageView imageViewStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, " oncreate");
        }
        setContentView(R.layout.activity_view_scorecards);
        LinearLayout linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayoutViewScorecards);
        imageViewCard = (ImageView) findViewById(R.id.imageViewCard);
        imageViewStat = (ImageView) findViewById(R.id.imageViewStat);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.scorecards);
        actionBar.setDisplayHomeAsUpEnabled(true);

        BackgroundSetter mSetter = new BackgroundSetter(this, linearLayoutMain);
        //applyBkg(mSetter.getBkg());
        mSetter.applyBkg();
        setupFragments();

        if (BuildConfig.DEBUG) {
            Log.d(TAG, " after setcontentview");
        }


        /** It is handy to keep a BackupManager cached */
        //mBackupManager = new BackupManager(this);


    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // *** RESET for tweet Image
        SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
                0);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("IMAGE_UPDATE", "");
        editor.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_scores, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_main_page) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (itemId == R.id.menu_archive) {
            startActivity(new Intent(this, ViewArchive.class));
        } else if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
        } else if (itemId == R.id.menu_export) {
            new ExportDatabaseFileTask().execute("");
        } else if (itemId == R.id.menu_email) {
            EmailAllScoreCards();
        } else if (itemId == R.id.menu_help) {
            startActivity(new Intent(this, Help.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void testFragments() {
        //Log.d(TAG, "testFragments()");
        mSingleFragment = false;
        mLeftFragment = false;
        mRightFragment = false;
        //mThirdFragment = false;
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment singleFragment = fragmentManager
                .findFragmentById(R.id.fragment_container);


        if (singleFragment != null && singleFragment.isVisible()) {
            //Log.d(TAG, "singleFragment IN view");
            mSingleFragment = true;

            //Fragment rightFragment = fragmentManager
            //       .findFragmentByTag("statFragment");

            // if (rightFragment != null) {

            //fragmentManager.popBackStack("rightFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("statFragment");
            if (fragment != null) {

                //Log.d(TAG, "rightFragment not null");
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }


        }

        Fragment leftFragment = fragmentManager
                .findFragmentById(R.id.fragment_left);

        if (leftFragment != null && leftFragment.isVisible()) {
            //Log.d(TAG, "leftFragment IN view");
            mLeftFragment = true;
        }

        Fragment rightFragment = fragmentManager
                .findFragmentById(R.id.fragment_right);

        if (rightFragment != null && rightFragment.isVisible()) {
            //Log.d(TAG, "rightFragment IN view");
            mRightFragment = true;
        }

    }

    private void setupFragments() {

        testFragments();
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        FragmentManager setupFragmentManager = getSupportFragmentManager();
        FragmentTransaction setupTransaction = setupFragmentManager.beginTransaction();
        if (!getResources().getBoolean(R.bool.dual_pane)) {  //Check for landscape orientation
            //Log.d(TAG, " oncreate fragment_container != null");

            // Create a new Fragment to be placed in the activity layout
            //fragmentScorecards = new FragmentScorecards();
            fragmentStats = new FragmentStats();
            // Add the fragment to the 'fragment_container' FrameLayout
            if (mSingleFragment) {
                setupTransaction.add(R.id.fragment_container, fragmentStats, "fragmentStats");
            }
            if (!mSingleFragment) {

                setupTransaction.replace(R.id.fragment_container, fragmentStats, "fragmentStats");
            }
        }

        if (getResources().getBoolean(R.bool.dual_pane)) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " setupFragments() mIsDualPane");
            }


            fragmentScorecards = new FragmentScorecards();
            fragmentStats = new FragmentStats();

            fragmentStats.setRetainInstance(false);
            if (mLeftFragment) {
                setupTransaction.replace(R.id.fragment_left, fragmentStats, "fragmentStats");
            }
            if (!mLeftFragment) {
                setupTransaction.add(R.id.fragment_left, fragmentStats, "fragmentStats");
            }
            if (mRightFragment) {
                setupTransaction.replace(R.id.fragment_right, fragmentScorecards, "fragmentScorecards");
            }
            if (!mRightFragment) {
                setupTransaction.add(R.id.fragment_right, fragmentScorecards, "fragmentScorecards");
            }


        }

        setupFragmentManager.popBackStack();
        setupTransaction.commit();
    }

    public void changeStatView(View v) {
        //Log.d(TAG, "changeStatView()");
        FragmentManager switchManager = getSupportFragmentManager();
        FragmentTransaction switchTransaction = switchManager.beginTransaction();
        Fragment singleFragment = switchManager
                .findFragmentByTag("fragmentStats");

        if (singleFragment != null && singleFragment.isVisible()) {
            //Log.d(TAG, "singleFragment IN view");
            mSingleFragment = true;
            fragmentScorecards = new FragmentScorecards();
            switchTransaction.replace(R.id.fragment_container, fragmentScorecards, "fragmentScorecards");
            imageViewStat.setImageResource(R.drawable.ba_list);
            imageViewCard.setImageResource(R.drawable.ba_card_g);
        }
        Fragment singFragment = switchManager
                .findFragmentByTag("fragmentScorecards");
        if (singFragment != null && singFragment.isVisible()) {
            //Log.d(TAG, "singleFragment IN view");
            mSingleFragment = true;
            fragmentStats = new FragmentStats();
            switchTransaction.replace(R.id.fragment_container, fragmentStats, "fragmentStats");
            imageViewStat.setImageResource(R.drawable.ba_list_g);
            imageViewCard.setImageResource(R.drawable.ba_card);
        }
        switchTransaction.commit();
    }


    private void emailFile() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                ViewScoreCards.this.getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder().append("<p><b>").append(ViewScoreCards.this.getString(R.string.email_text)).append(" ").append(ViewScoreCards.this.getString(R.string.app_name)).append(".  </p><p>").append(ViewScoreCards.this.getString(R.string.email_get_app)).append("</b></p></a>").append("<br><br><h2><a href = ").append(ViewScoreCards.this.getString(R.string.app_link)).append(">").append(ViewScoreCards.this.getString(R.string.download)).append(" ").append(ViewScoreCards.this.getString(R.string.app_name)).append("</a></h2>").toString()));
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root + "/" + Constants.EXPORT_FOLDER,
                "ArcheryScores.csv");
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(this,
                    ViewScoreCards.this.getString(R.string.attachment_error),
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Uri uri = Uri.fromFile(file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Send email..."));

        FirebaseCrash.log("Email CSV");
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
            curCSV = getContentResolver().query(ScoreProvider.SCORE_URI, null,
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
            Toast.makeText(ViewScoreCards.this, R.string.csv_saved, Toast.LENGTH_LONG)
                    .show();
            emailFile();
        }

    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();


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
        BackupManager bm = new BackupManager(this);
        bm.dataChanged();

        /** Also cache a reference to the Backup Manager */

        /** Set up our file bookkeeping */
        // mDataFile = new File(getFilesDir(), DatabaseSet.DATABASE_NAME);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "mBackupManager.dataChanged()");
        }

        // mBackupManager.dataChanged();
    }


    private void EmailAllScoreCards() {
        FragmentScorecards fragmentScorecards;
        fragmentScorecards = (FragmentScorecards) getSupportFragmentManager().findFragmentByTag("fragmentScorecards");
        if (fragmentScorecards == null || !fragmentScorecards.isVisible()) {
            //changeStatView(null);

            FragmentManager switchManager = getSupportFragmentManager();
            FragmentTransaction switchTransaction = switchManager.beginTransaction();

            fragmentScorecards = new FragmentScorecards();
            Bundle bundle = new Bundle();
            bundle.putBoolean("emailall", true);
            fragmentScorecards.setArguments(bundle);
            switchTransaction.replace(R.id.fragment_container, fragmentScorecards, "fragmentScorecards");
            imageViewStat.setImageResource(R.drawable.ba_list);
            imageViewCard.setImageResource(R.drawable.ba_card_g);
            switchTransaction.commit();

        }
        if (fragmentScorecards != null && fragmentScorecards.isVisible()) {
            fragmentScorecards.emailToGroup(null);
        }
        FirebaseCrash.log("Email all cards");
    }

    private void emailOneScorecard(String s) {

        FragmentScorecards fragmentScorecards = (FragmentScorecards) getSupportFragmentManager().findFragmentByTag("fragmentScorecards");
        if (fragmentScorecards == null) {
            //Keep going as normal
        }
        assert fragmentScorecards != null;
        fragmentScorecards.emailToGroup(s);
        FirebaseCrash.log("Email one card");
    }

    private void archiveScorecard(String s) {

        FragmentScorecards fragmentScorecards = (FragmentScorecards) getSupportFragmentManager().findFragmentByTag("fragmentScorecards");
        if (fragmentScorecards == null) {
            // addFragmentScorecards();
        }
        assert fragmentScorecards != null;
        fragmentScorecards.archiveScorecard(s);
        FirebaseCrash.log("Archive score");
    }

    private void sendTextScore() {
        FragmentScorecards fragmentScorecards = (FragmentScorecards) getSupportFragmentManager().findFragmentByTag("fragmentScorecards");
        if (fragmentScorecards == null) {
            // addFragmentScorecards();
        }
        assert fragmentScorecards != null;
        fragmentScorecards.sendTextScore();
        FirebaseCrash.log("Send SMS");
    }

    //CustomAlertDialog Interface methods

    @Override
    public void onPositiveButtonClicked(String s) {
        //Log.d(TAG, s+" onPositiveButtonClicked");
        emailOneScorecard(s);
    }

    @Override
    public void onNegativeButtonClicked(String s) {
        archiveScorecard(s);
    }

    @Override
    public void onNeutralButtonClicked() {
        sendTextScore();
    }

    private boolean isDualPane() {
        return getResources().getBoolean(R.bool.dual_pane);
    }

    /**
     * Positions
     * @param position position in listview
     * @param id _id in db
     */
    @Override
    public void OnScoreStatSelected(int position, long id) {

        fragmentScorecards = new FragmentScorecards();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putLong("id", id);
        fragmentScorecards.setArguments(bundle);
        FragmentManager selectManager = getSupportFragmentManager();
        FragmentTransaction selectTransaction = selectManager.beginTransaction();

        if (!isDualPane()) {

            Fragment singleFragment = selectManager
                    .findFragmentById(R.id.fragment_container);

            if (singleFragment != null && singleFragment.isVisible()) {
                //Log.d(TAG, "singleFragment IN view");
                mSingleFragment = true;

                selectTransaction.replace(R.id.fragment_container, fragmentScorecards, "fragmentScorecards");
                imageViewStat.setImageResource(R.drawable.ba_list);
                imageViewCard.setImageResource(R.drawable.ba_card_g);
                selectTransaction.commit();
            }
        }
        if (isDualPane()) {

            FirebaseCrash.log("Dual Pane");

            Fragment singleFragment = selectManager
                    .findFragmentByTag("fragmentScorecards");
            if (singleFragment != null && singleFragment.isVisible()) {
                //Log.d(TAG, "singleFragment IN view");
                mSingleFragment = true;

                selectTransaction.replace(R.id.fragment_right, fragmentScorecards, "fragmentScorecards");
                selectTransaction.commit();
            }
        }


    }
}

