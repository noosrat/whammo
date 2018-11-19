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
    ArrayList<String> SMSArrayList;

    public SMSAdapter(ArrayList<String> SMS, Context context) {
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

        SMSViewHolder.txtSMS.setText((String) SMSArrayList.get(position));
        //SMSViewHolder.txtPartnerName.setText((String) SMSArrayList.get(position).getPartnerName());


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
        TextView txtPartnerName;

        SMSViewHolder(View itemView) {
            super(itemView);

            txtSMS = (TextView) itemView.findViewById(R.id.textView);
            //txtSMSname = (TextView) itemView.findViewById(R.id.textView7);
            //txtPartnerName = (TextView) itemView.findViewById(R.id.textView6);

        }



    }


}