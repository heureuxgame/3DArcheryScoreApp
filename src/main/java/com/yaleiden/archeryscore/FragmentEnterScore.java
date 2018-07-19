package com.yaleiden.archeryscore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Activity for recording scores
 * <p/>
 * This allows recording score for selected Archer and Target.
 *
 * @author Yale
 */

public class FragmentEnterScore extends Fragment implements OnClickListener {
    private ArcherSelectedData archerSelectedData;

    private int score;
    private int USER_SCORING;
    // private Button submit_button;
    private boolean BULLSEYE;
    private boolean MISS;
    private String BullScore;
    //private String[] ScoreArray; // will be set to user-scoring
    private ScoreArrays scoreArrays;

    private static final String[] TargetArray = {"1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
            "17", "18", "19", "20", "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
            "37", "38", "39", "40", "41", "42", "43", "44", "45",
            "46", "47", "48", "49", "50"};
    private String UPDATE_SELECTION;

    private static final String TAG = "Enter Score ";
    private long row_id;
    private String sName;
    private Spinner spinnerTarget;
    private TextView spinnerScore;
    private TextView et1;
    private TextView et;
    private int currentTarget = 0;

    private int sound_enabled;
    private int fast_mode;
    private MediaPlayer bmp;
    private MediaPlayer mmp;
    boolean mIsDualPane;
    private OnScoreSavedListener sCallback;
    //Context ctx;

    // Container Activity must implement this interface
    public interface OnScoreSavedListener {
        void OnScoreSaved(String name, int s_score);
    }

    @Override
    public void onAttach(Context context) {
        // TODO Auto-generated method stub
        super.onAttach(context);
        //this.ctx = context;
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onAttach");
        }
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            sCallback = (OnScoreSavedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnScoreSavedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate");
        }

    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreateView");
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enter_score,
                container, false);
        ScrollView scrollView1 = (ScrollView) view.findViewById(R.id.ScrollView1);
        Button btn = (Button) view.findViewById(R.id.buttonSaveScore);
        btn.setOnClickListener(this);
        spinnerScore = (TextView) view.findViewById(R.id.spinnerScore);
        spinnerTarget = (Spinner) view.findViewById(R.id.spinnerTarget);
        et1 = (TextView) view.findViewById(R.id.textViewlastTarget);
        et = (TextView) view.findViewById(R.id.textViewArcherName);

        spinnerScore.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showScoreDialog();

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onActivityCreated");
        }
        //ScrollView1 = (ScrollView) getActivity().findViewById(R.id.ScrollView1);
        //Bundle can be null in Landscape mode
        if (null == archerSelectedData) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "archerSelecredData null");
            }


            return;
        }

        if (null != archerSelectedData) {

            row_id = archerSelectedData.getRowid();
            sName = archerSelectedData.getName();
            String lastTarget = archerSelectedData.getLastTarget();
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "archerSelecredData " + row_id + " " +
                        "  " + sName +
                        "   " + lastTarget);
            }



            bmp = MediaPlayer.create(getActivity(), R.raw.arrow1);
            mmp = MediaPlayer.create(getActivity(), R.raw.kid_laugh_koenig);

            // Get shared prefs from Shoot Setup
            SharedPreferences app_preferences = getActivity().getSharedPreferences("USER_PREFS",
                    0);
            String USER_TARGETS = app_preferences.getString("USER_TARGETS", "20");
            USER_SCORING = app_preferences.getInt("USER_SCORING", 0);
            sound_enabled = app_preferences.getInt("SOUND", 1);
            fast_mode = app_preferences.getInt("FAST", 1);

            //et1 = (TextView) getView().findViewById(R.id.textViewlastTarget);
            //et = (TextView) getView().findViewById(R.id.textViewArcherName);

            scoreArrays = new ScoreArrays();
            //spinnerTarget = (Spinner) getView().findViewById(R.id.spinnerTarget);
            //spinnerScore = (Spinner) getView().findViewById(R.id.spinnerScore);
            ArrayAdapter<String> targetadapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_custom_target, TargetArray);
            spinnerTarget.setAdapter(targetadapter);

            // spinnerScore.setAdapter(adapter);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, " lastTarget = " + lastTarget);
            }

            char d = lastTarget.charAt(0);
            if (d == 'T') {
                lastTarget = lastTarget.substring(1);
            }

            if (lastTarget.equals("0")) {

                et1.setText(getString(R.string.this_is_your_first_target));

                spinnerTarget.setSelection(0);

            }
            // The shooter has completed 50 targets do this...
            if (lastTarget.equals("50")) {

                et1.setText(getActivity().getString(
                        R.string.you_have_completed_50_targets));


                showCustomAlertDialogOne(getString(R.string.round_finished), sName
                        + " "
                        + getActivity().getString(
                        R.string.has_completed_50));



                spinnerTarget.setSelection(0);

            }
            // The shooter has completed the number of targets they set...
            else if (lastTarget.equals(USER_TARGETS)) {

                et1.setText("You have completed the round!");

                showCustomAlertDialogOne(getString(R.string.round_finished), sName + " "
                        + getString(R.string.has_completed_round));

                currentTarget = Integer.valueOf(lastTarget);
                spinnerTarget.setSelection(currentTarget);

            } else {

                et1.setText(getString(R.string.last_target_was) + " " + lastTarget);

                //**** The if statements are to handle the old T1, T2 target naming. Now it is 1, 2..

                char c = lastTarget.charAt(0);
                if (c == 'T') {
                    currentTarget = Integer.valueOf(lastTarget.substring(1));
                }
                if (c != 'T') {
                    currentTarget = Integer.valueOf(lastTarget);
                }
                spinnerTarget.setSelection(currentTarget);

            }

            et.setText(sName); // Get name to show in spinner prompt
            BullScore = scoreArrays.getBullScore(USER_SCORING);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_custom_score, scoreArrays.getScoring(USER_SCORING));

            //spinnerScore.setAdapter(adapter);

            if (fast_mode == 1) {
                //spinnerScore.setSelection(Adapter.NO_SELECTION, false); //FAST MODE
            } else {
                //spinnerScore.setSelection(0);  //SURE MODE
            }


            //Prompt is the title of the popup
            //spinnerScore.setPrompt(sName + "'s " + getString(R.string.score));
            // enable logging
        }
/// FAST MODE
        if (fast_mode == 1) {
/*
            spinnerScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    scoreClick();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            */
        }
        ///END FAST MODE
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        System.gc();
        Runtime.getRuntime().gc();
    }



    @Override
    public void onClick(View v) {
        //Called when save score button is clicked in this fragment
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onClick");
        }
        switch (v.getId()) {
            case R.id.buttonSaveScore:

                scoreClick();

                break;
            case R.id.spinnerScore:

                showScoreDialog();

                break;
        }
    }

    private void scoreClick() {
        final int HIT = 1;
        final int MISSED = 2;
        final int CUSTOM = 3;
        //final int OTHER = 4;
        String Score = spinnerScore.getText().toString();
        int scoreChoice = 4;
        if (Score.equals("Custom")) {
            scoreChoice = CUSTOM;
        }
        if (Score.equals(BullScore)) {
            BULLSEYE = true;
            scoreChoice = 1;
        }
        if (Score.equals("0")) {
            MISS = true;
            scoreChoice = MISSED;
        }

        switch (scoreChoice) {

            case HIT:
                score = Integer.valueOf(Score);
                updateScore(score);
                break;
            case MISSED:
                score = Integer.valueOf(Score);
                updateScore(score);
                break;
            case CUSTOM:
                enterCustomScore();
                break;
            case 4:
                score = Integer.valueOf(Score);
                updateScore(score);
                break;

        }
    }

    private void updateScore(int score) {
        UPDATE_SELECTION = "_id = " + row_id;

        String targetNumber = spinnerTarget.getSelectedItem().toString();
        ContentValues newScore = new ContentValues();
        newScore.put("T" + targetNumber, score);
        newScore.put("Targets", targetNumber);
        getActivity().getContentResolver().update(ScoreProvider.SCORE_URI, newScore,
                UPDATE_SELECTION, null);

        SumColumns(score);
    }

    private void SumColumns(int score) {
        // update archer total score
        int sum = 0;
        Cursor cursor = getActivity().getContentResolver().query(ScoreProvider.SCORE_URI,
                null, UPDATE_SELECTION, null, null);
        assert cursor != null;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int columns = cursor.getColumnCount();
            for (int col = 11; col < (columns); col++) {
                sum += cursor.getInt(col); // or cursor.getInt(col)
                // Log.d(TAG, " col value " + cursor.getInt(col));
            }
        }
        cursor.close();
        ContentValues newTotal = new ContentValues();
        newTotal.put("Total", sum);
        getActivity().getContentResolver().update(ScoreProvider.SCORE_URI, newTotal,
                UPDATE_SELECTION, null);
        if (sound_enabled == 1) {
            if (BULLSEYE) {
                bmp.start();
                //sound.playShortResource(R.raw.arrow1);

            }
            if (MISS) {
                mmp.start();
                //sound.playShortResource(R.raw.kid_laugh_mike_koenig);
            }
        }
        getActivity().getContentResolver().notifyChange(
                ScoreProvider.SCORE_URI, null);

        String passName = "FAST MODE IS OFF";
        if (fast_mode != 1) {

        } else {
            passName = sName;
        }
        sCallback.OnScoreSaved(passName, score);

    }


    private void enterCustomScore() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Custom score");
        alert.setMessage("Enter score");

        // Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        alert.setView(input);
        input.setFilters(new InputFilter[]{
                // Maximum 2 characters.
                new InputFilter.LengthFilter(2),
                // Digits only.
                DigitsKeyListener.getInstance(),  // Not strictly needed, IMHO.
        });

// Digits only & use numeric soft-keyboard.
        input.setKeyListener(DigitsKeyListener.getInstance(true, false));
        alert.setPositiveButton("Apply",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String custScore = input.getText().toString();

                        if (custScore.equals(BullScore)) {
                            BULLSEYE = true;

                        }
                        if (custScore.equals("0")) {
                            MISS = true;

                        }
                        //For scoreArrays with penalty scores
                        if (custScore.equals("-5")) {
                            MISS = true;

                        }
                        if (custScore.equals("-1")) {
                            MISS = true;

                        }
                        score = Integer.valueOf(custScore);
                        updateScore(score);
                    }
                });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    public void setData(ArcherSelectedData data) {
        this.archerSelectedData = data;
    }

    public ArcherSelectedData getData() {
        return archerSelectedData;
    }

    private void showCustomAlertDialogOne(String dTitle, String dMessage) {
//Prepare dialog messages
        String positiveString = getString(R.string.ok);
        String delete_selection = "no_delete";

        CustomAlertDialogOne.AlertDialogStrings customDialogStrings = new CustomAlertDialogOne.AlertDialogStrings(dTitle, dMessage, positiveString, delete_selection);
        CustomAlertDialogOne customAlertDialogOne = CustomAlertDialogOne.newInstance(customDialogStrings);

        customAlertDialogOne.show(getActivity().getSupportFragmentManager(), "customAlertDialogOne");

    }

    private void showScoreDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.Dialog_No_Border);

        boolean isDual_pane = getActivity().getResources().getBoolean(R.bool.dual_pane);

        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_score_dialog, null);

        ListView lv = (ListView) view.findViewById(R.id.custom_score_list);
        TextView textViewTitle = (TextView) view.findViewById(R.id.txt_title_cust_dia_score);
        textViewTitle.setText(sName + "'s " + getString(R.string.score));

        // Change MyActivity.this and myListOfItems to your own values
        // CustomListAdapterDialog clad = new CustomListAdapterDialog(MyActivity.this, myListOfItems);

        ArrayAdapter<String> MyScoreadapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_custom_score, scoreArrays.getScoring(USER_SCORING));

        lv.setAdapter(MyScoreadapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                String myScore = ((TextView) arg1).getText().toString();
                spinnerScore.setText(myScore);
                if (fast_mode == 1) {
                    scoreClick();
                    dialog.dismiss();
                }
                if (fast_mode == 0){
                    dialog.dismiss();
                }
            }
        });
        //Dialog dialog = new Dialog(getContext(),R.style.Dialog_No_Border);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        int margin = Math.round(getResources().getDimension(R.dimen.activity_horizontal_margin) * 2);//+ getResources().getDimension(R.dimen.activity_horizontal_margin);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();

        if(isDual_pane){
            int[] widthHeight = getDeviceWidthAndHeight(getActivity());
            int dialogHeight = widthHeight[1] - widthHeight[1] / 4;
            int dialogWidth = widthHeight[0] / 4;// - widthHeight[0] / 2 - margin;

            params.height = dialogHeight;
            params.width = dialogWidth;
            params.gravity = Gravity.TOP;
            params.gravity = Gravity.RIGHT;
            params.x = dialogWidth / 2;
            params.y = margin;
        }
        else {
            int[] widthHeight = getDeviceWidthAndHeight(getActivity());
            //int dialogHeight = widthHeight[1] - widthHeight[1] / 4;
            int dialogWidth = widthHeight[0] / 2;// - widthHeight[0] / 2 - margin;
            params.height = widthHeight[1] - margin*2;
            params.width = dialogWidth;
            params.gravity = Gravity.CENTER;
            //params.x = dialogWidth / 2;
            //params.y = margin;
        }


        dialog.getWindow().setAttributes(params);

        dialog.show();

    }

    @SuppressWarnings("deprecation")
    private static int[] getDeviceWidthAndHeight(Context context) {

        int widthHeght[] = new int[2];
        int measuredWidth;
        int measuredHeight;

        WindowManager w = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            Point size = new Point();
            w.getDefaultDisplay().getSize(size);
            measuredWidth = size.x;
            measuredHeight = size.y;
            widthHeght[0] = measuredWidth;
            widthHeght[1] = measuredHeight;

        } else {
            Display d = w.getDefaultDisplay();
            measuredWidth = d.getWidth();
            measuredHeight = d.getHeight();
            widthHeght[0] = measuredWidth;
            widthHeght[1] = measuredHeight;

        }
        return widthHeght;
    }
}
