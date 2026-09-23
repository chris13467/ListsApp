package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.Monsoon.lists.util.Popups;

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
        setupDragAndDrop();
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

    private void exitSelectMode(){
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
        adapter.changeSelectMode(false);
        adapter.notifyDataSetChanged();
        list.clearSelections();
    }

    private void setupDragAndDrop() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, // Allowed drag direction
                0 // Swipe directions (0 = disabled)
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                adapter.onItemMove(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Not used at this time
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
                    viewHolder.itemView.setAlpha(0.7f);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1.0f);
                list.reassignPositions();
                adapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.queueListView);
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
                binding.numberedItemContentInput.setText("");
                binding.newListNumber.setText("1");
                binding.numberedItemAdd.setVisibility(View.GONE);
                mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
                break;
            case NUMBERED_LIST_SELECT_MODE:
                exitSelectMode();
            default:

        }

    }



    @Override
    public void OnAddPressed() {
        binding.numberedItemAdd.setVisibility(View.VISIBLE);
        binding.numberedItemAdd.requestFocus();
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_ADD_ITEM);
    }

    @Override
    public void OnDonePressed() {
        list.addItem(new NumberedItem(
                binding.numberedItemContentInput.getText().toString()),
                Integer.parseInt(binding.newListNumber.getText().toString()));
        adapter.notifyDataSetChanged();
        binding.numberedItemContentInput.setText("");
        binding.newListNumber.setText("1");
        binding.numberedItemAdd.setVisibility(View.GONE);
        mainActivityCallback.OnListChange();
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_NORMAL);
    }

    @Override
    public void OnDeletePressed() {
        list.deleteSelected();
        exitSelectMode();
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
        NumberedItem editItem = (NumberedItem) list.getSelected();
        binding.newListNumber.setText(String.valueOf(editItem.getPosition() + 1));
        binding.numberedItemContentInput.setText(editItem.getContents());
        list.removeItem(editItem);
        binding.numberedItemAdd.setVisibility(View.VISIBLE);
        binding.numberedItemAdd.requestFocus();
        mainActivityCallback.onMenuContextChanged(MenuState.NUMBERED_LIST_ADD_ITEM);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnMoveToFrontPressed() {
        NumberedItem item = (NumberedItem) list.getSelected();
        adapter.onItemMove(item.getPosition(), 0);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(), 250);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnMoveToBackPressed() {
        NumberedItem item = (NumberedItem) list.getSelected();
        adapter.onItemMove(item.getPosition(), list.getList().size() - 1);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(), 250);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnRandomToFront() {
        NumberedItem item = (NumberedItem) list.getRandomItem();
        adapter.onItemMove(item.getPosition(), 0);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(), 250);
        mainActivityCallback.OnListChange();
    }
}