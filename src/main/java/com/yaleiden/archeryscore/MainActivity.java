package com.yaleiden.archeryscore;


import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;


public class MainActivity extends AppCompatActivity implements
        FragmentActive.OnArcherSelectedListener,
        FragmentEnterScore.OnScoreSavedListener, CustomAlertDialogTwo.CallbacksListener, CustomAlertDialogOne.CallbacksListenerOne, FragmentStats.OnScoreStatSelectedListener {
    static final String APPLICATION_NAME = "Tournament 3D";
    static final String TAG = "MainActivity";
    public static final String DATE_FORMAT_EXP = "yyyy-MM-dd";
    private FragmentActive activeFragment;  //first main screen "activeFragment"

    static final String ROW_ID = "_id"; // intent extra key

    private boolean mIsDualPane;
    private boolean mSingleFragment;
    private boolean mLeftFragment;
    private boolean mRightFragment;
    static final Object[] sDataLock = new Object[0];
    private LinearLayout linearLayoutMain;

    // BEFORE I CHANGED DUAL PANE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        //Log.d(TAG, " oncreate");

        setContentView(R.layout.activity_main);

        if(BuildConfig.DEBUG){
            Log.i(TAG, VariantConstants.type.toString() + " VERSION");
        }
        //Log.d(TAG, "Oncreate mIsDualPane = " + mIsDualPane);
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        setupFragments(); //moved all of the setup to a method outside ONcreate
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.active_archers));
        linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayoutMain);
        //BackupManager mBackupManager = new BackupManager(this);
        // [START shared_tracker]
        // Obtain the shared Tracker instance.

        // [END shared_tracker]
        BackgroundSetter mSetter = new BackgroundSetter(this, linearLayoutMain);
        mSetter.applyBkg();

        firstUse();
        //AppRater mAppRater = new AppRater();
        AppRater.app_launched(this);

        //Log.d(TAG, "after mBackupManager.requestRestore");
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
            activeFragment = new FragmentActive();
            // Add the fragment to the 'fragment_container' FrameLayout
            if (mSingleFragment) {
                setupTransaction.replace(R.id.fragment_container, activeFragment, "activeFragment");
            }
            if (!mSingleFragment) {
                setupTransaction.add(R.id.fragment_container, activeFragment, "activeFragment");
            }
        }

        if (getResources().getBoolean(R.bool.dual_pane)) {
            if (BuildConfig.DEBUG) {
                //Log.d(TAG, " setupFragments() mIsDualPane");
            }


            activeFragment = new FragmentActive();
            FragmentStats statFragment = new FragmentStats();
            statFragment.setRetainInstance(false);
            if (mLeftFragment) {
                setupTransaction.replace(R.id.fragment_left, activeFragment, "activeFragment");
            }
            if (!mLeftFragment) {
                setupTransaction.add(R.id.fragment_left, activeFragment, "activeFragment");
            }
            if (mRightFragment) {
                setupTransaction.replace(R.id.fragment_right, statFragment, "statFragment");
            }
            if (!mRightFragment) {
                setupTransaction.add(R.id.fragment_right, statFragment, "statFragment");
            }


        }

        setupFragmentManager.popBackStack();
        setupTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_sp, menu);
        // return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_main_page) {
            startActivity(new Intent(this, MainActivity.class));
        }
        else if (itemId == R.id.menu_cloud) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " menu MainCloud clicked");
            }
            startActivity(new Intent(this, MainCloud.class));
        }
        else if (itemId == R.id.menu_clear_shoot) {
            clearShoot();
        } else if (itemId == R.id.menu_bkg) {
            changeBkg();
        } else if (itemId == R.id.menu_news) {
            startActivity(new Intent(this, NewsWebActivity.class));
        } else if (itemId == R.id.menu_help) {
            startActivity(new Intent(this, Help.class));
        } else if (itemId == R.id.menu_archive) {
            startActivity(new Intent(this, ViewArchive.class));
        } else if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeBkg() {
        //Log.d(TAG, "changeTheme");

        BackgroundSetter mSetter = new BackgroundSetter(this, linearLayoutMain);
        mSetter.incrementBkg();
        mSetter.applyBkg();
    }

    private void firstUse() {

        SharedPreferences app_preferences = getSharedPreferences("USER_PREFS",
                0);

        int first_use = app_preferences.getInt("FIRST_USE", 0);

        if (first_use == 0) {

            CustomDialogClass cdd = new CustomDialogClass(MainActivity.this);
            cdd.show();

            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putInt("FIRST_USE", 1);
            editor.commit();
        }

    }

    public void setupShoot(View v) {
        startActivity(new Intent(this, SetupShoot.class));
    }

    public void webViewSponsor(View v) {
        startActivity(new Intent(this, SponsorWebActivity.class));
    }

    public void addArcher(View v) {
        startActivity(new Intent(this, SetupArchers.class));
    }

    public void viewScores(View v) {
        startActivity(new Intent(this, ViewScoreCards.class));
    }

    private void clearShoot() {

        showCustomAlertDialogTwo();

    }

    public void startActiveFragment(View v) {

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
    public void OnArcherSelected(Long r_id, String s_Name, String last_Target) {
        //Log.d(TAG, "OnArcherSelected");
        testFragments();
        ArcherSelectedData selectedData = new ArcherSelectedData();
        selectedData.setData(r_id, s_Name, last_Target);

        FragmentManager selectedFragmentManager = getSupportFragmentManager();
        //selectedFragmentManager.popBackStack();
        FragmentTransaction selectedTtransaction = selectedFragmentManager.beginTransaction();
        FragmentEnterScore scoreFragment = new FragmentEnterScore();
        scoreFragment.setData(selectedData);

        if (mIsDualPane) {
            //Log.d(TAG, "mIsDualPane true");
            activeFragment = new FragmentActive();

            if (mLeftFragment) {
                selectedTtransaction.replace(R.id.fragment_left, activeFragment, "activeFragment");
            }
            if (!mLeftFragment) {
                selectedTtransaction.add(R.id.fragment_left, activeFragment, "activeFragment");
            }
            if (mRightFragment) {
                selectedTtransaction.replace(R.id.fragment_right, scoreFragment, "scoreFragment").addToBackStack(null);
            }
            if (!mRightFragment) {
                selectedTtransaction.add(R.id.fragment_right, scoreFragment, "scoreFragment");
            }

        }

        if (!mIsDualPane) {
            //Log.d(TAG, "mIsDualPane false");
            if (mSingleFragment) {
                //Log.d(TAG, "mSingleFragment = true");
                selectedTtransaction.replace(R.id.fragment_container, scoreFragment, "scoreFragment").addToBackStack(null);
            }
            if (!mSingleFragment) {
                //Log.d(TAG, "mSingleFragment = false");
                selectedTtransaction.add(R.id.fragment_container, scoreFragment, "scoreFragment");
            }

        }

        // Commit the transaction
        selectedTtransaction.commit();
    }

    private void showArcher(String s_Name, int s_score) {
        if (s_Name.equals("FAST MODE IS OFF")) {
            Toast toast = Toast.makeText(this, s_Name + " " + s_score, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 160);
            toast.show();
        }
    }


    @Override
    public void OnScoreSaved(String name, int s_score) {

        showArcher(name, s_score);
        //Log.d(TAG, "OnScoreSaved");
        testFragments();
        FragmentManager savedFragmentManager = getSupportFragmentManager();
        savedFragmentManager.popBackStack();  //
        FragmentTransaction savedTransaction = savedFragmentManager.beginTransaction();
        activeFragment = new FragmentActive();

        if (mIsDualPane) {
            //Log.d(TAG, "mIsDualPane true");
            FragmentStats statFragment = new FragmentStats();
            if (mLeftFragment) {
                savedTransaction.replace(R.id.fragment_left, activeFragment, "activeFragment");
            }
            if (!mLeftFragment) {
                savedTransaction.add(R.id.fragment_left, activeFragment, "activeFragment");
            }
            if (mRightFragment) {
                savedTransaction.replace(R.id.fragment_right, statFragment, "statFragment");
            }
            if (!mRightFragment) {
                savedTransaction.add(R.id.fragment_right, statFragment, "statFragment");
            }

        }

        if (!mIsDualPane) {
            //Log.d(TAG, "mIsDualPane false");
            if (mSingleFragment) {
                savedTransaction.replace(R.id.fragment_container, activeFragment, "activeFragment");
            }
            if (!mSingleFragment) {
                savedTransaction.add(R.id.fragment_container, activeFragment, "activeFragment");
            }

        }
        // Commit the transaction
        savedTransaction.commit();
    }

    @Override
    public void onStart() {
        //Log.d(TAG, "onStart()");
        super.onStart();

    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {

        if (!getResources().getBoolean(R.bool.dual_pane)) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentStats yourFragment = (FragmentStats) fm.findFragmentById(R.id.fragment_right);

            if (yourFragment != null) {
                FragmentStats rem = new FragmentStats();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(rem);

                ft.commit();
            }
        }

        super.onResume();

        mIsDualPane = getResources().getBoolean(R.bool.dual_pane);

    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onStop() {

        super.onStop();

        if (getResources().getBoolean(R.bool.dual_pane)) {

            FirebaseCrash.log("Dual Pane");
        }
            System.gc();

        }

        @Override
        protected void onDestroy() {

            super.onDestroy();

        }

        @Override
        public void onPositiveButtonClicked (String delete_selection){
            //CustomAlertDialogTwo is used twice, thus the switch

            if (delete_selection.equals("CLEAR_ALL")) {
                getContentResolver().delete(
                        ScoreProvider.SCORE_URI, "_id > -1",
                        null);
            } else {
                this.getContentResolver().delete(
                        ScoreProvider.SCORE_URI,
                        delete_selection, null);
            }

        }

        @Override
        public void onNegativeButtonClicked () {
// Don't delete
        }

        @Override
        public void onPositiveButtonClickedOne (String deleteString){

        }

        @Override
        public void OnScoreStatSelected ( int position, long id){

        }
    }
