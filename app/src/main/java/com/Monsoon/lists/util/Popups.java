package com.Monsoon.lists.util;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.MainActivityCallback;

public class Popups {
    private Context context;
    private LayoutInflater inflater;

    public interface PopupCallback{
        public void onNumberSelected(int num);
    }

    public Popups(Context context, LayoutInflater inflater){
        this.context = context;
        this.inflater = inflater;
    }

    public void showNumberPickerDialog(int currentValue, int maxValue, PopupCallback callback) {
        // Inflate the custom layout
        View dialogView = inflater.inflate(R.layout.number_picker, null);

        // Initialize the NumberPicker from the custom layout
        NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker);

        // Set minimum and maximum values
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setValue(currentValue); // Optional default starting value
        numberPicker.setWrapSelectorWheel(true);

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select a Number");
        builder.setView(dialogView);

        // Set positive (OK) button action
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int num = numberPicker.getValue();
                callback.onNumberSelected(num);
            }
        });

        // Set negative (Cancel) button action
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        builder.create().show();
    }

}
