package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Monsoon.lists.Adapters.MealPlanAdapter;
import com.Monsoon.lists.ListItems.MealDay;
import com.Monsoon.lists.Lists.MealPlan;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.FragmentMealPlanBinding;
import com.Monsoon.lists.enums.Meal;
import com.Monsoon.lists.enums.MenuState;
import com.google.android.material.snackbar.Snackbar;

import java.time.format.DateTimeFormatter;

public class MealPlanFragment extends Fragment {

private FragmentMealPlanBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentMealPlanBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MealPlan mealPlan = (MealPlan) ((MainActivity)requireActivity()).getUserLists().getCurrentList();
        ((MainActivity)getActivity()).changeToolbarTitle(mealPlan.getName());
        ((MainActivity)getActivity()).onMenuContextChanged(MenuState.MEAL_PLAN_NORMAL);
        mealPlan.clean();
        binding.mealDayListView.setLayoutManager(new LinearLayoutManager(getContext()));
        MealPlanAdapter adapter = new MealPlanAdapter(mealPlan, new MealPlanAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(MealDay item) {

            }

            @Override
            public void onItemLongClick(MealDay item) {

            }

            @Override
            public void onDayClick(MealDay item) {

            }

            @Override
            public void onMealClick(MealDay item, Meal type) {

            }
        });
        binding.mealDayListView.setAdapter(adapter);

    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}