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


public class WireFragment extends Fragment {

    CustomList listAdapter;

    private GetNewsArticles getNewsArticles;

    public WireFragment()
    {
    }

    // TODO: Rename and change types and number of parameters
    public static WireFragment newInstance() {
        WireFragment fragment = new WireFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getNewsArticles = new GetNewsArticles(getContext());
        this.getNewsArticles.execute();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_wire, container, false);


        AdView adView = (AdView) view.findViewById(R.id.wireAdView);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

        return view;
    }



    public class GetNewsArticles extends AsyncTask<String, String, String>
    {

        private ListView listView;


        private ArrayList<NewsArticle> newsArticlesArrayList;


        private Context context;
        private ProgressDialog pdia;
        private String breitbartArticleURL = "https://newsapi.org/v1/articles?source=breitbart-news&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String breitBartJsonString;
        private String huffingtonPostArticleURL = "https://newsapi.org/v1/articles?source=the-huffington-post&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String huffingtonPostJsonString;

        private ArrayList<String> title = new ArrayList<String>();


        //this contains the json data for the URLs of the articles
        private ArrayList<String> url = new ArrayList<String>();

        private ArrayList<String> imageUrl = new ArrayList<String>();


        private ArrayList<String> descriptions = new ArrayList<String>();
        private ArrayList sourcesList = new ArrayList<String>();
        private ArrayList topArticle = new ArrayList<String>();
        private ArrayList<String> datePublished = new ArrayList<String>();



        private JSONObject breitbartJSONObject;
        private JSONObject huffpostJSONObject;
        public GetNewsArticles(Context contextArg)
        {
            this.context = contextArg;
        }


        protected void onPreExecute()
        {
            pdia = new ProgressDialog(context);
            pdia.setMessage("Loading Articles...");
            pdia.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                breitBartJsonString = Jsoup.connect(breitbartArticleURL).ignoreContentType(true).execute().body();


                try {
                    breitBartJsonString = Jsoup.connect(breitbartArticleURL).ignoreContentType(true).execute().body();
                    huffingtonPostJsonString = Jsoup.connect(huffingtonPostArticleURL).ignoreContentType(true).execute().body();
                } catch (IOException e) {

                }

                try {
                    huffpostJSONObject = new JSONObject(huffingtonPostJsonString);
                    breitbartJSONObject = new JSONObject(breitBartJsonString);

                } catch (JSONException e) {

                }


                try {
                    JSONArray breitbartJSONArray = breitbartJSONObject.getJSONArray("articles");
                    JSONArray huffpostJSONArray = huffpostJSONObject.getJSONArray("articles");


                    int breitbartIndex = breitbartJSONArray.length();
                    int huffpostIndex = huffpostJSONArray.length();

                    int totalIndex;

                    JSONArray newsArticles = new JSONArray();

                    ArrayList sourcesList = new ArrayList(100);

                    for (int i = 0; i < breitbartIndex; i++) {
                        newsArticles.put(i, breitbartJSONArray.get(i));
                        sourcesList.add(i, "Breitbart");
                    }
                    totalIndex = breitbartIndex;


                    for (int i = 0; i < huffpostIndex; i++) {
                        newsArticles.put(totalIndex + i, huffpostJSONArray.get(i));
                        sourcesList.add(totalIndex + i, "HuffPost");
                    }
                    totalIndex = totalIndex + huffpostIndex;


                    NewsArticle[] newsArticleObjects = new NewsArticle[newsArticles.length()];

                    for (int i = 0; i < newsArticles.length(); i++) {
                        newsArticleObjects[i] = new NewsArticle();
                    }

                    for (int i = 1; i <= newsArticles.length(); i++) {
                        this.title.add(i - 1, newsArticles.getJSONObject(i - 1).getString("title"));
                        newsArticleObjects[i - 1].setTitle(this.title.get(i - 1));

                    }

                    for (int i = 1; i <= newsArticles.length(); i++) {
                        this.url.add(i - 1, newsArticles.getJSONObject(i - 1).getString("url"));
                        newsArticleObjects[i - 1].setUrl(this.url.get(i - 1));
                    }

                    for (int i = 1; i <= newsArticles.length(); i++) {
                        this.imageUrl.add(i - 1, newsArticles.getJSONObject(i - 1).getString("urlToImage"));
                        newsArticleObjects[i - 1].setImageURL(imageUrl.get(i - 1));
                    }

                    for (int i = 1; i <= newsArticles.length(); i++) {
                        this.descriptions.add(i - 1, newsArticles.getJSONObject(i - 1).getString("description"));
                        newsArticleObjects[i - 1].setDescription(descriptions.get(i - 1));
                    }


                    for (int i = 1; i <= newsArticles.length(); i++) {
                        this.datePublished.add(i - 1, newsArticles.getJSONObject(i - 1).getString("publishedAt"));
                        newsArticleObjects[i - 1].setDatePublished(datePublished.get(i - 1));
                    }

                    for (int i = 1; i <= newsArticles.length(); i++) {
                        newsArticleObjects[i - 1].setSource((String) sourcesList.get(i - 1));
                    }


                    newsArticlesArrayList = new ArrayList<NewsArticle>(100);

                    for (int i = 0; i < newsArticleObjects.length; i++) {

                        newsArticlesArrayList.add(newsArticleObjects[i]);

                    }

                    Collections.sort(newsArticlesArrayList);


                } catch (JSONException e) {

                }

            } catch (IOException e) {
                Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT);
            }
            return null;

        }


        protected void onPostExecute(String string) {

            {
                listAdapter = new
                        CustomList(getContext(), this.newsArticlesArrayList);
                listView = (ListView) getView().findViewById(R.id.fakeNewsListView);

                listView.setAdapter(listAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            Uri uri = Uri.parse("googlechrome://navigate?url=" + newsArticlesArrayList.get(i).getUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is probably not installed
                        }
                    }
                });


                pdia.dismiss();

            }


        }

    }

}
