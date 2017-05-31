package zaba.constituent;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.lang.reflect.Array;
import java.util.ArrayList;

import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.Status;

/**
 * Created by ericz on 5/26/2017.
 */

public class TwitterCustomList extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<Tweet> statuses;

    public TwitterCustomList(Activity contextArg, ArrayList<Tweet> statuses) {
        super(contextArg, R.layout.twitter_list);
        this.context = (Activity) getContext();
        this.statuses = statuses;
    }
    public int getCount() {
        return statuses.size();
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder holder;
        if (rowView == null)
        {
            rowView = inflater.inflate(R.layout.twitter_list, null, true);

            holder = new ViewHolder();

            holder.tweetText = (TextView) rowView.findViewById(R.id.tweetText);
            holder.nameText = (TextView) rowView.findViewById(R.id.twitter_name);
            holder.imageView = (ImageView) rowView.findViewById(R.id.twitter_image);
            //holder.retweetWebView = (WebView) rowView.findViewById(R.id.retweetWebView);

            rowView.setTag(holder);
        }

        else
        {
            holder = (ViewHolder) rowView.getTag();
        }

        try {
            holder.nameText.setText(statuses.get(position).getUsername());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        String retweetText = "";
        String tweetText = statuses.get(position).getTweetText();
        if (tweetText.contains("t.co"))
        {
            retweetText = tweetText.substring(tweetText.indexOf("https://"), tweetText.length());
            tweetText = tweetText.substring(0, tweetText.indexOf("https://"));

            //holder.retweetWebView.getSettings().setJavaScriptEnabled(true);
            //holder.retweetWebView.loadUrl(retweetText);


        }




        holder.tweetText.setText(tweetText);
        Picasso.with(getContext()).load(statuses.get(position)
                .getProfileImage())
                .into(holder.imageView);

        Log.v("tag", retweetText);

        Log.v("TwitterCustomList", "getView ran");
        return rowView;
    }

    static class ViewHolder
    {
        ImageView imageView;
        TextView tweetText;
        TextView nameText;
        TextView handleText;
        TextView datePublished;
        WebView retweetWebView;


    }


}
