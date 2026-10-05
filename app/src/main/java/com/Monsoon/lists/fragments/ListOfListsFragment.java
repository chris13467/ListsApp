package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.Monsoon.lists.Adapters.ListOfListsAdapter;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.ListOfListsWrapper;
import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.ListOfListsFragmentBinding;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Popup.Popups;

import java.util.ArrayList;

public class ListOfListsFragment extends AbstractListFragment<ListOfListsFragmentBinding, ListOfLists, ListOfListsWrapper, ListOfListsAdapter.ViewHolder> {

    ListType createListType;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = ListOfListsFragmentBinding.inflate(inflater, container, false);
      return binding.getRoot();
    }

    @Override
    protected void onChildViewCreated() {
        mainActivityCallback.onMenuContextChanged(MenuState.LIST_OF_LISTS_NORMAL);
        list = mainActivityCallback.getUserLists();

        adapter = new ListOfListsAdapter(list, new onListItemClickedListener<ListOfListsWrapper>() {
            @Override
            public void onItemClick(ListOfListsWrapper item) {
                mainActivityCallback.getUserLists().setCurrentList(item.getList());
                mainActivityCallback.changeToolbarTitle(item.getList().getName());
                switch (item.getList().getListType()) {
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
                                navigate(R.id.action_load_numbered_list);
                        break;
                    case Constants.BOOK:
                        NavHostFragment.findNavController(ListOfListsFragment.this).
                                navigate(R.id.action_load_book_list);
                        break;
                }
            }

            @Override
            public void onItemLongClick(ListOfListsWrapper item) {
                mainActivityCallback.onMenuContextChanged(MenuState.LIST_OF_LISTS_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemSelected(ListOfListsWrapper item) {

            }
        });

        binding.ListsView.setAdapter(adapter);
        binding.ListsView.setLayoutManager(new GridLayoutManager(getContext(), Constants.NUM_COLOMNS));
    }

    @Override
    protected void OnDropClearView() {
        // Drag and Drop not implemented not used
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void OnBackPressed() {
        switch (mainActivityCallback.getMenuState()){
            case LIST_OF_LISTS_NORMAL:
                // this should never happen!
                break;
            case LIST_OF_LISTS_ADD_LIST:
                binding.listContentInput.setText("");
                binding.listAdd.setVisibility(View.GONE);
                mainActivityCallback.onMenuContextChanged(MenuState.LIST_OF_LISTS_NORMAL);
                break;
            case LIST_OF_LISTS_SELECT_MODE:
                exitSelectMode(MenuState.LIST_OF_LISTS_NORMAL);
                break;
        }
    }

    @Override
    public void OnRandomizePressed() {
        // not used
    }

    @Override
    public void OnEditPressed() {
        //not used
    }

    @Override
    public void OnMoveToFrontPressed() {
        // not used
    }

    @Override
    public void OnMoveToBackPressed() {
        // not used
    }

    @Override
    public void OnRandomToFront() {
        // not used
    }

    @Override
    public void OnRandomTagToFront() {
        // not used
    }

    @Override
    public void OnAllTagToFront() {
        // not used
    }

    @Override
    public void OnAddPressed() {
        mainActivityCallback.getPopupGenerator().showListSelectorPickerDialog(new Popups.PopupCallback() {
            @Override
            public void onNumberSelected(int num) {
                // not used
            }

            @Override
            public void onListTypeSelected(ListType listType) {
                list.CreateList(listType.label, listType.label);
                mainActivityCallback.onMenuContextChanged(MenuState.LIST_OF_LISTS_NORMAL);
                mainActivityCallback.OnListChange();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTagClicked(int pos) {
                // not used
            }

            @Override
            public void onTagsSelected(ArrayList<String> newTags) {
                // not used
            }
        });

    }

    @Override
    public void OnDonePressed() {
        // not Used
    }

    @Override
    public void OnDeletePressed() {
        list.deleteSelected();
        exitSelectMode(MenuState.LIST_OF_LISTS_NORMAL);
        mainActivityCallback.OnListChange();
    }
}