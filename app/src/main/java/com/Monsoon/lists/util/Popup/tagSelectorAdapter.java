package com.Monsoon.lists.util.Popup;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.Adapters.BookListAdapter;
import com.Monsoon.lists.R;
import com.Monsoon.lists.enums.ListType;
import com.Monsoon.lists.util.Tags;

import java.util.ArrayList;

public class tagSelectorAdapter extends RecyclerView.Adapter <tagSelectorAdapter.ViewHolder> {

    Tags data;
    Popups.PopupCallback listener;

    public tagSelectorAdapter(Tags tags, Popups.PopupCallback listener){
        data = tags;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        tagSelectorAdapter.ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_selection_card, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String currentItem = data.getList().get(position);
        boolean selected = data.getSelectedTags().get(position);
        holder.bind(currentItem, position, selected, listener);
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textContent;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.list_text);
            cardView = itemView.findViewById(R.id.card_view);
        }

        public void bind(String tag, int pos, boolean selected, Popups.PopupCallback listener){
            textContent.setText(tag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onTagClicked(pos);
                    notifyDataSetChanged();
                }
            });
            int color;
            if (selected){
                color = R.color.transparent_gray;
            } else {
                color = R.color.clear;
            }
            cardView.setForeground(new ColorDrawable(ContextCompat.getColor(itemView.getContext(),
                    color)));
        }
    }
}
