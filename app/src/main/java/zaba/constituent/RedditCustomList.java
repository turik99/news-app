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

    private ArrayList<RedditFragment.NewsArticle> newsArticles;

    public RedditCustomList(Context context, ArrayList newsArticlesArg) {
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
        rowView = inflater.inflate(R.layout.reddit_list_single, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.text);

        txtTitle.setText(newsArticles.get(position).getTitle());




        TextView subreddit = (TextView) rowView.findViewById(R.id.subredditTextView);
        subreddit.setText(newsArticles.get(position).getSource());




        ImageView imageView = (ImageView) rowView.findViewById(R.id.newsThumbnail);







        Picasso.with(getContext()).load((String) newsArticles.get(position).getImageURL()).into(imageView);




        Log.v("custom list", "all this shit ran");

        return rowView;


    }



}






