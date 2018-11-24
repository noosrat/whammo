package com.example.noosrat.budgettracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final Context context;
    ArrayList<Transaction> TransactionArrayList;

    public TransactionAdapter(ArrayList<Transaction> Transaction, Context context) {
        this.TransactionArrayList = Transaction;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_item, viewGroup, false);

        TransactionViewHolder uvh = new TransactionViewHolder(v);
        return uvh;


    }

    @Override
    public void onBindViewHolder(final TransactionViewHolder TransactionViewHolder, final int position) {

        TransactionViewHolder.txtSMS.setText((String) TransactionArrayList.get(position).getMessage());
        TransactionViewHolder.txtAmount.setText((String) TransactionArrayList.get(position).getAmount());
        TransactionViewHolder.txtCard.setText(TransactionArrayList.get(position).getCard() + "CARD");
        TransactionViewHolder.txtTransaction.setText(TransactionArrayList.get(position).getTransactionTypeDisplay());
        TransactionViewHolder.txtRecipient.setText((String) TransactionArrayList.get(position).getRecipient());
    }

    @Override
    public int getItemCount() {

        if (TransactionArrayList == null) {
            return 0;
        } else {
            return TransactionArrayList.size();
        }
    }


    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView txtSMS;
        TextView txtAmount;
        TextView txtCard;
        TextView txtRecipient;
        TextView txtTransaction;

        TransactionViewHolder(View itemView) {
            super(itemView);

            txtSMS = (TextView) itemView.findViewById(R.id.textView);
            txtAmount = (TextView) itemView.findViewById(R.id.amount);
            txtCard = (TextView) itemView.findViewById(R.id.card);
            txtRecipient = (TextView) itemView.findViewById(R.id.recipient);
            txtTransaction = (TextView) itemView.findViewById(R.id.transaction);
        }



    }


}