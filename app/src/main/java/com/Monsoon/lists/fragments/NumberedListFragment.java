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

import com.Monsoon.lists.Adapters.NumberedListAdapter;
import com.Monsoon.lists.ListItems.NumberedItem;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.NumberedListFragmentBinding;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.MainActivityCallback;
import com.Monsoon.lists.listeners.OnMenuItemPressed;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public class NumberedListFragment extends Fragment implements OnMenuItemPressed {

private NumberedListFragmentBinding binding;
private NumberedListAdapter adapter;
private NumberedList list;
private MainActivityCallback mainActivityCallback;



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = NumberedListFragmentBinding.inflate(inflater, container, false);
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
        mainActivityCallback = ((MainActivity)requireActivity());
        list = (NumberedList) mainActivityCallback.getUserLists().getCurrentList();
        mainActivityCallback.changeToolbarTitle(list.getName());
        mainActivityCallback.setMenuListener(this);
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
        binding.queueListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NumberedListAdapter(list, new onListItemClickedListener<NumberedItem>() {
            @Override
            public void onItemClick(NumberedItem item) {

            }

            @Override
            public void onItemLongClick(NumberedItem item) {
                mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemSelected(NumberedItem item) {
                mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_SELECT_MODE);
            }
        });
        binding.queueListView.setAdapter(adapter);
        if (list.getCurrentItem() != null){

        }

    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void OnBackPressed() {
        NavHostFragment.findNavController(this).navigate(R.id.action_return_to_lists_of_lists);
    }

    @Override
    public void OnAddPressed() {
        binding.numberedItemAdd.setVisibility(View.VISIBLE);
        binding.numberedItemAdd.requestFocus();
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_ADD_ITEM);
    }

    @Override
    public void OnDonePressed() {
        list.addItem(new NumberedItem(binding.queueItemContentInput.getText().toString()));
        adapter.notifyDataSetChanged();
        binding.numberedItemAdd.setVisibility(View.GONE);
        mainActivityCallback.OnListChange();
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
    }

    @Override
    public void OnDeletePressed() {
        switch (mainActivityCallback.getMenuState()){
            case NUMBERED_LIST_ADD_ITEM:
                mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
                binding.numberedItemAdd.setVisibility(View.GONE);
                break;
            default:

        }
    }
}