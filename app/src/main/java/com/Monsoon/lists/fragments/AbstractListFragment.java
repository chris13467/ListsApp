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

import com.Monsoon.lists.Adapters.AbstractListAdapter;
import com.Monsoon.lists.Adapters.ListsViewHolder;
import com.Monsoon.lists.Adapters.NumberedListAdapter;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.NumberedItem;
import com.Monsoon.lists.Lists.NumberedList;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.NumberedListFragmentBinding;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.MainActivityCallback;
import com.Monsoon.lists.listeners.OnMenuItemPressed;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Popups;

abstract public class AbstractListFragment<B, L extends baseList, I extends BaseListItem, VH extends ListsViewHolder<I>> extends Fragment implements OnMenuItemPressed {
    protected B binding;
    protected AbstractListAdapter<L, I, VH> adapter;
    protected L list;
    protected MainActivityCallback mainActivityCallback;



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                OnBackPressed();
            }
        });
        super.onViewCreated(view, savedInstanceState);
        mainActivityCallback = ((MainActivity)requireActivity());
        list = (L) mainActivityCallback.getUserLists().getCurrentList();
        mainActivityCallback.changeToolbarTitle(list.getName());
        mainActivityCallback.setMenuListener(this);
        onChildViewCreated();

    }

    protected void exitSelectMode(MenuState menuState){
        mainActivityCallback.onMenuContextChanged(menuState);
        adapter.changeSelectMode(false);
        adapter.notifyDataSetChanged();
        list.clearSelections();
    }

    protected void setupDragAndDrop(RecyclerView view) {
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
                OnDropClearView();
                adapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(view);
    }

    protected abstract void onChildViewCreated();
    protected abstract void OnDropClearView();

    @Override
    public void OnSelectAll(boolean b) {
        for (int i = 0; i < list.getList().size(); i++){
            list.getList().get(i).setSelected(b);
        }
        adapter.notifyDataSetChanged();
    }


}