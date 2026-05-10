package com.Monsoon.lists.Adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.Monsoon.lists.ListItems.MealDay;
import com.Monsoon.lists.Lists.MealPlan;
import com.Monsoon.lists.R;
import com.Monsoon.lists.enums.Meal;
import com.Monsoon.lists.listeners.onListItemClickedListener;


import java.time.format.DateTimeFormatter;

public class MealPlanAdapter extends AbstractListAdapter<MealPlan, MealDay, MealPlanAdapter.ViewHolder> {

    //TODO: add on long click listener
    public interface OnItemClickListener extends onListItemClickedListener<MealDay> {
        void onDayClick(MealDay item);
        void onMealClick(MealDay item, Meal type);
    }

    public class ViewHolder extends ListsViewHolder<MealDay>{
        TextView breakfastText, lunchText, dinnerText;
        public ViewHolder(View itemView){
            super(itemView);
            textContent = itemView.findViewById(R.id.date_text);
            breakfastText = itemView.findViewById(R.id.breakfast_text);
            lunchText = itemView.findViewById(R.id.lunch_text);
            dinnerText = itemView.findViewById(R.id.dinner_text);
        }

        @Override
        public void bind(MealDay item, onListItemClickedListener<MealDay> listener) {
            StringBuilder sb = new StringBuilder();
            sb.append(item.getDayOfWeek().label).append(": ").append(item.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            textContent.setText(sb.toString());
            sb = new StringBuilder();
            sb.append(Meal.BREAKFAST.label).append(": ").append(item.getMeals()[Meal.BREAKFAST.index]);
            breakfastText.setText(sb.toString());
            sb = new StringBuilder();
            sb.append(Meal.LUNCH.label).append(": ").append(item.getMeals()[Meal.LUNCH.index]);
            lunchText.setText(sb.toString());
            sb = new StringBuilder();
            sb.append(Meal.DINNER.label).append(": ").append(item.getMeals()[Meal.DINNER.index]);
            dinnerText.setText(sb.toString());

            textContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MealPlanAdapter.OnItemClickListener)listener).onDayClick(item);
                }
            });
            breakfastText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MealPlanAdapter.OnItemClickListener)listener).onMealClick(item, Meal.BREAKFAST);
                }
            });

            lunchText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MealPlanAdapter.OnItemClickListener)listener).onMealClick(item, Meal.LUNCH);
                }
            });
            dinnerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MealPlanAdapter.OnItemClickListener)listener).onMealClick(item, Meal.DINNER);
                }
            });
        }

    }

    public MealPlanAdapter(MealPlan data, MealPlanAdapter.OnItemClickListener listener){
        super(data, listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MealPlanAdapter.ViewHolder viewHolder = null;
        Log.d(TAG, "Rv_active_Child.." + viewType);

        viewHolder = new MealPlanAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.meal_day_item, parent, false));
        return viewHolder;
    }

    @Override
    protected MealDay getCurrentItem(int position) {
        return (MealDay) data.getList().get(position);
    }

    @Override
    public int getItemCount() {
        return data.getList().size();
    }

}
