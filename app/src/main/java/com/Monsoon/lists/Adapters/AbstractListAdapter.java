package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public abstract class AbstractListAdapter<L, I extends BaseListItem, VH extends ListsViewHolder<I>> extends RecyclerView.Adapter<VH> {

    protected L data;
    protected onListItemClickedListener<I> listener;
    boolean isSelectMode;

    protected abstract I getCurrentItem(int position);

    public AbstractListAdapter(L data, onListItemClickedListener<I> listener){
        this.data = data;
        this.listener = listener;
        this.isSelectMode = false;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        I currentItem = getCurrentItem(position);
        holder.bind(currentItem, listener);
    }
    public void changeSelectMode(boolean b){
        this.isSelectMode = b;
    }

}
