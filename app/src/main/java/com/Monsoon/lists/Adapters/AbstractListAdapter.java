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
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractListAdapter<L, I extends BaseListItem, VH extends ListsViewHolder<I>> extends RecyclerView.Adapter<VH> {

    protected L data;
    protected onListItemClickedListener<I> listener;
    protected boolean isSelectMode;
    protected boolean itemsClickable;

    protected abstract I getCurrentItem(int position);

    public AbstractListAdapter(L data, onListItemClickedListener<I> listener){
        this.data = data;
        this.listener = listener;
        this.isSelectMode = false;
        this.itemsClickable = true;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        I currentItem = getCurrentItem(position);
        holder.bind(currentItem, listener);
    }
    public void changeSelectMode(boolean b){
        this.isSelectMode = b;
    }

    public void onItemMove(int fromPosition, int toPosition){
        if (data instanceof baseList){
            if (fromPosition < toPosition){
                for (int i = fromPosition; i < toPosition; i++){
                    Collections.swap(((baseList) data).getList(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--){
                    Collections.swap(((baseList) data).getList(), i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }

    }

    public void setItemsClickable(boolean clickable){
        if (data instanceof baseList){
            for (BaseListItem item : ((baseList) data).getList()){
                item.setClickable(clickable);
            }
        }
    }

}
