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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Monsoon.lists.Adapters.BookListAdapter;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BookItem;
import com.Monsoon.lists.ListItems.BookSeriesItem;
import com.Monsoon.lists.ListItems.ListOfListsWrapper;
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
    private int frontIndex;

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
    public void OnAddToOtherPressed() {
        editItem = (BookItem) list.getSelected();
        mainActivityCallback.getPopupGenerator().showMoveListSelector(
                mainActivityCallback.getUserLists(),
                list, editItem,
                new Popups.PopupCallback() {
                    @Override
                    public void onListSelected(ListOfListsWrapper list) {
                        BookListFragment.this.list.removeItem(editItem);
                        editItem.setPosition(1);
                        editItem.setSelected(false);
                        editItem.setLock(false);
                        switch (list.getList().getListType()){
                            case Constants.BOOK:
                                list.getList().addItem(editItem);
                                break;
                            case Constants.BOOK_SERIES:
                                BookSeriesItem newSeries = new BookSeriesItem(editItem.getContents());
                                newSeries.setTags(editItem.getTags());
                                editItem.setSeriesTitle(editItem.getContents());
                                newSeries.addBook(editItem);
                                list.getList().addItem(newSeries);
                                break;
                            default:
                                Log.d("ERROR", "onListSelected: Incompatible List types");
                        }

                        adapter.notifyDataSetChanged();
                        mainActivityCallback.OnListChange();
                        exitSelectMode(MenuState.BOOK_LIST_NORMAL);
                    }
                }
        );
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
        adapter.setItemsClickable(false);
    }

    @Override
    public void OnDonePressed() {
        if (mainActivityCallback.getMenuState() == MenuState.BOOK_LIST_EDIT){
            list.removeItem(editItem);
        }
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
        adapter.setItemsClickable(true);
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
            case BOOK_LIST_EDIT:
                binding.bookItemContentInput.setText("");
                binding.newListNumber.setText("1");
                binding.bookItemAdd.setVisibility(View.GONE);
                mainActivityCallback.onMenuContextChanged(MenuState.BOOK_LIST_NORMAL);
                adapter.setItemsClickable(true);
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
        editItem = (BookItem) list.getSelected();
        binding.newListNumber.setText(String.valueOf(editItem.getPosition() + 1));
        binding.bookItemContentInput.setText(editItem.getContents());
        newBookTags = new Tags();
        newBookTags.addTags(editItem.getTags().getList());
        setTagText(newBookTags);
        binding.bookItemAdd.setVisibility(View.VISIBLE);
        mainActivityCallback.onMenuContextChanged(MenuState.BOOK_LIST_EDIT);
        adapter.setItemsClickable(false);
        mainActivityCallback.OnListChange();
    }

    @Override
    public void OnMoveToFrontPressed() {
        BookItem item = (BookItem) list.getSelected();
        frontIndex = 0;
        while (frontIndex < list.getList().size() &&
                ((BookItem)list.getList().get(frontIndex)).isLocked()) frontIndex++;
        if (!item.isLocked() && frontIndex < list.getList().size()) {
            adapter.onItemMove(item.getPosition(), frontIndex);
            list.reassignPositions();
            new Handler(Looper.getMainLooper()).postDelayed(() -> exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
            mainActivityCallback.OnListChange();
        }
    }

    @Override
    public void OnMoveToBackPressed() {
        BookItem item = (BookItem) list.getSelected();
        int backIndex = list.getList().size() - 1;
        while (backIndex > 0 &&
                ((BookItem)list.getList().get(backIndex)).isLocked()) backIndex--;
        if (!item.isLocked() && backIndex > 0) {
            adapter.onItemMove(item.getPosition(), backIndex);
            list.reassignPositions();
            new Handler(Looper.getMainLooper()).postDelayed(()->exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
            mainActivityCallback.OnListChange();
        }
    }

    @Override
    public void OnRandomToFront() {
        BookItem item = (BookItem) list.getRandomItem();
        frontIndex = 0;
        while (frontIndex < list.getList().size() &&
                ((BookItem)list.getList().get(frontIndex)).isLocked()) frontIndex++;
        if (!item.isLocked() && frontIndex < list.getList().size()) {
            adapter.onItemMove(item.getPosition(), frontIndex);
            list.reassignPositions();
            new Handler(Looper.getMainLooper()).postDelayed(() -> exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
            mainActivityCallback.OnListChange();
        }
    }

    @Override
    public void OnRandomTagToFront() {
        frontIndex = 0;
        while (frontIndex < list.getList().size() &&
                ((BookItem)list.getList().get(frontIndex)).isLocked()) frontIndex++;
        mainActivityCallback.getPopupGenerator().showTagsPickerDialog(
                mainActivityCallback.getSettings().getUserTages(),
                true,
                new Popups.PopupCallback() {

                    @Override
                    public void onTagsSelected(ArrayList<String> newTags) {
                        BookItem item = (BookItem) list.getRandomItem(newTags.get(0));
                        if (item == null)
                            Toast.makeText(requireContext(), "No Books with this tag!", Toast.LENGTH_SHORT).show();
                        else if (!item.isLocked() && frontIndex < list.getList().size()){
                            adapter.onItemMove(item.getPosition(), frontIndex);
                            list.reassignPositions();
                            new Handler(Looper.getMainLooper()).postDelayed(() -> exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
                            mainActivityCallback.OnListChange();
                        }
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
                    public void onTagsSelected(ArrayList<String> newTags) {
                        list.taggedItemsToTop(newTags.get(0));
                        list.reassignPositions();
                        new Handler(Looper.getMainLooper()).postDelayed(() -> exitSelectMode(MenuState.BOOK_LIST_NORMAL), 250);
                        mainActivityCallback.OnListChange();
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