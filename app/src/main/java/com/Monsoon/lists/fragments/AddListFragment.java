package com.Monsoon.lists.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.Monsoon.lists.Constants;
import com.Monsoon.lists.Lists.MealPlan;
import com.Monsoon.lists.Lists.TodoList;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.enums.MenuState;

import com.Monsoon.lists.databinding.FragmentAddListBinding;


public class AddListFragment extends Fragment {

    private FragmentAddListBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.ADD_LIST);


        String[] options = {
                Constants.BASIC,
                Constants.TODO,
                Constants.QUEUE,
                Constants.MEAL_PLAN
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.ListTypeSelecter.setAdapter(adapter);
        binding.ListTypeSelecter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.listDetailsBox.setDisplayedChild(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseList list;
                EditText requiredEditText;
                String name;
                switch (binding.ListTypeSelecter.getSelectedItemPosition()){
                    case 1:
                        requiredEditText = binding.listDetailsBox.findViewById(R.id.todo_list_name_input);
                        name = requiredEditText.getText().toString();
                        if (name.isBlank()){
                            Toast.makeText(requireContext(), "List name cannot be blank!", Toast.LENGTH_SHORT).show();
                        } else {
                           list = ((MainActivity)requireActivity()).getUserLists().CreateList(Constants.TODO, name);
                           SwitchCompat removedOnChecked = binding.listDetailsBox.findViewById(R.id.remove_on_checked);
                            ((TodoList)list).setRemoveOnChecked_(removedOnChecked.isChecked());
                            ((MainActivity)requireActivity()).OnListChange();
                            NavHostFragment.findNavController(AddListFragment.this).
                                    navigate(R.id.action_submit);
                        }
                        break;
                    case 2:
                        requiredEditText = binding.listDetailsBox.getCurrentView().findViewById(R.id.basic_list_name_input);
                        name = requiredEditText.getText().toString();
                        if (name.isBlank()){
                            Toast.makeText(requireContext(), "List name cannot be blank!", Toast.LENGTH_SHORT).show();
                        } else {
                            list = ((MainActivity)requireActivity()).getUserLists().CreateList(Constants.QUEUE, name);
                            ((MainActivity)requireActivity()).OnListChange();
                            NavHostFragment.findNavController(AddListFragment.this).
                                    navigate(R.id.action_submit);
                        }
                        break;
                    case 3:
                        requiredEditText = binding.listDetailsBox.findViewById(R.id.days_before_removal_input);
                        name = requiredEditText.getText().toString();
                        if (name.isBlank()){
                            Toast.makeText(requireContext(), "Days before removal must contain a number!", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                int daysBeforeRemoval = Integer.parseInt(name);
                                list = ((MainActivity)requireActivity()).getUserLists().CreateList(Constants.MEAL_PLAN);
                                ((MealPlan)list).setDaysBeforeRemoval(daysBeforeRemoval);
                                ((MainActivity)requireActivity()).OnListChange();
                                NavHostFragment.findNavController(AddListFragment.this).
                                        navigate(R.id.action_submit);
                            }
                            catch (Exception e){
                                Toast.makeText(requireContext(), "Days before removal must contain a number!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    default:
                        requiredEditText = binding.listDetailsBox.findViewById(R.id.basic_list_name_input);
                        name = requiredEditText.getText().toString();
                        if (name.isBlank()){
                            Toast.makeText(requireContext(), "List name cannot be blank!", Toast.LENGTH_SHORT).show();
                        } else {
                            list = ((MainActivity)requireActivity()).getUserLists().CreateList(Constants.BASIC, name);
                            ((MainActivity)requireActivity()).OnListChange();
                            NavHostFragment.findNavController(AddListFragment.this).
                                    navigate(R.id.action_submit);
                        }

                }

            }

        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddListBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}