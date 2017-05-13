package zaba.constituent;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RedditCustomList extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<NewsArticle> newsArticles;

    public RedditCustomList(Context context, ArrayList newsArticlesArg){
        super(context, R.layout.list_single);
        this.context = (Activity) getContext();
        this.newsArticles = newsArticlesArg;

        Log.v("customlist custructor", " this shit went trhough");


    }
    @Override
    public int getCount() {
        return newsArticles.size();
    }

    @NonNull
    public View getView(int position, View rowView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();


        ViewHolder holder;

        if (rowView == null)
        {
            rowView = inflater.inflate(R.layout.reddit_list_single, null, true);

            holder  = new ViewHolder();
            holder.titleText = (TextView) rowView.findViewById(R.id.text);
            holder.subreddit = (TextView) rowView.findViewById(R.id.subredditTextView);
            rowView.setTag(holder);
        }


        else {
            holder = (ViewHolder) rowView.getTag();
        }



        holder.titleText.setText(newsArticles.get(position).getTitle());
        holder.subreddit.setText(newsArticles.get(position).getSource());

        holder.thumbnail = (ImageView) rowView.findViewById(R.id.redditThumbnail);
        Log.v("custom list", "all this shit ran");


        Picasso.with(getContext()).load(newsArticles.get(position).getImageURL()).into(holder.thumbnail);
        return rowView;


    }

    static class ViewHolder
    {
        TextView titleText;
        TextView subreddit;
        ImageView thumbnail;

    }


}







