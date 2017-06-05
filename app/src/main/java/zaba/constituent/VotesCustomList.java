package zaba.constituent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ericz on 6/3/2017.
 */

public class VotesCustomList extends ArrayAdapter {

    private Bill[] bills = new Bill[100];
    private Activity context;
    private String[] twitterURLs;
    public VotesCustomList(Context context, Bill[] bills)
    {

        super(context, R.layout.congressman_list_single);
        this.context = (Activity) context;
        this.bills = bills;

    }

    @Override
    public int getCount()
    {

        return  bills.length;
    }

    @NonNull
    public View getView(final int position, View rowView, ViewGroup parent)
    {

        LayoutInflater inflater = context.getLayoutInflater();

        ViewHolder holder;

        if (rowView == null)
        {
            holder = new ViewHolder();
            rowView = inflater.inflate(R.layout.bill_list_single, null, true);

            holder.billName = (TextView) rowView.findViewById(R.id.billName);
            holder.cardView = (CardView) rowView.findViewById(R.id.billCardView);
            holder.billDescript = (TextView) rowView.findViewById(R.id.billDescription);
            holder.billVote = (TextView) rowView.findViewById(R.id.votePositionText);
            rowView.setTag(holder);

        }

        else {
            holder = (ViewHolder) rowView.getTag();

        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.bill_dialog);

                TextView billQuestion = (TextView) dialog.findViewById(R.id.dialogBillQuestion);
                TextView billDescript = (TextView) dialog.findViewById(R.id.dialogBillDescription);
                TextView billName = (TextView) dialog.findViewById(R.id.dialogBillName);
                TextView voteText = (TextView) dialog.findViewById(R.id.dialogVoteText);

                billQuestion.setText(bills[position].getQuestion());
                billDescript.setText(bills[position].getDescription());
                voteText.setText(bills[position].getVote());

                dialog.show();
            }
        });



        try {
            holder.billName.setText(bills[position].getQuestion());
            holder.billDescript.setText(bills[position].getDescription());
            holder.billVote.setText(String.valueOf(bills[position].getVote()));

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return rowView;
    }




    static class ViewHolder
    {
        CardView cardView;
        TextView billName;
        TextView billDescript;
        TextView billVote;
    }

}
