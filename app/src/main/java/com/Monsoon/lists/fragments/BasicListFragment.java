package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Monsoon.lists.Adapters.BasicListAdapter;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.listeners.OnMenuItemPressed;
import com.Monsoon.lists.databinding.BasicListFragmentBinding;
import com.Monsoon.lists.enums.MenuState;

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
        super.onViewCreated(view, savedInstanceState);
        list = (BasicList) ((MainActivity)getActivity()).getUserLists().getCurrentList();
        ((MainActivity)getActivity()).onMenuContextChanged(MenuState.BASIC_LIST_NORMAL);
        ((MainActivity)getActivity()).changeToolbarTitle(list.getName());
        ((MainActivity)getActivity()).setMenuListener(this);
        binding.basicListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BasicListAdapter(list, new BasicListAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(BaseListItem item) {

            }

            @Override
            public void onItemLongClick(BaseListItem item) {
                ((MainActivity)requireActivity()).onMenuContextChanged(MenuState.BASIC_LIST_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
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
    public void OnDeletePressed() {

    }
}