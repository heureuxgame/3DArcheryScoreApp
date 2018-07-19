package com.yaleiden.archeryscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.crash.FirebaseCrash;

/**
 * Created by Yale on 2/29/2016.
 */
public class ViewArchive extends AppCompatActivity implements ViewArchiveStats.OnStatSelectedListener, CustomAlertDialogTwo.CallbacksListener {

    private static final String TAG = "ViewArchive";
    private ViewArchiveStats fragmentStats;
    private ViewArchiveChart fragmentChart;
    private ViewArchiveCards fragmentCards;
    private ImageView imageView1Shoot;
    private ImageView imageView2;
    private ImageView imageView3;
    private static final int WRITE_STORAGE_PERMISSIONS_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        //Log.d(TAG, " oncreate");

        setContentView(R.layout.archive_view);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.archived_scores));
        actionBar.setDisplayHomeAsUpEnabled(true);
        LinearLayout layoutArchiveMain = (LinearLayout) findViewById(R.id.layoutArchiveMain);
        imageView1Shoot = (ImageView) findViewById(R.id.imageView1Shoot);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView1Shoot.setImageResource(R.drawable.ba_list_g);
        BackgroundSetter mSetter = new BackgroundSetter(this, layoutArchiveMain);
        mSetter.applyBkg();
        setupFragments();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.archive_sp, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_main_page) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (itemId == R.id.menu_help) {
            startActivity(new Intent(this, Help.class));
        } else if (itemId == R.id.menu_export) {
            tellFragEmailAllScoreCards();
            //checkPermissionReadStorage();
        } else if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {

        super.onStop();
        System.gc();

    }

    private void setupFragments() {

        //testFragments();
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction setupTransaction = fragmentManager.beginTransaction();

        Fragment singleFragment = fragmentManager
                .findFragmentById(R.id.fragment_container);
        fragmentStats = new ViewArchiveStats();

        if (singleFragment != null && singleFragment.isVisible()) {

            setupTransaction.replace(R.id.fragment_container, fragmentStats, "fragmentStats");

        } else {
            setupTransaction.add(R.id.fragment_container, fragmentStats, "fragmentStats");
        }
        setupTransaction.commit();
    }
/*
    public void checkPermissionReadStorage(){
        Toast.makeText(this, "Checking permissions", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Write to storage permission?", Toast.LENGTH_SHORT).show();
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
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
                Toast.makeText(this, "Write to storage permission granted", Toast.LENGTH_SHORT).show();
                tellFragEmailAllScoreCards();
            } else {
                 //showRationale = false if user clicks Never Ask Again, otherwise true
                    Toast.makeText(this, "Allow storage in device settings.", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    */

    private void tellFragEmailAllScoreCards() {
        //ViewArchiveCards fragmentCards;
        fragmentCards = (ViewArchiveCards) getSupportFragmentManager().findFragmentByTag("fragmentCards");

        if (fragmentCards != null && fragmentCards.isVisible()) {
            fragmentCards.exportArchive();
        }else {


            if (fragmentCards == null || !fragmentCards.isVisible()) {
                fragmentCards = new ViewArchiveCards();
                Bundle bundle = new Bundle();
                bundle.putBoolean("export", true);
                fragmentCards.setArguments(bundle);

                removeFrag();

                FragmentManager setupFragmentManager = getSupportFragmentManager();
                FragmentTransaction setupTransaction = setupFragmentManager.beginTransaction();

                //fragmentCards = new ViewArchiveCards();

                setupTransaction.add(R.id.fragment_container, fragmentCards, "fragmentCards");
                setupTransaction.commit();
                imageView1Shoot.setImageResource(R.drawable.ba_list);
                imageView2.setImageResource(R.drawable.ba_card_g);
                imageView3.setImageResource(R.drawable.ba_chart);
            }

        }
        FirebaseCrash.log("Export Archive");
    }

    //SETUP BUTTON CLICK FRAGMENT CHANGES HERE
    public void viewList(View v) {
        //removeFrag();

        FragmentManager setupFragmentManager = getSupportFragmentManager();
        FragmentTransaction setupTransaction = setupFragmentManager.beginTransaction();

        fragmentStats = new ViewArchiveStats();

        setupTransaction.replace(R.id.fragment_container, fragmentStats, "fragmentStats");
        setupTransaction.commit();
        imageView1Shoot.setImageResource(R.drawable.ba_list_g);
        imageView2.setImageResource(R.drawable.ba_card);
        imageView3.setImageResource(R.drawable.ba_chart);
    }

    public void viewCards(View v) {
        //removeFrag();

        FragmentManager setupFragmentManager = getSupportFragmentManager();
        FragmentTransaction setupTransaction = setupFragmentManager.beginTransaction();

        fragmentCards = new ViewArchiveCards();

        setupTransaction.replace(R.id.fragment_container, fragmentCards, "fragmentCards");
        setupTransaction.commit();
        imageView1Shoot.setImageResource(R.drawable.ba_list);
        imageView2.setImageResource(R.drawable.ba_card_g);
        imageView3.setImageResource(R.drawable.ba_chart);
    }

    public void viewChart(View v) {
        //removeFrag();

        FragmentManager setupFragmentManager = getSupportFragmentManager();
        FragmentTransaction setupTransaction = setupFragmentManager.beginTransaction();

        fragmentChart = new ViewArchiveChart();

        setupTransaction.replace(R.id.fragment_container, fragmentChart, "fragmentChart");
        setupTransaction.commit();
        imageView1Shoot.setImageResource(R.drawable.ba_list);
        imageView2.setImageResource(R.drawable.ba_card);
        imageView3.setImageResource(R.drawable.ba_chart_g);
    }

    private void removeFrag() {

        FragmentManager setupFragmentManager = getSupportFragmentManager();
        FragmentTransaction setupTransaction = setupFragmentManager.beginTransaction();
        if (fragmentStats != null && fragmentStats.isVisible()) {
            setupTransaction.remove(fragmentStats).commit();
        }
        if (fragmentCards != null && fragmentCards.isVisible()) {
            setupTransaction.remove(fragmentCards).commit();
        }
        if (fragmentChart != null && fragmentChart.isVisible()) {
            setupTransaction.remove(fragmentChart).commit();
        }
    }

    @Override
    public void OnStatSelected(int position, long id) {

        removeFrag();
        FragmentManager setupFragmentManager = getSupportFragmentManager();
        FragmentTransaction setupTransaction = setupFragmentManager.beginTransaction();

        fragmentCards = new ViewArchiveCards();

        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putLong("id", id);
        fragmentCards.setArguments(bundle);
        setupTransaction.add(R.id.fragment_container, fragmentCards, "fragmentCards");

        setupTransaction.commit();
        imageView1Shoot.setImageResource(R.drawable.ba_list);
        imageView2.setImageResource(R.drawable.ba_card_g);
        imageView3.setImageResource(R.drawable.ba_chart);

    }

    @Override
    public void onPositiveButtonClicked(String deleteString) {

        this.getContentResolver().delete(
                ScoreProvider.ARCHIVE_URI,
                deleteString, null);

        getContentResolver().notifyChange(
                ScoreProvider.ARCHIVE_URI, null);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPosClicked del " + deleteString);
        }
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}