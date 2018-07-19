package com.yaleiden.archeryscore;

/**
 * Created by Yale on 2/19/2016.
 */
//public class CustomAlertDialog {
//}


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Custom DialogFragment class
 */
public class CustomScoreDialog extends DialogFragment  implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;

    public interface CallbacksListenerScore {
        void onSaveButtonClicked(int score);
    }

    private CallbacksListenerScore callbacksListener;

    public void setCallbacksListener(CallbacksListenerScore callbacksListener) {
        this.callbacksListener = callbacksListener;
    }

    public CustomScoreDialog() {
        //empty constructor
    }

    private String titleString;

    @Override
    public void setArguments(Bundle bundle) {
        titleString = bundle.getString("titleString");
    }

    public static CustomScoreDialog newInstance(AlertDialogStrings alertDialogStrings) {
        CustomScoreDialog customAlertDialog = new CustomScoreDialog();
        Bundle b = new Bundle();
        b.putString("titleString", alertDialogStrings.titleString);
        customAlertDialog.setArguments(b);

        return customAlertDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.custom_score_dialog, null);
        ListView scorelist = (ListView) v.findViewById(R.id.custom_score_list);
        TextView titleTV = (TextView) v.findViewById(R.id.txt_title_cust_dia);
        titleTV.setText(titleString);

        Dialog dialog = new Dialog(getContext(),R.style.Dialog_No_Border);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        return dialog;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pos_cust_dialg:
                callbacksListener.onSaveButtonClicked(3);
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbacksListener = (CallbacksListenerScore) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CallbacksListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacksListener = null;
    }

    /**
     * Class for saving the wanted Strings we want to have on our CustomDialog implementation
     */

    public static class AlertDialogStrings {
        public final String titleString;

        public AlertDialogStrings(String title) {

            this.titleString = title;
        }
    }

}

