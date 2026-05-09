package com.Monsoon.lists.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.MealDay;
import com.Monsoon.lists.ListItems.QueueItem;
import com.Monsoon.lists.ListItems.TodoItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.MealPlan;
import com.Monsoon.lists.Lists.QueueList;
import com.Monsoon.lists.Lists.TodoList;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.FragmentAddItemBinding;
import com.Monsoon.lists.enums.Frequency;
import com.Monsoon.lists.enums.Meal;
import com.Monsoon.lists.enums.MenuState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class AddItemFragment extends Fragment {

    private FragmentAddItemBinding binding;
    View itemView;
    TextView EndDate;

    Button mealDate;
    SwitchCompat repeatableSwitch, endRepeat, positionSwitch;
    Spinner repeatFrequency, mealType;
    EditText contentView, numberOfDays, positionInput;
    private StringBuilder sb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseList list = ((MainActivity)getActivity()).getUserLists().getCurrentList();
        sb = new StringBuilder();
        ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.ADD_ITEM);
        switch (list.getListType()){
            case Constants.TODO:
                binding.itemDetailsBox.setDisplayedChild(1);
                itemView = binding.itemDetailsBox.getCurrentView();
                ViewSwitcher vs = itemView.findViewById(R.id.repeatable_view);
                repeatableSwitch = itemView.findViewById(R.id.repeatable_switch);
                repeatableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) vs.setDisplayedChild(1);
                        else vs.setDisplayedChild(0);
                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, Frequency.getLabels());
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                numberOfDays = itemView.findViewById(R.id.number_of_days);
                repeatFrequency = itemView.findViewById(R.id.repeat_type);
                repeatFrequency.setAdapter(adapter);
                repeatFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        int size = Frequency.getLabels().length;
                        numberOfDays.setEnabled(true);
                        if (i != size - 1) {
                            sb = new StringBuilder();
                            numberOfDays.setText(sb.append(Frequency.getFrequencyByIndex(i).days).toString());
                            numberOfDays.setEnabled(false);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                EndDate = itemView.findViewById(R.id.end_date);
                endRepeat = itemView.findViewById(R.id.end_repeat_switch);
                endRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        EndDate.setEnabled(b);
                    }
                });

                EndDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View Eview) {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                                (view, year1, monthOfYear, dayOfMonth) -> {
                                    EndDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1);
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                });
                break;
            case Constants.QUEUE:
                binding.itemDetailsBox.setDisplayedChild(2);
                itemView = binding.itemDetailsBox.getCurrentView();
                positionSwitch = itemView.findViewById(R.id.position_switch);
                positionInput = itemView.findViewById(R.id.position_input);
                positionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        positionInput.setEnabled(b);
                    }
                });
                break;
            case Constants.MEAL_PLAN:
                binding.itemDetailsBox.setDisplayedChild(3);
                itemView = binding.itemDetailsBox.getCurrentView();
                ArrayAdapter<String> mpAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, Meal.getLabels());
                mpAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                mealType = itemView.findViewById(R.id.meal_type);
                mealDate = itemView.findViewById(R.id.date_input);
                mealType.setAdapter(mpAdapter);
                mealDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View Eview) {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                                (view, year1, monthOfYear, dayOfMonth) -> {
                                    mealDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1);
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                break;
            default:
                binding.itemDetailsBox.setDisplayedChild(0);
                itemView = binding.itemDetailsBox.getCurrentView();

        }

        binding.createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content;
                switch (list.getListType()){
                    case Constants.TODO:
                        contentView = itemView.findViewById(R.id.todo_item_content_input);
                        content = contentView.getText().toString();
                        // check if item content is blank
                        if (content.isBlank()){
                            Toast.makeText(requireContext(), "Item Content cannot be blank!", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            TodoItem newItem = new TodoItem(content);

                            try{
                                if (repeatableSwitch.isChecked()){
                                    if (endRepeat.isChecked()){
                                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                                        Date t = sdf.parse(EndDate.getText().toString());
                                        if (EndDate.getText().toString().isBlank() || t == null){
                                            throw new InputMismatchException();
                                        } else {
                                            newItem.turnOnRepeat(Frequency.getFrequencyByIndex(repeatFrequency.getSelectedItemPosition()),
                                                    Integer.parseInt(numberOfDays.getText().toString()), true,
                                                    t.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                                        }
                                    } else {
                                        newItem.turnOnRepeat(Frequency.getFrequencyByIndex(repeatFrequency.getSelectedItemPosition()),
                                                Integer.parseInt(numberOfDays.getText().toString()));
                                    }
                                } else {
                                    newItem.turnOffRepeat();
                                }
//
                                list.addItem(newItem);
                                ((MainActivity)requireActivity()).OnListChange();
                                NavHostFragment.findNavController(AddItemFragment.this).
                                        navigate(R.id.action_todo_submit);
                            } catch (ParseException e) {
                            Toast.makeText(requireContext(), "End be in mm/dd/yyyy format!", Toast.LENGTH_SHORT).show();
                            } catch (InputMismatchException e){
                                Toast.makeText(requireContext(), "End date must be selected!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        break;
                    case Constants.QUEUE:
                        contentView = itemView.findViewById(R.id.queue_item_content_input);
                        content = contentView.getText().toString();
                        if (content.isBlank()){
                            Toast.makeText(requireContext(), "Item Content cannot be blank!", Toast.LENGTH_SHORT).show();
                        } else {
                            QueueItem newItem = new QueueItem(content);
                            if (positionSwitch.isChecked()){
                                String temp = positionInput.getText().toString();
                                if (temp.isBlank()){
                                    Toast.makeText(requireContext(), "Insert position cannot be blank!", Toast.LENGTH_SHORT).show();
                                } else {
                                    int insertPosition =Integer.parseInt(temp);
                                    if (insertPosition < 1 || insertPosition > list.getList().size()){
                                        Toast.makeText(requireContext(), "Insert position must be between 1 and " + list.getList().size(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        ((QueueList)list).addItem(newItem, insertPosition - 1);
                                        ((QueueList)list).reassignPositions();
                                        ((MainActivity)requireActivity()).OnListChange();
                                        NavHostFragment.findNavController(AddItemFragment.this).
                                                navigate(R.id.action_queue_submit);
                                    }

                                }
                            } else {
                                list.addItem(newItem);
                                ((MainActivity)requireActivity()).OnListChange();
                                NavHostFragment.findNavController(AddItemFragment.this).
                                        navigate(R.id.action_queue_submit);
                            }


                        }
                        break;
                    case Constants.MEAL_PLAN:
                        contentView = itemView.findViewById(R.id.meal_name_input);
                        content = contentView.getText().toString();
                        if (content.isBlank()){
                            Toast.makeText(requireContext(), "Meal name cannot be blank!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (mealDate.getText().toString().equals(getString(R.string.enter_meal_date))){
                                Toast.makeText(requireContext(), "Meal date must be selected!", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                                    Date t = sdf.parse(mealDate.getText().toString());
                                    ((MealPlan)list).addMeal(content, t.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), Meal.getMealByLabel(mealType.getSelectedItem().toString()));
                                    ((MainActivity)requireActivity()).OnListChange();
                                    NavHostFragment.findNavController(AddItemFragment.this).
                                            navigate(R.id.action_meal_plan_submit);
                                } catch (ParseException e) {
                                    Toast.makeText(requireContext(), "End be in mm/dd/yyyy format!", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        break;
                    default:
                        contentView = itemView.findViewById(R.id.basic_item_content_input);
                        content = contentView.getText().toString();
                        if (content.isBlank()){
                            Toast.makeText(requireContext(), "Item Content cannot be blank!", Toast.LENGTH_SHORT).show();
                        } else {
                            BaseListItem newItem = new BaseListItem(content);
                            list.addItem(newItem);
                            ((MainActivity)requireActivity()).OnListChange();
                            NavHostFragment.findNavController(AddItemFragment.this).
                                navigate(R.id.action_basic_submit);
                        }
                }

            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddItemBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}