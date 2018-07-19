package com.yaleiden.archeryscore;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Yale on 2/26/2016.
 */
public class FragmentStats extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "FragmentStats";
    private static final int ColorLoaderID = 1;
    //ListView mylist; // activities listView
    private CursorAdapterFragResult colorAdapter; // adapter
    private static final String[] FROM = new String[] {"Name",
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
            //"Average",
            //"Percentage",
            //"Possible",
            "_id" };

    private static final int[] TO = new int[] { R.id.textViewResult1, R.id.textViewResult1,
            R.id.textViewResult1 }; //This is overriden in the cursoradapter
    private static final String SELECTION = "_id > -1";
    //private LinearLayout fragmentLayoutBlank;
    private OnScoreStatSelectedListener tCallback;

    private ListView mylist;


    // Container Activity must implement this interface
    public interface OnScoreStatSelectedListener {
        void OnScoreStatSelected(int position, long id);

    }

    @Override
    public void onAttach(Context context) {
        // TODO Auto-generated method stub
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            tCallback = (OnScoreStatSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStatSelectedListener");
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

        mylist = (ListView) view.findViewById(R.id.listviewFragStats);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        if(BuildConfig.DEBUG) {
            Log.d(TAG, " onActivityCreated");
        }


        //ScoreObserver scoreObserver = new ScoreObserver(new Handler(), getActivity());

        colorAdapter = new CursorAdapterFragResult(getActivity(), R.layout.list_item_frag_result, null, FROM, TO);

        mylist.setAdapter(colorAdapter);
        mylist.setOnItemClickListener(myItemClickListener);
        if(BuildConfig.DEBUG) {
            android.util.Log.d(TAG, " setAdapter set");
        }
        getLoaderManager().restartLoader(ColorLoaderID,
                savedInstanceState, this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private final AdapterView.OnItemClickListener myItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {


            tCallback.OnScoreStatSelected(position, id);
            /*
            Intent intent = new Intent(ViewArchiveStats.this, ViewArchiveCards.class);
            intent.putExtra("position", position);
            intent.putExtra("id", id);
            startActivity(intent);
            */
        }
    };

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(getActivity(), ScoreProvider.SCORE_URI, FROM,
                SELECTION, null, "Name");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        colorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        colorAdapter.swapCursor(null);

    }

}
