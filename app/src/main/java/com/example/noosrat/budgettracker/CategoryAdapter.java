package com.example.noosrat.budgettracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.noosrat.budgettracker.Singleton.SpentSingleton;

import java.util.ArrayList;
import java.util.HashMap;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final Context context;
    HashMap<String, Float> categorySummaryMap;
    ArrayList<String> categories;

    public CategoryAdapter(HashMap<String, Float> categorySummaryMap, Context context) {
        this.categorySummaryMap = categorySummaryMap;
        this.context = context;
        this.categories =  new ArrayList<String>(categorySummaryMap.keySet());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);

        CategoryViewHolder uvh = new CategoryViewHolder(v);
        return uvh;


    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder categoryViewHolder, final int position) {
        categoryViewHolder.txtExpense.setText(SpentSingleton.currencyFormat.format(categorySummaryMap.get(categories.get(position))));
        categoryViewHolder.txtCategoryName.setText(categories.get(position));

        Glide.with(context)
                .load(SpentSingleton.categoryMap.get(categories.get(position)).getIcon())
                .apply(RequestOptions.circleCropTransform())
                .into(categoryViewHolder.imgIcon);

        categoryViewHolder.prbExpense.setMax(SpentSingleton.categoryMap.get(categories.get(position)).getBudget());
        categoryViewHolder.prbExpense.setProgress(categorySummaryMap.get(categories.get(position)).intValue());
        categoryViewHolder.prbExpense.getProgressDrawable().setColorFilter(Color.parseColor(SpentSingleton.categoryMap.get(categories.get(position)).getColour()),android.graphics.PorterDuff.Mode.MULTIPLY);

    }

    @Override
    public int getItemCount() {

        if (categorySummaryMap == null) {
            return 0;
        } else {
            return categorySummaryMap.size();
        }
    }


    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView txtCategoryName;
        private ProgressBar prbExpense;
        private TextView txtExpense;

        CategoryViewHolder(View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.icon);
            txtCategoryName = itemView.findViewById(R.id.name);
            prbExpense = itemView.findViewById(R.id.category_expense);
            txtExpense = itemView.findViewById(R.id.amount);
        }


    }


}