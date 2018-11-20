package com.example.noosrat.budgettracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.SMSViewHolder> {

    private final Context context;
    ArrayList<SMS> SMSArrayList;

    public SMSAdapter(ArrayList<SMS> SMS, Context context) {
        this.SMSArrayList = SMS;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public SMSViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sms_item, viewGroup, false);

        SMSViewHolder uvh = new SMSViewHolder(v);
        return uvh;


    }

    @Override
    public void onBindViewHolder(final SMSViewHolder SMSViewHolder, final int position) {

        SMSViewHolder.txtSMS.setText((String) SMSArrayList.get(position).getMessage());
        SMSViewHolder.txtAmount.setText((String) SMSArrayList.get(position).getAmount());
        SMSViewHolder.txtCard.setText(SMS.CARD_TYPES[SMSArrayList.get(position).getCard()] + "CARD");
        SMSViewHolder.txtTransaction.setText(SMS.TRANSACTION_TYPES[SMSArrayList.get(position).getTransactionType()]);
        SMSViewHolder.txtRecipient.setText((String) SMSArrayList.get(position).getReciepient());
    }

    @Override
    public int getItemCount() {

        if (SMSArrayList == null) {
            return 0;
        } else {
            return SMSArrayList.size();
        }
    }


    public static class SMSViewHolder extends RecyclerView.ViewHolder {

        TextView txtSMS;
        TextView txtAmount;
        TextView txtCard;
        TextView txtRecipient;
        TextView txtTransaction;

        SMSViewHolder(View itemView) {
            super(itemView);

            txtSMS = (TextView) itemView.findViewById(R.id.textView);
            txtAmount = (TextView) itemView.findViewById(R.id.amount);
            txtCard = (TextView) itemView.findViewById(R.id.card);
            txtRecipient = (TextView) itemView.findViewById(R.id.recipient);
            txtTransaction = (TextView) itemView.findViewById(R.id.transaction);
        }



    }


}