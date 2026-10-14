package com.Monsoon.lists.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookSeriesFragment extends AbstractListFragment<FragmentBookSeriesBinding, BookSeriesList, BookItem, BooksInSeriesAdapter.ViewHolder> {
    private BookSeriesItem item;
    private BooksInSeriesAdapter bAdapter, uAdapter;
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
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_NORMAL);
        mainActivityCallback.changeToolbarTitle(item.getContents());
        binding.booksInSeriesView.setLayoutManager(new LinearLayoutManager(requireContext()));
        bAdapter = new BooksInSeriesAdapter(item.getBookList(), new onListItemClickedListener<BookItem>() {
            @Override
            public void onItemClick(BookItem item) {
                OnSelectModeEntered();
            }

            @Override
            public void onItemLongClick(BookItem item) {

            }

            @Override
            public void onItemSelected(BookItem item) {
                notifiyDatasetChanged();
            }
        });
        binding.booksInSeriesView.setAdapter(bAdapter);
        setupDragAndDrop(binding.booksInSeriesView);


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
                        binding.bookReleaseDatePicker.setText(date);
                    }
                });
            }
        });

        uAdapter = new BooksInSeriesAdapter(item.getUpcomingBooks(), new onListItemClickedListener<BookItem>() {
            @Override
            public void onItemClick(BookItem item) {
                OnSelectModeEntered();
            }

            @Override
            public void onItemLongClick(BookItem item) {

            }

            @Override
            public void onItemSelected(BookItem item) {
                BookSeriesFragment.this.item.moveBookFromUpcomingToSeries(item);
                notifiyDatasetChanged();
            }
        });
        uAdapter.setPositionOffset(item.getBookList().getList().size()+1);
        binding.upcomingBooksInSeriesView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.upcomingBooksInSeriesView.setAdapter(uAdapter);

        loadDataOnViews();
    }

    @Override
    protected void OnDropClearView() {
        item.getBookList().reassignPositions();
        loadDataOnViews();
    }

    @Override
    public void OnAddPressed() {
        showAddBookPane();
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_ADD);
    }

    @Override
    public void OnDonePressed() {
        BookItem book = new BookItem(
                binding.bookTitleInput.getText().toString(),
                newBookTags
        );
        // set book release date to data picker date
        String releaseDate = binding.bookReleaseDatePicker.getText().toString();
        if (!releaseDate.isEmpty()){
            book.setReleaseDate(LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
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
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_NORMAL);
        hideAddBookPane();
    }

    @Override
    public void OnDeletePressed() {
        item.deleteSelections();
        notifiyDatasetChanged();
    }

    @Override
    public void OnBackPressed() {
        switch (mainActivityCallback.getMenuState()){
            case BOOK_SERIES_NORMAL:
                NavHostFragment.findNavController(this).navigate(R.id.action_return_to_book_series_list);
                break;
            case BOOK_SERIES_ADD:
                hideAddBookPane();
                break;
            case BOOK_SERIES_SELECT_MODE:
                exitSelectMode(MenuState.BOOK_SERIES_NORMAL);
            default:

        }
    }

    @Override
    public void OnRandomizePressed() {
        // NotUsed
    }

    @Override
    public void OnEditPressed() {
        BookItem edit = item.getSelected();
        if (edit != null){
            showAddBookPane();
            binding.newBookNumber.setText(String.valueOf(edit.getPosition() + 1));
            binding.bookTitleInput.setText(edit.getContents());
            if (edit.isUpcoming()) item.removeBookFromUpcomingBooks(edit);
            else item.removeBookFromBookSeries(edit);
            binding.isUpcoming.setChecked(edit.isUpcoming());
            if (edit.getReleaseDate() != null)
                binding.bookReleaseDatePicker.setText(edit.getReleaseDate()
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_ADD);
            mainActivityCallback.OnListChange();
        }
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
        item.estimateNewReleaseDate();
        notifiyDatasetChanged();
    }

    @Override
    public void LoedBookReleaseTracker() {
        //not used
    }

    @Override
    protected void bindTagTextToView(SpannableString spannableString) {
        binding.bookSeriesTagsTextView.setText(spannableString);
        binding.bookSeriesTagsTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void ResolveTagsSelected(ArrayList<String> newTags) {
        newBookTags = new Tags();
        newBookTags.addTags(newTags);
        item.setTags(newBookTags);
        mainActivityCallback.OnSettingsChanged();
        mainActivityCallback.OnListChange();
        notifiyDatasetChanged();
    }

    private void showAddBookPane(){
        addMode = true;
        binding.addBookPane.setVisibility(View.VISIBLE);
        newBookTags = new Tags();
    }

    private void hideAddBookPane(){
        addMode = false;
        binding.addBookPane.setVisibility(View.GONE);
        binding.newBookNumber.setText("1");
        binding.bookTitleInput.setText("");
        binding.isUpcoming.setChecked(false);
        binding.bookReleaseDatePicker.setText("");
        notifiyDatasetChanged();
    }

    @Override
    protected void onItemMove(int fromPosition, int toPosition) {
        bAdapter.onItemMove(fromPosition, toPosition);
    }

    @Override
    protected void onClearView(@NonNull RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setAlpha(1.0f);
        OnDropClearView();
        notifiyDatasetChanged();
    }

    protected void loadDataOnViews(){
        StringBuilder sb = new StringBuilder("Author: ");
        sb.append(item.getAuthor());
        binding.seriesAuthorTextView.setText(sb.toString());
        sb = new StringBuilder("(");
        sb.append(item.getNumberOfReadBooks()).append("/").append(item.getNumberOfReleasedBooks())
                .append(")");
        binding.numBooksTextView.setText(sb.toString());
        if (item.getNumberOfReadBooks() == item.getNumberOfReleasedBooks() && item.hasUpcomingBook())
            binding.diamondIcon.setImageTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.upcoming)));
        else if (item.getNumberOfReadBooks() == item.getNumberOfReleasedBooks())
            binding.diamondIcon.setImageTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.read)));
        else
            binding.diamondIcon.setImageTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.not_read)));
        if (item.hasUpcomingBook()){
            if (item.getNextUpcomingBook().getReleaseDate() != null) {
                binding.nextUpcomingReleaseTextView.setVisibility(View.VISIBLE);
                sb = new StringBuilder("Next upcoming book released on ");
                sb.append(item.getNextUpcomingBook().getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                binding.nextUpcomingReleaseTextView.setText(sb.toString());
            }
            binding.upcomingSectionBooks.setVisibility(View.VISIBLE);
        }
        else if (item.getEstimatedNewReleaseDate() != null){
            binding.nextUpcomingReleaseTextView.setVisibility(View.VISIBLE);
            sb = new StringBuilder("Next Upcoming book estimated to release on ");
            sb.append(item.getEstimatedNewReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            binding.nextUpcomingReleaseTextView.setText(sb.toString());
        }
        else {
            binding.nextUpcomingReleaseTextView.setVisibility(View.GONE);
            binding.upcomingSectionBooks.setVisibility(View.GONE);
        }
        setTagText(item.getTags());
    }

    protected void notifiyDatasetChanged(){
        uAdapter.setPositionOffset(item.getBookList().getList().size()+1);
        bAdapter.notifyDataSetChanged();
        uAdapter.notifyDataSetChanged();
        loadDataOnViews();
        mainActivityCallback.OnListChange();
    }

    protected void OnSelectModeEntered(){
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_SERIES_SELECT_MODE);
        bAdapter.changeSelectMode(true);
        uAdapter.changeSelectMode(true);
        notifiyDatasetChanged();
    }

    @Override
    protected void exitSelectMode(MenuState menuState) {
        mainActivityCallback.onMenuContextChanged(menuState);
        bAdapter.changeSelectMode(false);
        uAdapter.changeSelectMode(false);
        bAdapter.notifyDataSetChanged();
        uAdapter.notifyDataSetChanged();
        item.clearSelections();
    }
}