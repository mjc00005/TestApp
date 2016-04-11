package com.example.manuel.testapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Manuel on 18/03/2016.
 */
public class AlertDialogFragment extends android.app.DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context ctx = getActivity();

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
        builder.setTitle("Sorry");
        builder.setMessage("Please, try again");
        builder.setPositiveButton("OK", null);

        android.support.v7.app.AlertDialog dialog = builder.create();
        return dialog;
    }
}
