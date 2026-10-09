package com.Monsoon.lists.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Monsoon.lists.Adapters.BookListAdapter;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.NumberedItem;
import com.Monsoon.lists.Lists.BookList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.databinding.FragmentBookListBinding;
import com.Monsoon.lists.listeners.MainActivityCallback;
import com.Monsoon.lists.listeners.onListItemClickedListener;
import com.Monsoon.lists.util.Popup.Popups;
import com.Monsoon.lists.util.Tags;

import java.util.ArrayList;

public class BookListFragment extends AbstractListFragment<FragmentBookListBinding, BookList, BookItem, BookListAdapter.ViewHolder> {

    private Tags newBookTags;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentBookListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    protected void onChildViewCreated() {
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_LIST_NORMAL);
        binding.bookListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BookListAdapter(list, new onListItemClickedListener<BookItem>() {
            @Override
            public void onItemClick(BookItem item) {
                mainActivityCallback.onMenuContextChanged(MenuState.BOOK_LIST_SELECT_MODE);
                adapter.changeSelectMode(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(BookItem item) {

            }

            @Override
            public void onItemSelected(BookItem item) {

            }
        });
        binding.bookListView.setAdapter(adapter);
        setupDragAndDrop(binding.bookListView);
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void OnDropClearView() {
        list.reassignPositions();
    }

    @Override
    public void OnAddPressed() {
        newBookTags = new Tags();
        setTagText(newBookTags);
        binding.bookItemAdd.setVisibility(View.VISIBLE);
        binding.bookItemContentInput.requestFocus();
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_LIST_ADD);
    }

    @Override
    public void OnDonePressed() {
        list.addItem(new BookItem(
                binding.bookItemContentInput.getText().toString(), newBookTags),
                Integer.parseInt(binding.newListNumber.getText().toString())
        );
        adapter.notifyDataSetChanged();
        binding.bookItemContentInput.setText("");
        binding.newListNumber.setText("1");
        binding.bookItemAdd.setVisibility(View.GONE);
        mainActivityCallback.OnListChange();
        mainActivityCallback.getSettings().getUserTages().clearSelections();
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_LIST_NORMAL);
    }

    @Override
    public void OnDeletePressed() {
        list.deleteSelected();
        exitSelectMode(MenuState.BOOK_LIST_NORMAL);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnBackPressed() {
        switch (mainActivityCallback.getMenuState()){
            case BOOK_LIST_NORMAL:
                NavHostFragment.findNavController(this).navigate(R.id.action_return_to_lists_of_lists);
                break;
            case BOOK_LIST_ADD:
                binding.bookItemContentInput.setText("");
                binding.newListNumber.setText("1");
                binding.bookItemAdd.setVisibility(View.GONE);
                mainActivityCallback.onMenuContextChanged(MenuState.BOOK_LIST_NORMAL);
                break;
            case BOOK_LIST_SELECT_MODE:
                exitSelectMode(MenuState.BOOK_LIST_NORMAL);
            default:

        }
    }

    @Override
    public void OnRandomizePressed() {
        list.Randomize(1);
        adapter.notifyDataSetChanged();
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnEditPressed() {
        BookItem editItem = (BookItem) list.getSelected();
        binding.newListNumber.setText(String.valueOf(editItem.getPosition() + 1));
        binding.bookItemContentInput.setText(editItem.getContents());
        newBookTags = new Tags();
        newBookTags.addTags(editItem.getTags().getList());
        setTagText(newBookTags);
        list.removeItem(editItem);
        binding.bookItemAdd.setVisibility(View.VISIBLE);
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_LIST_ADD);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnMoveToFrontPressed() {
        BookItem item = (BookItem) list.getSelected();
        adapter.onItemMove(item.getPosition(), 0);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnMoveToBackPressed() {
        BookItem item = (BookItem) list.getSelected();
        adapter.onItemMove(item.getPosition(), list.getList().size() - 1);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnRandomToFront() {
        BookItem item = (BookItem) list.getRandomItem();
        adapter.onItemMove(item.getPosition(), 0);
        list.reassignPositions();
        new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnRandomTagToFront() {
        mainActivityCallback.getPopupGenerator().showTagsPickerDialog(
                mainActivityCallback.getSettings().getUserTages(),
                true,
                new Popups.PopupCallback() {
                    @Override
                    public void onNumberSelected(int num) {
                        // not used
                    }

                    @Override
                    public void onListTypeSelected(ListType listType) {
                        //not used
                    }

                    @Override
                    public void onTagClicked(int pos) {
                        // not used
                    }

                    @Override
                    public void onTagsSelected(ArrayList<String> newTags) {
                        BookItem item = (BookItem) list.getRandomItem(newTags.get(0));
                        if (item == null)
                            Toast.makeText(requireContext(), "No Books with this tag!", Toast.LENGTH_SHORT).show();
                        else {
                            adapter.onItemMove(item.getPosition(), 0);
                            list.reassignPositions();
                            new Handler(Looper.getMainLooper()).postDelayed(() -> exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
                            mainActivityCallback.OnListChange();
                        }
                    }

                    @Override
                    public void onDateSelected(String date) {

                    }
                }
        );
    }

    @Override
    public void OnAllTagToFront() {
        mainActivityCallback.getPopupGenerator().showTagsPickerDialog(
                mainActivityCallback.getSettings().getUserTages(),
                true,
                new Popups.PopupCallback() {
                    @Override
                    public void onNumberSelected(int num) {
                        // not used
                    }

                    @Override
                    public void onListTypeSelected(ListType listType) {
                        //not used
                    }

                    @Override
                    public void onTagClicked(int pos) {
                        // not used
                    }

                    @Override
                    public void onTagsSelected(ArrayList<String> newTags) {
                        list.taggedItemsToTop(newTags.get(0));
                        list.reassignPositions();
                        new Handler(Looper.getMainLooper()).postDelayed(() -> exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
                        mainActivityCallback.OnListChange();
                    }

                    @Override
                    public void onDateSelected(String date) {

                    }
                }
        );
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
}