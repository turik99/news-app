package zaba.constituent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;


public class RedditFragment extends Fragment {

    private RedditFragment.GetRedditArticles getRedditArticles;


    public RedditFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RedditFragment newInstance() {
        RedditFragment fragment = new RedditFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getRedditArticles = new RedditFragment.GetRedditArticles(getContext());
        this.getRedditArticles.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reddit, container, false);
    }




public class GetRedditArticles extends AsyncTask<String, String, String>
{

    private String redditURL = "http://reddit.com/r/libertarian+democrat+liberal+conservative+republican.json";
    private String redditJSONString;
    private JSONObject redditObject;
    private JSONArray articlesArray;

    private ArrayList newsArticlesArrayList;

    RedditCustomList listAdapter;

    private ListView listView;

    private Context context;

    ProgressDialog pdia;

    public GetRedditArticles(Context context1)
    {
        this.context = context1;
    }
    protected void onPreExecute()
    {
        pdia = new ProgressDialog(context);
        pdia.setMessage("Loading Articles...");
        pdia.show();

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            redditJSONString = Jsoup.connect(redditURL).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            redditObject = new JSONObject(redditJSONString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            articlesArray = redditObject.getJSONObject("data").getJSONArray("children");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        int articlesIndex = articlesArray.length();

        NewsArticle[] newsArticles = new NewsArticle[articlesIndex];


        for (int i = 0; i<articlesIndex; i++)
        {
            newsArticles[i] = new NewsArticle();
            try {
                newsArticles[i].setTitle(articlesArray.getJSONObject(i)
                        .getJSONObject("data")
                        .get("title")
                        .toString());

                newsArticles[i].setUrl(articlesArray.getJSONObject(i)
                        .getJSONObject("data")
                        .get("url").toString());

                newsArticles[i].setImageURL(articlesArray.getJSONObject(i)
                        .getJSONObject("data")
                        .get("thumbnail").toString());

                newsArticles[i].setUpvotes(articlesArray.getJSONObject(i)
                        .getJSONObject("data")
                        .get("ups").toString());

                newsArticles[i].setSource(articlesArray.getJSONObject(i)
                        .getJSONObject("data")
                        .get("subreddit_name_prefixed").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        newsArticlesArrayList = new ArrayList<NewsArticle>();

        for (int i = 0; i<newsArticles.length; i++)
        {
            newsArticlesArrayList.add(newsArticles[i]);
        }






        return null;
    }
    protected void onPostExecute(String string)
    {
            listAdapter = new
                    RedditCustomList(getContext(), newsArticlesArrayList);

        listView=(ListView) getView().findViewById(R.id.redditListView);

        listView.setAdapter(listAdapter);

        pdia.dismiss();

    }

}

}
