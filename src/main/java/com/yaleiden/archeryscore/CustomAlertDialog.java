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
import android.widget.Button;
import android.widget.TextView;
/**
 * Custom DialogFragment class
 */
public class CustomAlertDialog  extends DialogFragment  implements
        View.OnClickListener {

    public Activity c;
    public Dialog d;

    public interface CallbacksListener {
        void onPositiveButtonClicked(String selection);

        void onNegativeButtonClicked(String selection);

        void onNeutralButtonClicked();
    }

    private CallbacksListener callbacksListener;

    public void setCallbacksListener(CallbacksListener callbacksListener) {
        this.callbacksListener = callbacksListener;
    }

    public CustomAlertDialog() {
        //empty constructor
    }

    private String titleString;
    private String messageString;
    private String positiveString;
    private String negativeString;
    private String neutralString;
    private String selectionString;

    @Override
    public void setArguments(Bundle bundle) {
        titleString = bundle.getString("titleString");
        messageString = bundle.getString("messageString");
        positiveString = bundle.getString("positiveString");
        negativeString = bundle.getString("negativeString");
        neutralString = bundle.getString("neutralString");
        selectionString = bundle.getString("selectionString");
    }

    public static CustomAlertDialog newInstance(AlertDialogStrings alertDialogStrings) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog();
        Bundle b = new Bundle();
        b.putString("titleString", alertDialogStrings.titleString);
        b.putString("messageString", alertDialogStrings.messageString);
        b.putString("negativeString", alertDialogStrings.negativeString);
        b.putString("positiveString", alertDialogStrings.positiveString);
        b.putString("neutralString", alertDialogStrings.neutralString);
        b.putString("selectionString", alertDialogStrings.selectionString);
        customAlertDialog.setArguments(b);

        return customAlertDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = getActivity().getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);

        TextView titleTV = (TextView) v.findViewById(R.id.txt_title_cust_dia);
        TextView messageTV = (TextView) v.findViewById(R.id.txt_msg_cust_dia);
        Button positiveButton = (Button) v.findViewById(R.id.btn_pos_cust_dialg);
        Button negativeButton = (Button) v.findViewById(R.id.btn_neg_cust_dialg);
        Button neutralButton = (Button) v.findViewById(R.id.btn_neut_cust_dialg);
        titleTV.setText(titleString);
        messageTV.setText(messageString);
        positiveButton.setText(positiveString);
        negativeButton.setText(negativeString);
        neutralButton.setText(neutralString);
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);
        neutralButton.setOnClickListener(this);

        Dialog dialog = new Dialog(getContext(),R.style.Dialog_No_Border);
        dialog.setContentView(v);
        return dialog;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pos_cust_dialg:
                callbacksListener.onPositiveButtonClicked(selectionString);
                dismiss();
                break;
            case R.id.btn_neg_cust_dialg:
                callbacksListener.onNegativeButtonClicked(selectionString);
                dismiss();
                break;
            case R.id.btn_neut_cust_dialg:
                callbacksListener.onNeutralButtonClicked();
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
            callbacksListener = (CallbacksListener) activity;
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
        public final String negativeString;
        public final String neutralString;
        public final String selectionString;

        public AlertDialogStrings(String title, String message, String positiveString, String negativeString, String neutralString, String selectionString) {
            this.messageString = message;
            this.titleString = title;
            this.positiveString = positiveString;
            this.negativeString = negativeString;
            this.neutralString = neutralString;
            this.selectionString = selectionString;
        }
    }
}

