package zaba.constituent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by ericz on 5/31/2017.
 */

public class CongressCustomList extends ArrayAdapter<String> {

    private Congressman[] congressmen;
    private ArrayList<Congressman> members;
    private Activity context;
    private String[] twitterURLs;
    public CongressCustomList(Context context, Congressman[] congressmen)
    {


        super(context, R.layout.congressman_list_single);
        this.context = (Activity) getContext();
        this.members = members;
        this.congressmen = congressmen;


    }

    @Override
    public int getCount()
    {

        return congressmen.length;
    }

    @NonNull
    public View getView(final int position, View rowView, ViewGroup parent)
    {

        LayoutInflater inflater = context.getLayoutInflater();

        ViewHolder holder;

        if (rowView ==null)
        {
            holder = new ViewHolder();
            rowView = inflater.inflate(R.layout.congressman_list_single, null, true);

            holder.nameText = (TextView) rowView.findViewById(R.id.congressmanName);
            holder.partyText = (TextView) rowView.findViewById(R.id.congressmanParty);
            holder.stateText = (TextView) rowView.findViewById(R.id.congressmanState);
            holder.cardView = (CardView) rowView.findViewById(R.id.congressListCardView);
            //holder.profileImage = (ImageView) rowView.findViewById(R.id.congressmanImage);

            rowView.setTag(holder);

        }

        else {
            holder = (ViewHolder) rowView.getTag();

        }

        try {
            holder.nameText.setText(congressmen[position].getName());
            holder.partyText.setText(congressmen[position].getParty());
            holder.stateText.setText(congressmen[position].getState());

//            Picasso.with(context).load(new ImageURL(congressmen[position]
//                    .getTwitter())
//                    .execute()
//                    .get()).into(holder.profileImage);


        }
        catch (Exception e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CongressmanActivity.class);
                intent.putExtra("congressman", congressmen[position].getApiThing());
                intent.putExtra("district", congressmen[position].getState());
                getContext().startActivity(intent);

            }

        });

        return rowView;
    }











    static class ViewHolder
    {
        CardView cardView;
        TextView nameText;
        TextView partyText;
        TextView stateText;

        ImageView profileImage;
    }









}
