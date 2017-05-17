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

public class CustomList extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<NewsArticle> newsArticles;

    public CustomList(Context context, ArrayList newsArticlesArg)
    {
        super(context, R.layout.list_single);
        this.context = (Activity) getContext();
        this.newsArticles = newsArticlesArg;

        Log.v("customlist custructor", " the list was created");
    }
    @Override
    public int getCount() {
        return newsArticles.size();
    }

    @NonNull
    public View getView(int position, View rowView, ViewGroup parent)
    {

        LayoutInflater inflater = context.getLayoutInflater();


        ViewHolder holder;

        if (rowView == null)
        {
            rowView = inflater.inflate(R.layout.list_single, null, true);

            holder = new ViewHolder();
            holder.titleText = (TextView) rowView.findViewById(R.id.text);
            holder.biasText = (TextView) rowView.findViewById(R.id.biasTextView);
            holder.sourceText = (TextView) rowView.findViewById(R.id.newsSourceTextView);
            holder.sourceImage =(ImageView) rowView.findViewById(R.id.newsSourceImageView);
            holder.thumbnail = (ImageView) rowView.findViewById(R.id.newsThumbnail);
            holder.description = (TextView) rowView.findViewById(R.id.textView2);

            rowView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.titleText.setText(newsArticles.get(position).getTitle());
        holder.sourceText.setText(newsArticles.get(position).getSource());


        String source = newsArticles.get(position).getSource();
        switch (source){
            case "Associated Press":
                holder.biasText.setText("| minimal bias");
                holder.sourceImage.setImageResource(R.drawable.ap_logo);
                break;

            case "Bloomberg":
                holder.biasText.setText("| minimal bias");
                holder.sourceImage.setImageResource(R.drawable.bloomberg_logo);
                break;

            case "Financial Times":
                holder.biasText.setText("| conservative bias");
                holder.sourceImage.setImageResource(R.drawable.ft_logo);
                break;

            case "Reuters":
                holder.biasText.setText("| minimal bias");
                holder.sourceImage.setImageResource(R.drawable.reuterslogo);
                break;

            case "Wall Street Journal":
                holder.biasText.setText("| slight conservative bias");
                holder.sourceImage.setImageResource(R.drawable.wsj_logo);
                break;

            case "New York Times":
                holder.biasText.setText("| liberal bias");
                holder.sourceImage.setImageResource(R.drawable.nyt_logo);
                break;

            case "Washington Post":
                holder.biasText.setText("| liberal bias");
                holder.sourceImage.setImageResource(R.drawable.wapo_logo);
                break;

            case "Breitbart":
                holder.biasText.setText("| far right bias");
                holder.sourceImage.setImageResource(R.drawable.breitbart);
                break;
            case "HuffPost":
                holder.biasText.setText("| far left bias");
                holder.sourceImage.setImageResource(R.drawable.huffpost);
                break;


        }

        String description = newsArticles.get(position).getDescription();


        if (description != "null")
        {
            if (description.length() >= 220)
            {
                holder.description.setText(description.substring(0, 220)+"...");

                if (!newsArticles.get(position).getImageURL().isEmpty())
                {
                    Picasso.with(getContext()).load(newsArticles.get(position).getImageURL()).into(holder.thumbnail);

                }

            }

            else
            {
                if (description.length()<80)
                {
                    holder.description.setText(description);
                    holder.thumbnail.setImageResource(R.drawable.noimage);
                }
                else {
                    holder.description.setText(description);

                    if (!newsArticles.get(position).getImageURL().isEmpty())
                    {
                        Picasso.with(getContext()).load(newsArticles.get(position).getImageURL()).into(holder.thumbnail);

                    }
                }



            }
        }


        else {
            holder.description.setText(" ");
        }



        Log.v("custom list", "getView ran on time");

        return rowView;


    }
    static class ViewHolder
    {
        TextView biasText;
        TextView sourceText;
        TextView titleText;
        ImageView sourceImage;
        ImageView thumbnail;
        TextView description;
    }



}






