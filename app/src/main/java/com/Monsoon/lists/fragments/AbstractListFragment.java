package com.Monsoon.lists.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.Adapters.AbstractListAdapter;
import com.Monsoon.lists.Adapters.ListsViewHolder;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.enums.MenuState;
import com.Monsoon.lists.listeners.MainActivityCallback;
import com.Monsoon.lists.listeners.OnMenuItemPressed;
import com.Monsoon.lists.util.Popup.Popups;
import com.Monsoon.lists.util.Tags;
import com.Monsoon.lists.util.Util;

import java.util.ArrayList;

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

                onItemMove(fromPosition, toPosition);
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
                onClearView(viewHolder);
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

    protected void setTagText(Tags tags){
        String tagText = Util.getTagsString(tags, true);
        SpannableString spannableString = new SpannableString(tagText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                mainActivityCallback.getPopupGenerator().showTagsPickerDialog(
                        mainActivityCallback.getSettings().getUserTages(),
                        false,
                        new Popups.PopupCallback() {
                            @Override
                            public void onNumberSelected(int num) {
                                // not used
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
                                ResolveTagsSelected(newTags);
                            }

                            @Override
                            public void onDateSelected(String date) {

                            }
                        }
                );
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setTypeface(Typeface.create(ds.getTypeface(), Typeface.BOLD));
                ds.setUnderlineText(false);
                ds.setColor(Color.BLUE);
            }
        };

        int startIndex = tagText.indexOf(Constants.ADD_TAG);
        int endIndex = startIndex + Constants.ADD_TAG.length();

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

       bindTagTextToView(spannableString);

    }

    protected void ResolveTagsSelected(ArrayList<String> newTags){
        // do something when overloaded
    }

    protected void bindTagTextToView(SpannableString spannableString){
        // do something when overloaded
    }

    protected void onItemMove(int fromPosition, int toPosition){
        adapter.onItemMove(fromPosition, toPosition);
    }

    protected void onClearView(@NonNull RecyclerView.ViewHolder viewHolder){
        viewHolder.itemView.setAlpha(1.0f);
        OnDropClearView();
        adapter.notifyDataSetChanged();
    }


}