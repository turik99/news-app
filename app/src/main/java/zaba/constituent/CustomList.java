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

    private ArrayList<NewsFragment.NewsArticle> newsArticles;

    public CustomList(Context context, ArrayList newsArticlesArg) {
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
        rowView = inflater.inflate(R.layout.list_single, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.text);

        txtTitle.setText(newsArticles.get(position).getTitle());

        TextView biasText = (TextView) rowView.findViewById(R.id.biasTextView);


        TextView sourceText = (TextView) rowView.findViewById(R.id.newsSourceTextView);
        sourceText.setText(newsArticles.get(position).getSource());


        TextView descriptionText = (TextView) rowView.findViewById(R.id.textView2);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.newsThumbnail);

        ImageView sourceLogo = (ImageView) rowView.findViewById(R.id.newsSourceImageView);



        String source = newsArticles.get(position).getSource();
        switch (source){
            case "Associated Press":
                biasText.setText(" | minimal bias");
                sourceLogo.setImageResource(R.drawable.ap_logo);
                break;

            case "Bloomberg":
                biasText.setText(" | minimal bias");
                sourceLogo.setImageResource(R.drawable.bloomberg_logo);
                break;

            case "Financial Times":
                biasText.setText(" | conservative bias");
                sourceLogo.setImageResource(R.drawable.ft_logo);
                break;

            case "Reuters":
                biasText.setText(" | minimal bias");
                sourceLogo.setImageResource(R.drawable.reuterslogo);
                break;

            case "Wall Street Journal":
                biasText.setText(" | conservative bias");
                sourceLogo.setImageResource(R.drawable.wsj_logo);
                break;

            case "New York Times":
                biasText.setText(" | liberal bias");
                sourceLogo.setImageResource(R.drawable.nyt_logo);
                break;

            case "Washington Post":
                biasText.setText(" | slight liberal bias");
                sourceLogo.setImageResource(R.drawable.wapo_logo);
                break;


        }

        if (newsArticles.get(position).getDescription().length()>40)
        {
            descriptionText.setText((String) newsArticles.get(position).getDescription());


            Picasso.with(getContext()).load((String) newsArticles.get(position).getImageURL()).into(imageView);
        }
        if(newsArticles.get(position).getDescription()==null)
        {
            descriptionText.setText("");
            imageView.setImageResource(R.drawable.noimage);
        }
        else
        {
            descriptionText.setText("");
            imageView.setImageResource(R.drawable.noimage);
        }



        Log.v("custom list", "all this shit ran");

        return rowView;


    }



}






