package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.BaseListItem;
import com.Monsoon.lists.ListItems.MealDay;
import com.Monsoon.lists.Lists.BasicList;
import com.Monsoon.lists.Lists.ListOfLists;
import com.Monsoon.lists.Lists.MealPlan;
import com.Monsoon.lists.Lists.baseList;
import com.Monsoon.lists.Constants;
import com.Monsoon.lists.R;
import com.Monsoon.lists.enums.Meal;
import com.Monsoon.lists.listeners.onListItemClickedListener;


public class ListOfListsAdapter extends RecyclerView.Adapter<ListOfListsAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(baseList item);
        void onItemLongClick(baseList item);
    }
    ListOfLists data;
    OnItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listNameView;
        RecyclerView listPreview;
        CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            listNameView = itemView.findViewById(R.id.list_name_view);
            listPreview = itemView.findViewById(R.id.list_preview);
            cardView = itemView.findViewById(R.id.card_view);
        }

        public void bind(final baseList item, final OnItemClickListener listener) {
            listNameView.setText(item.getName());

            listPreview.setLayoutManager(new LinearLayoutManager(listPreview.getContext()));
            switch (item.getListType()){
                case Constants.BASIC:
                    BasicListAdapter basicListAdapter = new BasicListAdapter((BasicList) item, new onListItemClickedListener<BaseListItem>() {
                        @Override
                        public void onItemClick(BaseListItem item_b) {
                            listener.onItemClick(item);
                        }

                        @Override
                        public void onItemLongClick(BaseListItem item) {

                        }
                    });
                    listPreview.setAdapter(basicListAdapter);
                    break;
                case Constants.TODO:
                case Constants.QUEUE:
                    PreviewAdapter previewAdapter = new PreviewAdapter(item, new PreviewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick() {
                            listener.onItemClick(item);
                        }
                    });
                    listPreview.setAdapter(previewAdapter);
                    break;
                case Constants.MEAL_PLAN:
                    MealPlanAdapter mealPlanAdapter = new MealPlanAdapter((MealPlan) item, new MealPlanAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(MealDay item) {

                        }

                        @Override
                        public void onItemLongClick(MealDay item) {

                        }

                        @Override
                        public void onDayClick(MealDay item_mp) {
                            listener.onItemClick(item);
                        }

                        @Override
                        public void onMealClick(MealDay item_mp, Meal type) {
                            listener.onItemClick(item);
                        }
                    });
                    listPreview.setAdapter(mealPlanAdapter);
                    break;
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    public ListOfListsAdapter(ListOfLists data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListOfListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListOfListsAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new ListOfListsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_card, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfListsAdapter.ViewHolder holder, int position) {
        baseList currentItem = data.getUserLists().get(position);

        holder.bind(currentItem, listener);
    }

    @Override
    public int getItemCount() {
        return data.getUserLists().size();
    }
}
