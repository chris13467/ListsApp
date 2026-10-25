package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Monsoon.lists.Adapters.NumberedListAdapter;
import com.Monsoon.lists.ListItems.NumberedItem;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.NumberedListFragmentBinding;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Popup.Popups;

import java.util.ArrayList;

public class NumberedListFragment extends AbstractListFragment<NumberedListFragmentBinding, NumberedList, NumberedItem, NumberedListAdapter.ViewHolder> {


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
      binding = NumberedListFragmentBinding.inflate(inflater, container, false);
      return binding.getRoot();
    }

    @Override
    protected void onChildViewCreated() {
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
        binding.queueListView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NumberedListAdapter(list, new onListItemClickedListener<NumberedItem>() {
            @Override
            public void onItemClick(NumberedItem item) {
                mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(NumberedItem item) {
//
            }

            @Override
            public void onItemSelected(NumberedItem item) {
                mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_SELECT_MODE);
            }
        });
        binding.queueListView.setAdapter(adapter);
        setupDragAndDrop(binding.queueListView);
        binding.newListNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityCallback.getPopupGenerator().showNumberPickerDialog(
                        Integer.parseInt(binding.newListNumber.getText().toString()),
                        list.getList().size() + 1,
                        new Popups.PopupCallback() {
                            @Override
                            public void onNumberSelected(int num) {
                                binding.newListNumber.setText(String.valueOf(num));
                            }
                        });
            }
        });

    }

    @Override
    protected void OnDropClearView() {
        list.reassignPositions();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void OnBackPressed() {
        switch (mainActivityCallback.getMenuState()){
            case NUMBERED_LIST_NORMAL:
                NavHostFragment.findNavController(this).navigate(R.id.action_return_to_lists_of_lists);
                break;
            case NUMBERED_LIST_ADD_ITEM:
            case NUMBERED_LIST_EDIT_ITEM:
                binding.numberedItemContentInput.setText("");
                binding.newListNumber.setText("1");
                binding.numberedItemAdd.setVisibility(View.GONE);
                mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
                adapter.setItemsClickable(true);
                break;
            case NUMBERED_LIST_SELECT_MODE:
                exitSelectMode(MenuState.NUMBERED_LIST_NORMAL);
            default:

        }

    }



    @Override
    public void OnAddPressed() {
        binding.numberedItemAdd.setVisibility(View.VISIBLE);
        binding.numberedItemAdd.requestFocus();
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_ADD_ITEM);
        adapter.setItemsClickable(false);
    }

    @Override
    public void OnDonePressed() {
        if (mainActivityCallback.getMenuState() == MenuState.NUMBERED_LIST_EDIT_ITEM){
            list.removeItem(editItem);
        }
        list.addItem(new NumberedItem(
                binding.numberedItemContentInput.getText().toString()),
                Integer.parseInt(binding.newListNumber.getText().toString()));
        adapter.notifyDataSetChanged();
        binding.numberedItemContentInput.setText("");
        binding.newListNumber.setText("1");
        binding.numberedItemAdd.setVisibility(View.GONE);
        mainActivityCallback.OnListChange();
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
        adapter.setItemsClickable(true);
    }

    @Override
    public void OnDeletePressed() {
        list.deleteSelected();
        exitSelectMode(MenuState.NUMBERED_LIST_NORMAL);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnRandomizePressed() {
        list.Randomize(1);
        adapter.notifyDataSetChanged();
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnEditPressed() {
        editItem = (NumberedItem) list.getSelected();
        binding.newListNumber.setText(String.valueOf(editItem.getPosition() + 1));
        binding.numberedItemContentInput.setText(editItem.getContents());
        binding.numberedItemAdd.setVisibility(View.VISIBLE);
        binding.numberedItemAdd.requestFocus();
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_EDIT_ITEM);
        adapter.setItemsClickable(false);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnMoveToFrontPressed() {
        NumberedItem item = (NumberedItem) list.getSelected();
        adapter.onItemMove(item.getPosition(), 0);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(MenuState.NUMBERED_LIST_NORMAL), 250);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnMoveToBackPressed() {
        NumberedItem item = (NumberedItem) list.getSelected();
        adapter.onItemMove(item.getPosition(), list.getList().size() - 1);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(MenuState.NUMBERED_LIST_NORMAL), 250);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnRandomToFront() {
        NumberedItem item = (NumberedItem) list.getRandomItem();
        adapter.onItemMove(item.getPosition(), 0);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(MenuState.NUMBERED_LIST_NORMAL), 250);
        mainActivityCallback.OnListChange();
    }


}