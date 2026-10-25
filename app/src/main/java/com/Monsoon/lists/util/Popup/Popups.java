package com.Monsoon.lists.util.Popup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.Adapters.ListOfListsAdapter;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.ListOfListsWrapper;
import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.Settings;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Tags;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Popups {
    private Context context;
    private LayoutInflater inflater;

    public interface PopupCallback{
        default public void onNumberSelected(int num){
            // not used
        }
        default public void onListTypeSelected(ListType listType){
            // not used
        }
        default public void onTagClicked(int pos){
            // not used
        }
        default public void onStringInput(String s){
            // not used
        }
        default public void onTagsSelected(ArrayList<String> newTags){
            // not used
        }
        default public void onDateSelected(String date){
            // not used
        }

        default public void onListSelected(ListOfListsWrapper list){
            // not used
        }
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

        // Set negative (Cancel) button action
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        listSelector.setAdapter(new listPIckerAdapter(new PopupCallback() {
            @Override
            public void onListTypeSelected(ListType listType) {
                callback.onListTypeSelected(listType);
                dialog.dismiss();
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
            public void onTagClicked(int pos) {
                if (single) tags.clearSelections();
                tags.getSelectedTags().set(pos, !tags.getSelectedTags().get(pos));
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

    public void showDatePickerDialog(PopupCallback callback) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                        String formattedDate = sdf.format(calendar.getTime());

                        if (callback != null) {
                            callback.onDateSelected(formattedDate);
                        }
                    }
                },
                year,
                month,
                day
        );

        // Show the dialog
        datePickerDialog.show();
    }

    public void showTextInputDialog(String title, String hint, PopupCallback callback) {
        // Inflate the custom layout
        View dialogView = inflater.inflate(R.layout.text_input_dialog, null);

        // Initialize the NumberPicker from the custom layout
        EditText input = dialogView.findViewById(R.id.input_box);
        input.setHint(hint);

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(dialogView);

        // Set positive (OK) button action
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onStringInput(input.getText().toString());
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

    public void showMoveListSelector(ListOfLists lists, baseList callingList, BaseListItem item, PopupCallback callback) {
        ListOfLists selectableLists = lists.getCompatibleLists(callingList, item);
        if (!selectableLists.getList().isEmpty()) {

            // Inflate the custom layout
            View dialogView = inflater.inflate(R.layout.list_of_lists_fragment, null);

            // Build the AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Select list");
            builder.setView(dialogView);


            // Set negative (Cancel) button action
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();

            ListOfListsAdapter adapter = new ListOfListsAdapter(selectableLists, new onListItemClickedListener<ListOfListsWrapper>() {
                @Override
                public void onItemClick(ListOfListsWrapper item) {
                    callback.onListSelected(item);
                    dialog.dismiss();
                }

                @Override
                public void onItemLongClick(ListOfListsWrapper item) {

                }

                @Override
                public void onItemSelected(ListOfListsWrapper item) {

                }
            });

            RecyclerView listsView = dialogView.findViewById(R.id.ListsView);
            listsView.setLayoutManager(new GridLayoutManager(context, Constants.NUM_COLOMNS));
            listsView.setAdapter(adapter);

            // Show the dialog
            dialog.show();
        } else {
            Toast.makeText(context, "No compatible Lists", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO
    public void showInfoDialog(PopupCallback callback) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                        String formattedDate = sdf.format(calendar.getTime());

                        if (callback != null) {
                            callback.onDateSelected(formattedDate);
                        }
                    }
                },
                year,
                month,
                day
        );

        // Show the dialog
        datePickerDialog.show();
    }

}
