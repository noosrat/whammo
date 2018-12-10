package com.example.noosrat.budgettracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
    ArrayList<FeedItem> transactionArrayList;

    public TransactionAdapter(ArrayList<FeedItem> FeedItem, Context context) {
        this.transactionArrayList = FeedItem;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return transactionArrayList.get(position).itemViewType;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v;
        if (i == FeedItem.TYPE_ITEM_TRANSACTION)
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_item, viewGroup, false);
        else
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.date_item, viewGroup, false);
        TransactionViewHolder uvh = new TransactionViewHolder(v);
        return uvh;


    }

    @Override
    public void onBindViewHolder(final TransactionViewHolder transactionViewHolder, final int position) {
        if (getItemViewType(position) == FeedItem.TYPE_ITEM_TRANSACTION){
            transactionViewHolder.tvAmount.setText((String) transactionArrayList.get(position).getTransaction().getAmount());
            transactionViewHolder.tvDate.setText((String) transactionArrayList.get(position).getTransaction().getDate().toString());
            transactionViewHolder.tvDescription.setText((String) transactionArrayList.get(position).getTransaction().getMerchant().getName());
            Glide.with(context)
                    .load(transactionArrayList.get(position).getTransaction().getMerchant().getIcon())
                    .apply(RequestOptions.circleCropTransform())
                    .into(transactionViewHolder.imgMerchant);

            transactionViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent i = new Intent(context, TransactionActivity.class);

                    i.putExtra("amount",(String) transactionArrayList.get(position).getTransaction().getAmount());
                    i.putExtra("date",(String) transactionArrayList.get(position).getTransaction().getDate().toString());
                    i.putExtra("recipient",(String) transactionArrayList.get(position).getTransaction().getMerchant().getName());
                    i.putExtra("imageURL", (String) transactionArrayList.get(position).getTransaction().getMerchant().getIcon());

                    context.startActivity(i);                }
            });
        }
        else{
            transactionViewHolder.tvDate.setText(transactionArrayList.get(position).getDisplayTimePeriodGroup());
        }
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
        public CardView cardView;
        private TextView tvDescription;
        private TextView tvAmount;
        private TextView tvDate;
        private ImageView imgMerchant;

        TransactionViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            imgMerchant = (ImageView) itemView.findViewById(R.id.imgMerchant);
        }


    }


}