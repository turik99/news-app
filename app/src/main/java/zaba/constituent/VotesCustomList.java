package zaba.constituent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
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
//
////                try {
////
////                    GetBillStuff getBillStuff = new GetBillStuff();
////                    String responseString = getBillStuff.execute().get();
////                    JSONObject jsonObject = new JSONObject(responseString);
////                    JSONArray jsonArray = jsonObject.getJSONArray("results");
////
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//
//
//
//
//                final Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.bill_dialog);
//
//                TextView billQuestion = (TextView) dialog.findViewById(R.id.dialogBillQuestion);
//                TextView billDescript = (TextView) dialog.findViewById(R.id.dialogBillDescription);
//                TextView billName = (TextView) dialog.findViewById(R.id.dialogBillName);
//                TextView voteText = (TextView) dialog.findViewById(R.id.dialogVoteText);
//
//
//
//                try
//                {
//                    billQuestion.setText(bills[position].getQuestion());
//
//                    billName.setText(bills[position].getName());
//                    billDescript.setText(bills[position].getDescription());
//
//                    voteText.setText(bills[position].getVote());
//
//
//                }
//                catch (Exception e)
//                {
//
//                }
//
//                dialog.show();
            }
        });



        try {

            Log.v("BillStuff", bills[position].getQuestion());
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



    class GetBillStuff extends AsyncTask<String, String, String>

    {
        @Override
        protected String doInBackground(String... strings) {


            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet billGet = new HttpGet();
                URI billURI = new URI("https://api.propublica.org/congress/v1/115/bills/hr1301.jsonhttps://api.propublica.org/congress/v1/115/bills/hr1301.json");
                billGet.setURI(billURI);
                billGet.setHeader("X-API-Key", "jUr5eCGK2h5opZ9xnlEAB8pU4v6v8h4g1rWmxO6f");
                HttpResponse response = httpclient.execute(billGet);
                String responseString = new BasicResponseHandler().handleResponse(response);

                return responseString;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }


        }
    }





}
