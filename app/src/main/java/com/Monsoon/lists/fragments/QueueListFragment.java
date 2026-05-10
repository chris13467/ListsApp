package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Monsoon.lists.Adapters.BasicListAdapter;
import com.Monsoon.lists.Adapters.QueueListAdapter;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.QueueItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.QueueList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.QueueListFragmentBinding;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.google.android.material.snackbar.Snackbar;

public class QueueListFragment extends Fragment {

private QueueListFragmentBinding binding;
private QueueListAdapter adapter;
private QueueList list;



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = QueueListFragmentBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = (QueueList) ((MainActivity)getActivity()).getUserLists().getCurrentList();
        ((MainActivity)getActivity()).changeToolbarTitle(list.getName());
        ((MainActivity)getActivity()).onMenuContextChanged(MenuState.QUEUE_LIST_NORMAL);
        binding.queueListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QueueListAdapter(list, new onListItemClickedListener<QueueItem>() {
            @Override
            public void onItemClick(QueueItem item) {

            }

            @Override
            public void onItemLongClick(QueueItem item) {

            }
        });
        binding.queueListView.setAdapter(adapter);


    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}