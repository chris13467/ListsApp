package com.Monsoon.lists.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Monsoon.lists.Adapters.BookListAdapter;
import com.Monsoon.lists.Adapters.BookSeriesListAdapter;
import com.Monsoon.lists.Adapters.BooksInSeriesAdapter;
import com.Monsoon.lists.Adapters.SeriesTrackerAdapter;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.ListItems.ListOfListsWrapper;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.Lists.BookSeriesList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.FragmentSeriesReleaseTrackerBinding;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Popup.Popups;
import com.Monsoon.lists.util.Tags;

import java.util.ArrayList;

public class SeriesReleaseTrackerFragment extends AbstractListFragment<FragmentSeriesReleaseTrackerBinding, BookSeriesList, BookItem, BooksInSeriesAdapter.ViewHolder> {

    private Tags newBookTags;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSeriesReleaseTrackerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    protected void onChildViewCreated() {
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_TRACKER);
        binding.bookListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        SeriesTrackerAdapter uAdapter = new SeriesTrackerAdapter(list.getAllUpcomingBooks(), new onListItemClickedListener<BookItem>() {
            @Override
            public void onItemClick(BookItem item) {

            }

            @Override
            public void onItemLongClick(BookItem item) {

            }

            @Override
            public void onItemSelected(BookItem item) {

            }
        });
        binding.bookListView.setAdapter(uAdapter);

        binding.estimatedBookListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        SeriesTrackerAdapter eAdapter = new SeriesTrackerAdapter(list.getAllEstimatedUpcomingBooks(), new onListItemClickedListener<BookItem>() {
            @Override
            public void onItemClick(BookItem item) {

            }

            @Override
            public void onItemLongClick(BookItem item) {

            }

            @Override
            public void onItemSelected(BookItem item) {

            }
        });
        binding.estimatedBookListView.setAdapter(eAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    protected void OnDropClearView() {
        // not used
    }

    @Override
    public void OnAddPressed() {
       // not used
    }

    @Override
    public void OnDonePressed() {
        // not used
    }

    @Override
    public void OnDeletePressed() {
      // not used
    }

    @Override
    public void OnBackPressed() {
        NavHostFragment.findNavController(SeriesReleaseTrackerFragment.this).
                navigate(R.id.action_return_to_book_series_list);
    }


    @Override
    public void OnEditPressed() {
        // not used
    }


}