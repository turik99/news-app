package zaba.constituent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
    public int getCount()
    {
        return newsArticles.size();
    }

    @NonNull
    public View getView(int position, View rowView, ViewGroup parent)
    {

        LayoutInflater inflater = context.getLayoutInflater();


        ViewHolder holder;

        if (rowView == null)
        {
            rowView = inflater.inflate(R.layout.reddit_list_single, null, true);

            holder  = new ViewHolder();
            holder.titleText = (TextView) rowView.findViewById(R.id.text);
            holder.subreddit = (TextView) rowView.findViewById(R.id.subredditTextView);
            holder.biasImage = (ImageView) rowView.findViewById(R.id.biasImageView);


            rowView.setTag(holder);
        }


        else {
            holder = (ViewHolder) rowView.getTag();
        }



        holder.titleText.setText(newsArticles.get(position).getTitle());
        holder.subreddit.setText(newsArticles.get(position).getSource());



        String subreddit = holder.subreddit.getText().toString();

        if (subreddit.equals("r/Libertarian") ) {
            holder.subreddit.setTextColor(Color.parseColor("#ffc107"));
            holder.biasImage.setImageResource(R.drawable.liberty_wing);
        }

        if(subreddit.equals("r/Liberal")) {
            holder.subreddit.setTextColor(Color.BLUE);
            holder.biasImage.setImageResource(R.drawable.left_wing);
        }

        if (subreddit.equals("r/Conservative") ) {
            holder.subreddit.setTextColor(Color.RED);
            holder.biasImage.setImageResource(R.drawable.right_wing);
        }

        if (subreddit.equals("r/progressive") ) {
            holder.subreddit.setTextColor(Color.BLUE);
            holder.biasImage.setImageResource(R.drawable.left_wing);
        }

        if(subreddit.equals("r/Republican")) {
            holder.subreddit.setTextColor(Color.RED);
            holder.biasImage.setImageResource(R.drawable.right_wing);
        }

        if(subreddit.equals("r/Democrat")) {
            holder.subreddit.setTextColor(Color.BLUE);
            holder.biasImage.setImageResource(R.drawable.left_wing);
        }
        if(subreddit.equals("r/esist")) {
            holder.subreddit.setTextColor(Color.BLUE);
            holder.biasImage.setImageResource(R.drawable.left_wing);
        }

        if(subreddit.equals("r/The_Donald")) {
            holder.subreddit.setTextColor(Color.RED);
            holder.biasImage.setImageResource(R.drawable.right_wing);
        }

        if(subreddit.equals("r/OurPresident")) {
            holder.subreddit.setTextColor(Color.BLUE);
            holder.biasImage.setImageResource(R.drawable.left_wing);
        }

        Log.v("custom list", "all this shit ran");


        holder.thumbnail = (ImageView) rowView.findViewById(R.id.redditThumbnail);

        Picasso.with(getContext()).load(newsArticles.get(position).getImageURL()).into(holder.thumbnail);
        return rowView;


    }

    static class ViewHolder
    {
        TextView titleText;
        TextView subreddit;
        ImageView thumbnail;
        ImageView biasImage;
    }


}







