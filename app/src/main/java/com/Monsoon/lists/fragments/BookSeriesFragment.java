package com.Monsoon.lists.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Monsoon.lists.Adapters.BooksInSeriesAdapter;
import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.Lists.BookSeriesList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.databinding.FragmentBookListBinding;
import com.Monsoon.lists.databinding.FragmentBookSeriesBinding;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Popup.Popups;
import com.Monsoon.lists.util.Tags;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookSeriesFragment extends AbstractListFragment<FragmentBookSeriesBinding, BookSeriesList, BookItem, BooksInSeriesAdapter.ViewHolder> {
    private BookSeriesItem item;
    private BooksInSeriesAdapter bAdapter;
    private Tags newBookTags;
    private boolean addMode;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentBookSeriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    protected void onChildViewCreated() {
        item = list.getCurrentItem();
        addMode = false;
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES);
        mainActivityCallback.changeToolbarTitle(item.getContents());
        binding.booksInSeriesView.setLayoutManager(new LinearLayoutManager(requireContext()));
        bAdapter = new BooksInSeriesAdapter(item.getBookList(), new onListItemClickedListener<BookItem>() {
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
        binding.booksInSeriesView.setAdapter(bAdapter);
        //setupDragAndDrop(binding.booksInSeriesView);

        binding.addBookToSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addMode){
                    BookItem book = new BookItem(
                            binding.bookTitleInput.getText().toString(),
                            newBookTags
                    );
                    // set book release date to data picker date
                    String releaseDate = binding.bookReleaseDatePicker.getText().toString();
                    if (!releaseDate.isEmpty()){
                        book.setReleaseDate(LocalDate.parse(releaseDate));
                    }
                    // if upcoming put upcoming in front of name
                    book.setUpcoming(binding.isUpcoming.isChecked());
                    // add series title to book
                    book.setSeriesTitle(item.getContents());
                    // add new book to series
                    item.addBook(book);
                    // notify the adapter
                    bAdapter.onDataChanged(item.getBookList());
                    mainActivityCallback.OnListChange();
                    hideAddBookPane();
                } else {
                    showAddBookPane();
                }
            }
        });

        binding.newBookNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityCallback.getPopupGenerator().showNumberPickerDialog(
                        Integer.parseInt(binding.newBookNumber.getText().toString()),
                        item.getBookList().getList().size() + 1,
                        new Popups.PopupCallback() {
                            @Override
                            public void onNumberSelected(int num) {
                                binding.newBookNumber.setText(String.valueOf(num));
                            }

                            @Override
                            public void onListTypeSelected(ListType listType) {
                                // not used
                            }

                            @Override
                            public void onTagClicked(int pos) {
                                // not used
                            }

                            @Override
                            public void onTagsSelected(ArrayList<String> newTags) {
                                // not used
                            }

                            @Override
                            public void onDateSelected(String date) {

                            }
                        }
                );
            }
        });

        binding.bookReleaseDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityCallback.getPopupGenerator().showDatePickerDialog(new Popups.PopupCallback() {
                    @Override
                    public void onNumberSelected(int num) {

                    }

                    @Override
                    public void onListTypeSelected(ListType listType) {

                    }

                    @Override
                    public void onTagClicked(int pos) {

                    }

                    @Override
                    public void onTagsSelected(ArrayList<String> newTags) {

                    }

                    @Override
                    public void onDateSelected(String date) {
                        binding.bookReleaseDatePicker.setText(date) ;
                    }
                });
            }
        });
    }

    @Override
    protected void OnDropClearView() {

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
        NavHostFragment.findNavController(BookSeriesFragment.this).
                navigate(R.id.action_return_to_book_series_list);
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
    public void OnRandomTagToFront() {

    }

    @Override
    public void OnAllTagToFront() {

    }

    @Override
    protected void bindTagTextToView(SpannableString spannableString) {
        binding.bookTagsTextView.setText(spannableString);
        binding.bookTagsTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void ResolveTagsSelected(ArrayList<String> newTags) {
        newBookTags = new Tags();
        newBookTags.addTags(newTags);
        setTagText(newBookTags);
        mainActivityCallback.OnSettingsChanged();
    }

    private void showAddBookPane(){
        addMode = true;
        binding.addBookToSeries.setText(R.string.add_book);
        binding.addBookPane.setVisibility(View.VISIBLE);
        newBookTags = new Tags();
        setTagText(newBookTags);
    }

    private void hideAddBookPane(){
        addMode = false;
        binding.addBookToSeries.setText(R.string.add_book_to_series);
        binding.addBookPane.setVisibility(View.GONE);
        binding.newBookNumber.setText("1");
        binding.bookTitleInput.setText("");
        binding.isUpcoming.setChecked(false);
        binding.bookReleaseDatePicker.setText("");
    }

}