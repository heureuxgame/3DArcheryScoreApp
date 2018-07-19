package com.yaleiden.archeryscore;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentActive extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "FragmentActive";
    private int reporting;
    private SimpleCursorAdapter scoreAdapter; // adapter
    private static final String[] FROM = new String[]{"Name", "Total", "Targets",
            "_id"};
    private static final int[] TO = new int[]{R.id.nameTextView, R.id.scoreTextView,
            R.id.lastTargetTextView};
    private static final String SELECTION = "_id > -1";
    private String sName = null;
    static final String ROW_ID = "_id"; // intent extra key
    private TextView mtx;
    private static final int ScoreLoaderID = 1;
    //boolean mIsDualPane;
    private OnArcherSelectedListener mCallback;
    private static Activity mActivity;
    private String delete_selection;
    //Context ctx;


    // Container Activity must implement this interface
    public interface OnArcherSelectedListener {
        void OnArcherSelected(Long r_id, String s_Name, String last_Target);

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
            mCallback = (OnArcherSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnArcherSelectedListener");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater
                .inflate(R.layout.fragment_active, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        getLoaderManager().initLoader(ScoreLoaderID,
                null, this);

        //scoreObserver = new ScoreObserver(new Handler(), mActivity);
        RelativeLayout relativeLayoutMain = (RelativeLayout) getActivity().findViewById(
                R.id.relativeLayoutMain);

        //scoreObserver = new ScoreObserver(new Handler(), mActivity);

        ListView mylist = (ListView) getView().findViewById(android.R.id.list);
        // the
        // android
        // prefix..

        scoreAdapter = new SimpleCursorAdapter(mActivity,
                R.layout.list_item_active_shoot, null, FROM, TO, 0);
        mylist.setOnItemClickListener(myListClickListener); // selects archer
        // to
        // score
        mylist.setOnItemLongClickListener(myListLongClickListener); // selects
        // archer
        // to
        // delete
        //mylist.setCacheColorHint(R.color.transparent);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "before mylist.setAdapter set");
        }
        mylist.setAdapter(scoreAdapter);
        //mylist.setDivider(null);

        getLoaderManager().restartLoader(ScoreLoaderID,
                savedInstanceState, this);
        //firstUse();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "after getsupporloadermanager");
        }
    }

    private final OnItemClickListener myListClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // Get information for selected archer
            mtx = (TextView) arg1.findViewById(R.id.nameTextView);
            TextView mtx1 = (TextView) arg1.findViewById(R.id.lastTargetTextView);

            sName = mtx.getText().toString();
            // if this is the first target set target to 'T0'
            String lastTarget = null;
            if (mtx1.getText().toString().equals("")) {
                lastTarget = "T0";
            } else {
                // if not first target - cut to target number
                String LT = mtx1.getText().toString();
                int slength = LT.length();
                lastTarget = LT.substring(0, slength);
            }
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "arg3, sName, lastTarget " + arg3 + " " + sName + " "
                        + lastTarget);
            }
            mCallback.OnArcherSelected(arg3, sName, lastTarget);
            //showArcher(arg3, sName, lastTarget);
        }
    };

    private final OnItemLongClickListener myListLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View v, int index,
                                       final long arg3) {
            // get archer name to display in delete dialog
            mtx = (TextView) v.findViewById(R.id.nameTextView);
            sName = mtx.getText().toString();
            delete_selection = "_id =" + arg3;
            showCustomAlertDialogTwo();

            return true;

        }

    };

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(mActivity, ScoreProvider.SCORE_URI, FROM,
                SELECTION, null, "Name");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        scoreAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        scoreAdapter.changeCursor(null);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {

        super.onStop();
        System.gc();

    }



    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();


    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        //System.gc();
        //Runtime.getRuntime().gc();
    }

    private void showCustomAlertDialogTwo() {
//Prepare dialog messages
        String title = getString(R.string.confirm_delete);
        String message = getString(R.string.delete) + " " + sName + "?";
        String positiveString = getString(R.string.yes);
        String negativeString = getString(R.string.no);

        CustomAlertDialogTwo.AlertDialogStrings customDialogStrings = new CustomAlertDialogTwo.AlertDialogStrings(title, message, positiveString, negativeString, delete_selection);
        CustomAlertDialogTwo customAlertDialogTwo = CustomAlertDialogTwo.newInstance(customDialogStrings);

        customAlertDialogTwo.show(getActivity().getSupportFragmentManager(), "customAlertDialogTwo");

    }

}