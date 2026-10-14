package com.Monsoon.lists.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Monsoon.lists.Adapters.BookSeriesListAdapter;

import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.Lists.BookSeriesList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.FragmentBookSeriesListBinding;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Popup.Popups;
import com.Monsoon.lists.util.Tags;

import java.util.ArrayList;

public class BookSeriesListFragment extends AbstractListFragment<FragmentBookSeriesListBinding,
        BookSeriesList, BookSeriesItem, BookSeriesListAdapter.ViewHolder> {

    private Tags newBookTags;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentBookSeriesListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    protected void onChildViewCreated() {
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_LIST_NORMAL);
        binding.bookSeriesListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BookSeriesListAdapter(list, new onListItemClickedListener<BookSeriesItem>() {
            @Override
            public void onItemClick(BookSeriesItem item) {
                list.setCurrentItem(item);
                NavHostFragment.findNavController(BookSeriesListFragment.this).
                        navigate(R.id.action_load_book_series);
            }

            @Override
            public void onItemLongClick(BookSeriesItem item) {
                mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_LIST_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemSelected(BookSeriesItem item) {
                //not used
            }
        });
        binding.bookSeriesListView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void OnDropClearView() {
        //not used
    }

    @Override
    public void OnAddPressed() {
        binding.bookSeriesItemAdd.setVisibility(View.VISIBLE);
        binding.bookSeriesItemContentInput.requestFocus();
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_LIST_ADD);
    }

    @Override
    public void OnDonePressed() {
        list.addItem(new BookSeriesItem(binding.bookSeriesItemContentInput.getText().toString()));
        adapter.notifyDataSetChanged();
        binding.bookSeriesItemContentInput.setText("");
        binding.bookSeriesItemAdd.setVisibility(View.GONE);
        mainActivityCallback.OnListChange();
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_LIST_NORMAL);
    }

    @Override
    public void OnDeletePressed() {
        list.deleteSelected();
        exitSelectMode(MenuState.BOOK_SERIES_LIST_NORMAL);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnBackPressed() {
        switch (mainActivityCallback.getMenuState()){
            case BOOK_SERIES_LIST_NORMAL:
                NavHostFragment.findNavController(this).navigate(R.id.action_return_to_lists_of_lists);
                break;
            case BOOK_SERIES_LIST_ADD:
                binding.bookSeriesItemContentInput.setText("");
                binding.bookSeriesItemAdd.setVisibility(View.GONE);
                mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_LIST_NORMAL);
                break;
            case BOOK_SERIES_LIST_SELECT_MODE:
                exitSelectMode(MenuState.BOOK_SERIES_LIST_NORMAL);
            default:

        }
    }

    @Override
    public void OnRandomizePressed() {
        // not used
    }

    @Override
    public void OnEditPressed() {
        BookSeriesItem editItem = (BookSeriesItem) list.getSelected();
        binding.bookSeriesItemContentInput.setText(editItem.getContents());
        list.removeItem(editItem);
        binding.bookSeriesItemAdd.setVisibility(View.VISIBLE);
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_LIST_ADD);
        mainActivityCallback.OnListChange();
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
    public void EstimateReleaseDate() {

    }

    @Override
    public void LoedBookReleaseTracker() {

    }
}