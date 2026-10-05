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

import com.Monsoon.lists.R;
import com.Monsoon.lists.enums.ListType;

public class listPIckerAdapter extends RecyclerView.Adapter <listPIckerAdapter.ViewHolder> {

    ListType[] data;
    Popups.PopupCallback listener;

    public listPIckerAdapter(Popups.PopupCallback listener){
        data = ListType.values();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        listPIckerAdapter.ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_selection_card, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListType currentItem = data[position];
        holder.bind(currentItem, listener);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textContent;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.list_text);
            cardView = itemView.findViewById(R.id.card_view);
        }

        public void bind(ListType listType, Popups.PopupCallback listener){
            textContent.setText(listType.label);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onListTypeSelected(listType);
                }
            });
            cardView.setForeground(new ColorDrawable(ContextCompat.getColor(itemView.getContext(),
                    listType.color)));
        }
    }
}
