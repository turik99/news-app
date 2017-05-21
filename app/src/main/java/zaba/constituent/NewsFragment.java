package zaba.constituent;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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

        View view =  inflater.inflate(R.layout.fragment_news, container, false);


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


        private ArrayList<NewsArticle> newsArticlesArrayList;

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
                apJsonString = Jsoup.connect(apArticleURL).ignoreContentType(true).execute().body();

                try {
                    apJsonString = Jsoup.connect(apArticleURL).ignoreContentType(true).execute().body();
                    bloombergJsonString = Jsoup.connect(bloombergArticleURL).ignoreContentType(true).execute().body();
                    //financialtimesJsonString =Jsoup.connect(financialtimesArticleURL).ignoreContentType(true).execute().body();
                    reutersJsonString = Jsoup.connect(reutersArticleURL).ignoreContentType(true).execute().body();
                    wallStreetJournalJsonString = Jsoup.connect(wallStreetJournalArticleURL).ignoreContentType(true).execute().body();
                    newYorkTimesJsonString = Jsoup.connect(newYorkTimesArticleURl).ignoreContentType(true).execute().body();
                    washingtonPostJsonString = Jsoup.connect(washingtonPostArticleURL).ignoreContentType(true).execute().body();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //------------------------------------------------------------------------------------------
                //----
                //----
                //----
                //------------------------------------------------------------------------------------------
                //getting the JSON Object from the string that was created by the received text from the website
                try {
                    this.apJsonObject = new JSONObject(apJsonString);
                    this.bloombergJsonObject = new JSONObject(bloombergJsonString);
                    //this.financialtimesJsonObject = new JSONObject(financialtimesJsonString);
                    this.reutersJsonObject = new JSONObject(reutersJsonString);
                    this.wallStreetJournalJsonObject = new JSONObject(wallStreetJournalJsonString);
                    this.newYorkTimesJsonObject = new JSONObject(newYorkTimesJsonString);
                    this.washingtonPostJsonObject = new JSONObject(washingtonPostJsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //------------------------------------------------------------------------------------------
                //----
                //----
                //----
                //Getting the news articles array for further use
                //------------------------------------------------------------------------------------------
                try {
                    JSONArray apArticleJsonArray = this.apJsonObject.getJSONArray("articles");
                    JSONArray bloombergJsonArray = this.bloombergJsonObject.getJSONArray("articles");
//                JSONArray ftJsonArray = this.financialtimesJsonObject.getJSONArray("articles");
                    JSONArray reutersJsonArray = this.reutersJsonObject.getJSONArray("articles");
                    JSONArray wallStreetJournalJsonArray = this.wallStreetJournalJsonObject.getJSONArray("articles");
                    JSONArray newYorkTimesJsonArray = this.newYorkTimesJsonObject.getJSONArray("articles");
                    JSONArray washingtonPostJsonArray = this.washingtonPostJsonObject.getJSONArray("articles");


                    int apArticleIndex = apArticleJsonArray.length();
                    int bloombergArticleIndex = bloombergJsonArray.length();
                    //    int ftArticleIndex = ftJsonArray.length();
                    int reutersArticleIndex = reutersJsonArray.length();
                    int wallStreetJournalArticleIndex = wallStreetJournalJsonArray.length();
                    int newYorkTimesArticleIndex = newYorkTimesJsonArray.length();
                    int washingtonPostArticleIndex = washingtonPostJsonArray.length();


                    for (int i = 0; i < apArticleIndex; i++) {
                        this.newsArticles.put(i, apArticleJsonArray.get(i));
                        sourcesList.add(i, "Associated Press");
                    }
                    Log.v("tag", String.valueOf(apArticleIndex));
                    int newsArticlesIndex = apArticleIndex;

                    for (int i = 0; i < bloombergArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, bloombergJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "Bloomberg");
                    }

                    newsArticlesIndex = newsArticlesIndex + bloombergArticleIndex;
                    for (int i = 0; i < reutersArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, reutersJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "Reuters");
                    }

                    newsArticlesIndex = newsArticlesIndex + reutersArticleIndex;
                    for (int i = 0; i < wallStreetJournalArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, wallStreetJournalJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "Wall Street Journal");
                    }
                    newsArticlesIndex = newsArticlesIndex + newYorkTimesArticleIndex;
                    for (int i = 0; i < newYorkTimesArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, newYorkTimesJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "New York Times");
                    }

                    newsArticlesIndex = newsArticlesIndex + washingtonPostArticleIndex;

                    for (int i = 0; i < washingtonPostArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, washingtonPostJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "Washington Post");
                    }


                    Log.v("tag", String.valueOf(this.newsArticles.length()));


                    NewsArticle[] newsArticleObjects = new NewsArticle[this.newsArticles.length()];

                    for (int i = 0; i < this.newsArticles.length(); i++) {
                        newsArticleObjects[i] = new NewsArticle();
                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.title.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("title"));
                        newsArticleObjects[i - 1].setTitle(this.title.get(i - 1));

                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.url.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("url"));
                        newsArticleObjects[i - 1].setUrl(this.url.get(i - 1));
                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.imageUrl.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("urlToImage"));
                        newsArticleObjects[i - 1].setImageURL(imageUrl.get(i - 1));
                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.descriptions.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("description"));
                        newsArticleObjects[i - 1].setDescription(descriptions.get(i - 1));
                    }


                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.datePublished.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("publishedAt"));
                        newsArticleObjects[i - 1].setDatePublished(datePublished.get(i - 1));
                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        newsArticleObjects[i - 1].setSource((String) sourcesList.get(i - 1));
                    }


                    newsArticlesArrayList = new ArrayList<NewsArticle>(newsArticles.length());


                    for (int i = 0; i < newsArticleObjects.length; i++) {

                        newsArticlesArrayList.add(newsArticleObjects[i]);

                    }
                    Collections.sort(newsArticlesArrayList);
                    Collections.reverse(newsArticlesArrayList);
                    Log.v("tag", this.title.get(apArticleIndex));
                    Log.v("tag", this.url.get(apArticleIndex));


                    Log.v("newstest", "it worked");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("newstest", "it dont work ");
                }

                try {
                    apJsonString = Jsoup.connect(apArticleURL).ignoreContentType(true).execute().body();
                    bloombergJsonString = Jsoup.connect(bloombergArticleURL).ignoreContentType(true).execute().body();
                    //financialtimesJsonString =Jsoup.connect(financialtimesArticleURL).ignoreContentType(true).execute().body();
                    reutersJsonString = Jsoup.connect(reutersArticleURL).ignoreContentType(true).execute().body();
                    wallStreetJournalJsonString = Jsoup.connect(wallStreetJournalArticleURL).ignoreContentType(true).execute().body();
                    newYorkTimesJsonString = Jsoup.connect(newYorkTimesArticleURl).ignoreContentType(true).execute().body();
                    washingtonPostJsonString = Jsoup.connect(washingtonPostArticleURL).ignoreContentType(true).execute().body();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //------------------------------------------------------------------------------------------
                //----
                //----
                //----
                //------------------------------------------------------------------------------------------
                //getting the JSON Object from the string that was created by the received text from the website
                try {
                    this.apJsonObject = new JSONObject(apJsonString);
                    this.bloombergJsonObject = new JSONObject(bloombergJsonString);
                    //this.financialtimesJsonObject = new JSONObject(financialtimesJsonString);
                    this.reutersJsonObject = new JSONObject(reutersJsonString);
                    this.wallStreetJournalJsonObject = new JSONObject(wallStreetJournalJsonString);
                    this.newYorkTimesJsonObject = new JSONObject(newYorkTimesJsonString);
                    this.washingtonPostJsonObject = new JSONObject(washingtonPostJsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //------------------------------------------------------------------------------------------
                //----
                //----
                //----
                //Getting the news articles array for further use
                //------------------------------------------------------------------------------------------
                try {
                    JSONArray apArticleJsonArray = this.apJsonObject.getJSONArray("articles");
                    JSONArray bloombergJsonArray = this.bloombergJsonObject.getJSONArray("articles");
//                JSONArray ftJsonArray = this.financialtimesJsonObject.getJSONArray("articles");
                    JSONArray reutersJsonArray = this.reutersJsonObject.getJSONArray("articles");
                    JSONArray wallStreetJournalJsonArray = this.wallStreetJournalJsonObject.getJSONArray("articles");
                    JSONArray newYorkTimesJsonArray = this.newYorkTimesJsonObject.getJSONArray("articles");
                    JSONArray washingtonPostJsonArray = this.washingtonPostJsonObject.getJSONArray("articles");


                    int apArticleIndex = apArticleJsonArray.length();
                    int bloombergArticleIndex = bloombergJsonArray.length();
                    //    int ftArticleIndex = ftJsonArray.length();
                    int reutersArticleIndex = reutersJsonArray.length();
                    int wallStreetJournalArticleIndex = wallStreetJournalJsonArray.length();
                    int newYorkTimesArticleIndex = newYorkTimesJsonArray.length();
                    int washingtonPostArticleIndex = washingtonPostJsonArray.length();


                    for (int i = 0; i < apArticleIndex; i++) {
                        this.newsArticles.put(i, apArticleJsonArray.get(i));
                        sourcesList.add(i, "Associated Press");
                    }
                    Log.v("tag", String.valueOf(apArticleIndex));
                    int newsArticlesIndex = apArticleIndex;

                    for (int i = 0; i < bloombergArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, bloombergJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "Bloomberg");
                    }

                    newsArticlesIndex = newsArticlesIndex + bloombergArticleIndex;
                    for (int i = 0; i < reutersArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, reutersJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "Reuters");
                    }

                    newsArticlesIndex = newsArticlesIndex + reutersArticleIndex;
                    for (int i = 0; i < wallStreetJournalArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, wallStreetJournalJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "Wall Street Journal");
                    }
                    newsArticlesIndex = newsArticlesIndex + newYorkTimesArticleIndex;
                    for (int i = 0; i < newYorkTimesArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, newYorkTimesJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "New York Times");
                    }

                    newsArticlesIndex = newsArticlesIndex + washingtonPostArticleIndex;

                    for (int i = 0; i < washingtonPostArticleIndex; i++) {
                        this.newsArticles.put(newsArticlesIndex + i, washingtonPostJsonArray.get(i));
                        sourcesList.add(newsArticlesIndex + i, "Washington Post");
                    }


                    Log.v("tag", String.valueOf(this.newsArticles.length()));


                    NewsArticle[] newsArticleObjects = new NewsArticle[this.newsArticles.length()];

                    for (int i = 0; i < this.newsArticles.length(); i++) {
                        newsArticleObjects[i] = new NewsArticle();
                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.title.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("title"));
                        newsArticleObjects[i - 1].setTitle(this.title.get(i - 1));

                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.url.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("url"));
                        newsArticleObjects[i - 1].setUrl(this.url.get(i - 1));
                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.imageUrl.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("urlToImage"));
                        newsArticleObjects[i - 1].setImageURL(imageUrl.get(i - 1));
                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.descriptions.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("description"));
                        newsArticleObjects[i - 1].setDescription(descriptions.get(i - 1));
                    }


                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        this.datePublished.add(i - 1, this.newsArticles.getJSONObject(i - 1).getString("publishedAt"));
                        newsArticleObjects[i - 1].setDatePublished(datePublished.get(i - 1));
                    }

                    for (int i = 1; i <= this.newsArticles.length(); i++) {
                        newsArticleObjects[i - 1].setSource((String) sourcesList.get(i - 1));
                    }


                    newsArticlesArrayList = new ArrayList<NewsArticle>(newsArticles.length());


                    for (int i = 0; i < newsArticleObjects.length; i++) {

                        newsArticlesArrayList.add(newsArticleObjects[i]);

                    }
                    Collections.sort(newsArticlesArrayList);
                    Collections.reverse(newsArticlesArrayList);
                    Log.v("tag", this.title.get(apArticleIndex));
                    Log.v("tag", this.url.get(apArticleIndex));


                    Log.v("newstest", "it worked");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("newstest", "it dont work ");
                }

                codeWorked = true;

            }
            catch (Exception e)
            {
                e.printStackTrace();

                codeWorked = false;
            }
            return null;
        }

        protected void onPostExecute(String string) {

            if (codeWorked)
            {
                listAdapter = new
                        CustomList(getContext(), this.newsArticlesArrayList);
                listView = (ListView) getView().findViewById(R.id.mainListView);

                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View v, int position,
                                            long arg3) {

                        String URL = newsArticlesArrayList.get(position).getUrl();
                        try {
                            Uri uri = Uri.parse("googlechrome://navigate?url=" + URL);
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is probably not installed
                        }

                    }
                });

            }
            else {

            }


            pdia.dismiss();

        }
        ///-----------------------------------------------------------------------------------------
        //-----------
        //-----------
        // These are the getter methods for all of the relevant variables (urls, etc)

        public ArrayList getTitle() {
            return this.title;
        }

        public ArrayList getUrl() {
            return this.url;

        }

        protected Bitmap[] getThumbnails() {
            return this.thumbnails;
        }


    }

}
