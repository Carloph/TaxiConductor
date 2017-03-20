package com.taxiconductor.View.ViewHome;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by carlos on 08/03/17.
 */

public class DialogHandler {

    boolean returnValue = false;

    // Dialog. --------------------------------------------------------------

    public boolean Confirm(Activity act, String Title, String ConfirmText,
                           String CancelBtn, String OkBtn) {

        AlertDialog dialog = new AlertDialog.Builder(act).create();
        dialog.setTitle(Title);
        dialog.setMessage(ConfirmText);
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, OkBtn,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                        returnValue =true;
                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, CancelBtn,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                        returnValue = false;
                    }
                });
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.show();
        return returnValue;
    }
}