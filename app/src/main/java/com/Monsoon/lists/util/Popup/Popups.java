package com.Monsoon.lists.util.Popup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.R;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.util.Tags;

import java.util.ArrayList;

public class Popups {
    private Context context;
    private LayoutInflater inflater;

    public interface PopupCallback{
        public void onNumberSelected(int num);
        public void onListTypeSelected(ListType listType);
        public void onTagClicked(int pos);
        public void onTagsSelected(ArrayList<String> newTags);
    }

    public Popups(Context context, LayoutInflater inflater){
        this.context = context;
        this.inflater = inflater;
    }

    public void showListSelectorPickerDialog(PopupCallback callback) {
        // Inflate the custom layout
        View dialogView = inflater.inflate(R.layout.list_selector, null);

        // Initialize the NumberPicker from the custom layout
        RecyclerView listSelector = dialogView.findViewById(R.id.list_selector_list);
        listSelector.setLayoutManager(new GridLayoutManager(context, 2));


        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select a list Type");
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Set negative (Cancel) button action
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        listSelector.setAdapter(new listPIckerAdapter(new PopupCallback() {
            @Override
            public void onNumberSelected(int num) {
                // not used
            }

            @Override
            public void onListTypeSelected(ListType listType) {
                callback.onListTypeSelected(listType);
                dialog.dismiss();
            }

            @Override
            public void onTagClicked(int pos) {
                // not used
            }

            @Override
            public void onTagsSelected(ArrayList<String> newTags) {
                // not used
            }

        }));

        // Show the dialog
        dialog.show();
    }

    public void showTagsPickerDialog(Tags tags, boolean single, PopupCallback callback) {
        tags.clearSelections();
        // Inflate the custom layout
        View dialogView = inflater.inflate(R.layout.tag_selector, null);

        // Initialize the NumberPicker from the custom layout
        EditText addTagEditText = dialogView.findViewById(R.id.add_tag_edit_text);
        RecyclerView listSelector = dialogView.findViewById(R.id.list_selector_list);
        listSelector.setLayoutManager(new GridLayoutManager(context, 3));


        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Selected tags to add");
        builder.setView(dialogView);



        tagSelectorAdapter adapter = new tagSelectorAdapter(tags, new PopupCallback() {
            @Override
            public void onNumberSelected(int num) {
                // not used
            }

            @Override
            public void onListTypeSelected(ListType listType) {
                // not used
            }

            @Override
            public void onTagClicked(int pos) {
                if (single) tags.clearSelections();
                tags.getSelectedTags().set(pos, !tags.getSelectedTags().get(pos));
            }

            @Override
            public void onTagsSelected(ArrayList<String> newTags) {
                // not used
            }
        });

        // Set positive (OK) button action
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<String> newTags = new ArrayList<>();
               for (int i = 0; i < tags.getList().size(); i++){
                   if (tags.getSelectedTags().get(i)) newTags.add(tags.getList().get(i));
               }
               callback.onTagsSelected(newTags);
            }
        });

        builder.setNeutralButton("Add Tag", null);

        // Set negative (Cancel) button action
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        listSelector.setAdapter(adapter);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                neutralButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!addTagEditText.getText().toString().isEmpty()){
                            tags.addTag(addTagEditText.getText().toString());
                            addTagEditText.setText("");
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        // Show the dialog
        dialog.show();
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
