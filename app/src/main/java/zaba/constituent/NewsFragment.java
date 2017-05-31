package zaba.constituent;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;


public class NewsFragment extends Fragment {
    private GetNewsArticles getNewsArticles;

    CustomList listAdapter;
    Context context;

    public NewsFragment() {
        // Required empty public constructor


    }


    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getNewsArticles = new GetNewsArticles(getContext());
            this.getNewsArticles.execute();

        } catch (Exception e) {
            e.printStackTrace();
            //not raw.

            Toast.makeText(getContext(), "The articles couldn't load, Check your internet connection", Toast.LENGTH_SHORT);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_news, container, false);


        return view;


    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        AdView adView = (AdView) view.findViewById(R.id.adViewNews);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

        Log.v("news onViewCreated", "the shit ran ");

    }


    public class GetNewsArticles extends AsyncTask<String, String, String> {

        private boolean codeWorked;

        private Context context;

        //Json data is held in these private strings
        private String apJsonString;
        private String bloombergJsonString;
        private String financialtimesJsonString;
        private String reutersJsonString;
        private String wallStreetJournalJsonString;
        private String newYorkTimesJsonString;
        private String washingtonPostJsonString;

        //these strings contain each of the objects from the strings produced by all of the sources
        private JSONObject apJsonObject;
        private JSONObject bloombergJsonObject;
        private JSONObject financialtimesJsonObject;
        private JSONObject reutersJsonObject;
        private JSONObject wallStreetJournalJsonObject;
        private JSONObject newYorkTimesJsonObject;
        private JSONObject washingtonPostJsonObject;

        //This json array holds each of the articles from all of the sources used
        private JSONArray newsArticles = new JSONArray();

        //This array carrys the extracted JSON data for the title of the articles
        private ArrayList<String> title = new ArrayList<String>();

        //this contains the json data for the URLs of the articles
        private ArrayList<String> url = new ArrayList<String>();

        private ArrayList<String> imageUrl = new ArrayList<String>();


        private ArrayList<String> descriptions = new ArrayList<String>();
        private ArrayList sourcesList = new ArrayList<String>();
        private ArrayList topArticle = new ArrayList<String>();
        private ArrayList<String> datePublished = new ArrayList<String>();


        //The listview that is populated by the data
        private ListView listView;

        //the Bitmap array from the thumbnails that will be put in the listview
        private Bitmap[] thumbnails;

        //the urls to each news source is held in these strings


        private String apArticleURL = "https://newsapi.org/v1/articles?source=associated-press&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String bloombergArticleURL = "https://newsapi.org/v1/articles?source=bloomberg&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String financialtimesArticleURL = "https://newsapi.org/v1/articles?source=financial-times&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String reutersArticleURL = "https://newsapi.org/v1/articles?source=reuters&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String wallStreetJournalArticleURL = "https://newsapi.org/v1/articles?source=the-wall-street-journal&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String newYorkTimesArticleURl = "https://newsapi.org/v1/articles?source=the-new-york-times&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String washingtonPostArticleURL = "https://newsapi.org/v1/articles?source=the-washington-post&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";

        private JSONArray apArticleJsonArray;

        private JSONArray bloombergJsonArray;
        private JSONArray newYorkTimesJsonArray;
        private JSONArray reutersJsonArray;
        private JSONArray wallStreetJournalJsonArray;
        private JSONArray washingtonPostJsonArray;
        private ArrayList<NewsArticle> newsArticlesArrayList;


        private SharedPreferences sharedPreferences = getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
        private SharedPreferences.Editor editor = sharedPreferences.edit();

        public GetNewsArticles(Context contextArg) {
            this.context = contextArg;
        }

        ProgressDialog pdia;

        protected void onPreExecute() {
            pdia = new ProgressDialog(context);
            pdia.setMessage("Loading Articles...");
            pdia.show();
            pdia.setCanceledOnTouchOutside(false);


        }

        @Override
        protected String doInBackground(String... params) {
            //--------------------------------------------------------------------------------------
            //Getting the String to be converted and used as a JSON object


            try {


                if (sharedPreferences.getBoolean("ap", true)) {
                    apJsonString = Jsoup.connect(apArticleURL).ignoreContentType(true).execute().body();
                    apJsonObject = new JSONObject(apJsonString);
                    apArticleJsonArray = apJsonObject.getJSONArray("articles");

                    for (int i = 0; i < apArticleJsonArray.length(); i++) {
                        newsArticles.put(apArticleJsonArray.get(i));
                        sourcesList.add("Associated Press");
                    }
                }
                if (sharedPreferences.getBoolean("bloomberg", true)) {
                    bloombergJsonString = Jsoup.connect(bloombergArticleURL).ignoreContentType(true).execute().body();
                    bloombergJsonObject = new JSONObject(bloombergJsonString);
                    bloombergJsonArray = bloombergJsonObject.getJSONArray("articles");

                    for (int i = 0; i < bloombergJsonArray.length(); i++) {
                        newsArticles.put(bloombergJsonArray.get(i));
                        sourcesList.add("Bloomberg");

                    }


                }
                if (sharedPreferences.getBoolean("reuters", true)) {
                    reutersJsonString = Jsoup.connect(reutersArticleURL).ignoreContentType(true).execute().body();
                    reutersJsonObject = new JSONObject(reutersJsonString);
                    reutersJsonArray = reutersJsonObject.getJSONArray("articles");

                    for (int i = 0; i < reutersJsonArray.length(); i++) {
                        newsArticles.put(reutersJsonArray.get(i));
                        sourcesList.add("Reuters");

                    }
                }
                if (sharedPreferences.getBoolean("wsj", true)) {
                    wallStreetJournalJsonString = Jsoup.connect(wallStreetJournalArticleURL).ignoreContentType(true).execute().body();
                    wallStreetJournalJsonObject = new JSONObject(wallStreetJournalJsonString);
                    wallStreetJournalJsonArray = wallStreetJournalJsonObject.getJSONArray("articles");

                    for (int i = 0; i < wallStreetJournalJsonArray.length(); i++) {
                        newsArticles.put(wallStreetJournalJsonArray.get(i));
                        sourcesList.add("Wall Street Journal");

                    }
                }
                if (sharedPreferences.getBoolean("nyt", true)) {
                    newYorkTimesJsonString = Jsoup.connect(newYorkTimesArticleURl).ignoreContentType(true).execute().body();
                    newYorkTimesJsonObject = new JSONObject(newYorkTimesJsonString);
                    newYorkTimesJsonArray = newYorkTimesJsonObject.getJSONArray("articles");

                    for (int i = 0; i < newYorkTimesJsonArray.length(); i++) {
                        newsArticles.put(newYorkTimesJsonArray.get(i));
                        sourcesList.add("New York Times");

                    }
                }
                if (sharedPreferences.getBoolean("wapo", true)) {
                    washingtonPostJsonString = Jsoup.connect(washingtonPostArticleURL).ignoreContentType(true).execute().body();
                    washingtonPostJsonObject = new JSONObject(washingtonPostJsonString);
                    washingtonPostJsonArray = washingtonPostJsonObject.getJSONArray("articles");

                    for (int i = 0; i < washingtonPostJsonArray.length(); i++) {
                        newsArticles.put(washingtonPostJsonArray.get(i));
                        sourcesList.add("Washington Post");

                    }
                }

                newsArticlesArrayList = new ArrayList<NewsArticle>();

                NewsArticle[] newsArray = new NewsArticle[newsArticles.length()];

                for (int i = 0; i < newsArticles.length(); i++) {
                    newsArray[i] = new NewsArticle();
                    newsArray[i].setDatePublished(newsArticles.getJSONObject(i).getString("publishedAt"));
                    newsArray[i].setDescription(newsArticles.getJSONObject(i).getString("description"));
                    newsArray[i].setImageURL(newsArticles.getJSONObject(i).getString("urlToImage"));
                    newsArray[i].setTitle(newsArticles.getJSONObject(i).getString("title"));
                    newsArray[i].setUrl(newsArticles.getJSONObject(i).getString("url"));
                    newsArray[i].setSource(sourcesList.get(i).toString());
                }


                for (int i = 0; i < newsArticles.length(); i++) {
                    newsArticlesArrayList.add(newsArray[i]);

                }


                Collections.sort(newsArticlesArrayList);
                Collections.reverse(newsArticlesArrayList);
                Log.v("newstest", String.valueOf(newsArticlesArrayList.size()));
                codeWorked = true;


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Articles failed to load, check your internet", Toast.LENGTH_SHORT).show();
            }


            return null;
        }


        protected void onPostExecute(String string) {

            if (codeWorked) {
                listAdapter = new
                        CustomList(getContext(), this.newsArticlesArrayList);
                listView = (ListView) getView().findViewById(R.id.mainListView);

                listView.setAdapter(listAdapter);
                Log.v("sdf", "adapter was worked");

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3) {
                        Log.v("sdf", "list item clicked");


                        String URL = newsArticlesArrayList.get(position).getUrl();
                        try {
                            Uri uri = Uri.parse("googlechrome://navigate?url=" + URL);
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            Log.v("sdf", "list item clicked");

                        } catch (ActivityNotFoundException e) {
                            // Chrome is probably not installed
                            Log.v("sdf", "list item clicked");

                        }

                    }
                });

            } else {

                Toast.makeText(getContext(), "Failed to load articles, check your internet connection", Toast.LENGTH_LONG);
            }


            pdia.dismiss();

        }
    }

}


