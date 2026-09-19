package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.Monsoon.lists.Adapters.ListOfListsAdapter;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.ListOfListsFragmentBinding;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.OnMenuItemPressed;

public class ListOfListsFragment extends Fragment implements OnMenuItemPressed {

private ListOfListsFragmentBinding binding;
private ListOfListsAdapter adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = ListOfListsFragmentBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.LIST_OF_LISTS_NORMAL);
        binding.ListsView.setLayoutManager(new GridLayoutManager(getContext(), Constants.NUM_COLOMNS));
        ListOfLists userLists = ((MainActivity) requireActivity()).getUserLists();
        ((MainActivity)requireActivity()).setMenuListener(this);
        adapter = new ListOfListsAdapter(userLists, new ListOfListsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(baseList item) {
                ((MainActivity)requireActivity()).getUserLists().setCurrentList(item);
                ((MainActivity)getActivity()).changeToolbarTitle(item.getName());
                switch (item.getListType()) {
                    case Constants.BASIC:
                        NavHostFragment.findNavController(ListOfListsFragment.this).
                                navigate(R.id.action_load_basic_list);
                        break;
                    case Constants.TODO:
                        NavHostFragment.findNavController(ListOfListsFragment.this).
                                navigate(R.id.action_load_todo_list);
                        break;
                    case Constants.NUMBERED:
                        NavHostFragment.findNavController(ListOfListsFragment.this).
                                navigate(R.id.action_load_queue_list);
                        break;
                    default:
                            NavHostFragment.findNavController(ListOfListsFragment.this).
                                    navigate(R.id.action_load_meal_plan);
                        break;
                }

            }

            @Override
            public void onItemLongClick(baseList item) {
                ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.LIST_OF_LISTS_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }
        });
        binding.ListsView.setAdapter(adapter);

    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void OnBackPressed() {

    }

    @Override
    public void OnAddPressed() {
        ((MainActivity)requireActivity()).getUserLists().CreateList(Constants.NUMBERED, "Test Numbered List");
        ((MainActivity)requireActivity()).OnListChange();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnDonePressed() {

    }

    @Override
    public void OnDeletePressed() {

    }
}