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

import com.Monsoon.lists.Adapters.BasicListAdapter;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.OnMenuItemPressed;
import com.Monsoon.lists.databinding.BasicListFragmentBinding;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public class BasicListFragment extends Fragment implements OnMenuItemPressed {

private BasicListFragmentBinding binding;
private BasicListAdapter adapter;
private BasicList list;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = BasicListFragmentBinding.inflate(inflater, container, false);
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
        list = (BasicList) ((MainActivity)getActivity()).getUserLists().getCurrentList();
        ((MainActivity)getActivity()).onMenuContextChanged(MenuState.BASIC_LIST_NORMAL);
        ((MainActivity)getActivity()).changeToolbarTitle(list.getName());
        ((MainActivity)getActivity()).setMenuListener(this);
        binding.basicListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BasicListAdapter(list, new onListItemClickedListener<BaseListItem>() {
            @Override
            public void onItemClick(BaseListItem item) {

            }

            @Override
            public void onItemLongClick(BaseListItem item) {
                ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.BASIC_LIST_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemSelected(BaseListItem item) {

            }
        });
        binding.basicListView.setAdapter(adapter);


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

    @Override
    public void OnRandomizePressed() {

    }

    @Override
    public void OnEditPressed() {

    }

    @Override
    public void OnMoveToFrontPressed() {

    }

    @Override
    public void OnMoveToBackPressed() {

    }

    @Override
    public void OnRandomToFront() {

    }

    @Override
    public void OnSelectAll(boolean b) {

    }
}