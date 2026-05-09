package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.TodoItem;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.TodoList;
import com.Monsoon.lists.MainActivity;
import com.Monsoon.lists.R;
import com.Monsoon.lists.enums.Frequency;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

   
    public interface OnItemClickListener {
        void onItemClick(TodoItem item);
        void onItemLongClick(TodoItem item);

    }
    TodoList data;
    TodoListAdapter.OnItemClickListener listener;
    boolean isSelectMode;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView todoListView;
        TextView repeatStatusView;
        CheckBox itemCheckBox;
        FrameLayout overlay;

        CheckBox selectButton;
        public ViewHolder(View itemView){
            super(itemView);
            todoListView = itemView.findViewById(R.id.todo_item_text);
            repeatStatusView = itemView.findViewById(R.id.repeat_status_text);
            itemCheckBox = itemView.findViewById(R.id.item_check_box);
            overlay = itemView.findViewById(R.id.todo_overlay);
            selectButton = itemView.findViewById(R.id.selected_radio_todo);


        }
        public void bind(final TodoItem item, final TodoListAdapter.OnItemClickListener listener) {
            if (isSelectMode) {
                overlay.setVisibility(View.VISIBLE);
                itemCheckBox.setVisibility(View.INVISIBLE);
            }
            else {
                itemCheckBox.setVisibility(View.VISIBLE);
                item.setSelected(false);
            }
            selectButton.setChecked(item.isSelected());

            overlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectButton.setChecked(!selectButton.isChecked());
                }
            });
            selectButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    item.setSelected(selectButton.isChecked());
                }
            });

            todoListView.setText(item.getContents());
            itemCheckBox.setChecked(item.isChecked());
            if (item.isChecked()) todoListView.setPaintFlags(todoListView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            else todoListView.setPaintFlags(todoListView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            StringBuilder sb = new StringBuilder();
            sb.append("Repeat: ");
            if (item.isRepeatable()){
                if (item.getFrequency() == Frequency.CUSTOM_DAYS ||
                        item.getFrequency() == Frequency.CUSTOM_WEEKS||
                        item.getFrequency() == Frequency.CUSTOM_MONTHS){
                    sb.append("Every ").append(item.getNumberOfDays()).append(" days");
                } else {
                    sb.append(item.getFrequency().label);
                }
            } else {
                sb.append(" Once");
            }
            repeatStatusView.setText(sb.toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemCheckBox.setChecked(!itemCheckBox.isChecked());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(item);
                    item.setSelected(true);
                    return false;
                }
            });

            itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    data.toggleCheckedOnItem(item);
                    if (item.isChecked()) todoListView.setPaintFlags(todoListView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    else todoListView.setPaintFlags(todoListView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    listener.onItemClick(item);
                }
            });
        }
    }

    public TodoListAdapter(TodoList data, TodoListAdapter.OnItemClickListener listener){
        this.data = data;
        this.listener = listener;
        isSelectMode = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoListAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new TodoListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.todo_list_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem currentItem = (TodoItem) data.getList().get(position);
        holder.bind(currentItem, listener);
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }

    public void changeSelectMode(boolean b){
        this.isSelectMode = b;
    }
}
