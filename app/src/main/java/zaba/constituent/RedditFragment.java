package zaba.constituent;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.util.ArrayList;


public class RedditFragment extends Fragment
{

    private RedditFragment.GetRedditArticles getRedditArticles;

    public RedditFragment()
    {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RedditFragment newInstance()
    {
        RedditFragment fragment = new RedditFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getRedditArticles = new RedditFragment.GetRedditArticles(getContext());
        this.getRedditArticles.execute();



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_reddit, container, false);

        return view;



    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {


        AdView adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

    }




public class GetRedditArticles extends AsyncTask<String, String, String>
{

    private boolean internetWorks;
    private String redditURL = "http://reddit.com/r/libertarian+liberal+conservative+republican+esist+progressive+ourpresident.json";
    private String redditJSONString;
    private JSONObject redditObject;
    private JSONArray articlesArray;

    private ArrayList<NewsArticle> newsArticlesArrayList;
    RedditCustomList listAdapter;
    private ListView listView;
    private Context context;
    ProgressDialog pdia;

    public GetRedditArticles(Context contextArg)
    {
        this.context = contextArg;
    }
    protected void onPreExecute()
    {
        pdia = new ProgressDialog(context);
        pdia.setMessage("Loading Articles...");
        pdia.show();
        pdia.setCanceledOnTouchOutside(false);

    }

    @Override
    protected String doInBackground(String... params)
    {


        internetWorks = true;
        try {
            redditJSONString = Jsoup.connect(redditURL).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            internetWorks = false;
        }


        if (internetWorks)
        {
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

                } catch (JSONException e)
                {

                }
            }

            newsArticlesArrayList = new ArrayList<NewsArticle>();

            for (int i = 0; i<newsArticles.length; i++)
            {
                newsArticlesArrayList.add(newsArticles[i]);
            }
        }

        else {

        }





        return null;
    }
    protected void onPostExecute(String string)
    {
        if (internetWorks)
        {
            listAdapter = new
                    RedditCustomList(context, newsArticlesArrayList);

            listView=(ListView) getView().findViewById(R.id.redditListView);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try
                    {
                        Uri uri = Uri.parse("googlechrome://navigate?url=" + newsArticlesArrayList.get(i).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (ActivityNotFoundException e)
                    {
                        // Chrome is probably not installed
                    }
                }
            });
        }

        else
        {
            LinearLayout linearLayout = new LinearLayout(getContext());

        }


        pdia.dismiss();

    }

}

}
