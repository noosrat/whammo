package com.example.noosrat.budgettracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final Context context;
    ArrayList<Transaction> transactionArrayList;

    public TransactionAdapter(ArrayList<Transaction> Transaction, Context context) {
        this.transactionArrayList = Transaction;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_item, viewGroup, false);
        TransactionViewHolder uvh = new TransactionViewHolder(v);
        return uvh;


    }

    @Override
    public void onBindViewHolder(final TransactionViewHolder transactionViewHolder, final int position) {
        transactionViewHolder.tvAmount.setText((String) transactionArrayList.get(position).getAmount());
        transactionViewHolder.tvDate.setText((String) transactionArrayList.get(position).getDate().toString());
        transactionViewHolder.tvDescription.setText((String) transactionArrayList.get(position).getMerchant().getName());
        Glide.with(context)
                .load(transactionArrayList.get(position).getMerchant().getIcon())
                .apply(RequestOptions.circleCropTransform())
                .into(transactionViewHolder.imgMerchant);
    }

    @Override
    public int getItemCount() {

        if (transactionArrayList == null) {
            return 0;
        } else {
            return transactionArrayList.size();
        }
    }


    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescription;
        private TextView tvAmount;
        private TextView tvDate;
        private ImageView imgMerchant;

        TransactionViewHolder(View itemView) {
            super(itemView);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            imgMerchant = (ImageView) itemView.findViewById(R.id.imgMerchant);
        }


    }


}