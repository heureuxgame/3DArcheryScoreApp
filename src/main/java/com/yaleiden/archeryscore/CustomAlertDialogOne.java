package com.yaleiden.archeryscore;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Yale on 2/22/2016.
 */
public class CustomAlertDialogOne extends DialogFragment implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;

    public interface CallbacksListenerOne {
        void onPositiveButtonClickedOne(String deleteString);
    }

    private CallbacksListenerOne callbacksListener;

    public void setCallbacksListener(CallbacksListenerOne callbacksListener) {
        this.callbacksListener = callbacksListener;
    }

    public CustomAlertDialogOne() {
        //empty constructor
    }

    private String titleString;
    private String messageString;
    private String positiveString;
    private String deleteString;

    @Override
    public void setArguments(Bundle bundle) {
        titleString = bundle.getString("titleString");
        messageString = bundle.getString("messageString");
        positiveString = bundle.getString("positiveString");
        deleteString = bundle.getString("deleteString");
    }

    public static CustomAlertDialogOne newInstance(AlertDialogStrings alertDialogStrings) {
        CustomAlertDialogOne customAlertDialogTwo = new CustomAlertDialogOne();
        Bundle b = new Bundle();
        b.putString("titleString", alertDialogStrings.titleString);
        b.putString("messageString", alertDialogStrings.messageString);
        b.putString("positiveString", alertDialogStrings.positiveString);
        b.putString("deleteString", alertDialogStrings.deleteString);
        customAlertDialogTwo.setArguments(b);

        return customAlertDialogTwo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = getActivity().getLayoutInflater().inflate(R.layout.custom_alert_dialog_one, null);

        TextView titleTV = (TextView) v.findViewById(R.id.txt_title_cust_dia);
        TextView messageTV = (TextView) v.findViewById(R.id.txt_msg_cust_dia);
        Button positiveButton = (Button) v.findViewById(R.id.btn_pos_cust_dialg);
        titleTV.setText(titleString);
        messageTV.setText(messageString);
        positiveButton.setText(positiveString);
        positiveButton.setOnClickListener(this);

        Dialog dialog = new Dialog(getContext(),R.style.Dialog_No_Border);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        return dialog;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pos_cust_dialg:
                callbacksListener.onPositiveButtonClickedOne(deleteString);
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
            callbacksListener = (CallbacksListenerOne) activity;
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
        public final String messageString;
        public final String positiveString;
        private final String deleteString;

        public AlertDialogStrings(String title, String message, String positiveString, String deleteString) {
            this.messageString = message;
            this.titleString = title;
            this.positiveString = positiveString;
            this.deleteString = deleteString;
        }
    }
}
