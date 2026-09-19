package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.Monsoon.lists.Adapters.TodoListAdapter;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.TodoItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.TodoList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.TodoListFragmentBinding;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.OnMenuItemPressed;
import com.google.android.material.snackbar.Snackbar;

public class TodoListFragment extends Fragment implements OnMenuItemPressed  {

private TodoListFragmentBinding binding;
private TodoListAdapter adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = TodoListFragmentBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                OnBackPressed();
            }
        });
        super.onViewCreated(view, savedInstanceState);
        TodoList list = (TodoList) ((MainActivity)getActivity()).getUserLists().getCurrentList();
        ((MainActivity)getActivity()).changeToolbarTitle(list.getName());
        ((MainActivity)getActivity()).onMenuContextChanged(MenuState.TODO_LIST_NORMAL);
        list.checkRepeatable();
        binding.todoListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TodoListAdapter(list, new TodoListAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(TodoItem item) {
                ((MainActivity)requireActivity()).OnListChange();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(TodoItem item) {
                ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.TODO_LIST_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }
        });
        binding.todoListView.setAdapter(adapter);

    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void OnAddPressed() {

    }

    @Override
    public void OnDonePressed() {

    }

    @Override
    public void OnDeletePressed() {

    }

    @Override
    public void OnBackPressed() {
        NavHostFragment.findNavController(this).navigate(R.id.action_return_to_lists_of_lists);
    }
}