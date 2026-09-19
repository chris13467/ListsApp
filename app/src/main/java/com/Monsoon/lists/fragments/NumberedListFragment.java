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
import com.Monsoon.lists.listeners.OnMenuItemPressed;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public class NumberedListFragment extends Fragment implements OnMenuItemPressed {

private NumberedListFragmentBinding binding;
private NumberedListAdapter adapter;
private NumberedList list;



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
        list = (NumberedList) ((MainActivity)requireActivity()).getUserLists().getCurrentList();
        ((MainActivity)requireActivity()).changeToolbarTitle(list.getName());
        ((MainActivity)requireActivity()).setMenuListener(this);
        ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
        binding.queueListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NumberedListAdapter(list, new onListItemClickedListener<NumberedItem>() {
            @Override
            public void onItemClick(NumberedItem item) {

            }

            @Override
            public void onItemLongClick(NumberedItem item) {
                ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.NUMBERED_LIST_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemSelected(NumberedItem item) {
                ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.NUMBERED_LIST_SELECT_MODE);
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
        ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.NUMBERED_LIST_ADD_ITEM);
    }
}