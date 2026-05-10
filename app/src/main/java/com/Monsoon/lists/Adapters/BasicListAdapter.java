package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.R;
import com.Monsoon.lists.listeners.onListItemClickedListener;

public class BasicListAdapter extends AbstractListAdapter<BasicList, BaseListItem, BasicListAdapter.ViewHolder> {


    public class ViewHolder extends ListsViewHolder<BaseListItem> {
        public ViewHolder(View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.basic_item_text);
            overlay = itemView.findViewById(R.id.basic_overlay);
            selectButton = itemView.findViewById(R.id.selected_radio);

        }

        @Override
        public void bind(BaseListItem item, onListItemClickedListener<BaseListItem> listener) {
            setUniversalViewBinding(item, listener, isSelectMode);
            textContent.setText(item.getContents());
        }
    }


    public BasicListAdapter(BasicList data, onListItemClickedListener<BaseListItem> listener){
        super(data, listener);
    }

    @Override
    protected BaseListItem getCurrentItem(int position) {
        return data.getList().get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BasicListAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new BasicListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.basic_list_item, parent, false));
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }

}
