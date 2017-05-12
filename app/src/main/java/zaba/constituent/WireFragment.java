package zaba.constituent;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;


public class WireFragment extends Fragment {


    public WireFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static WireFragment newInstance() {
        WireFragment fragment = new WireFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wire, container, false);
    }



    public class GetNewsArticles extends AsyncTask<String, String, String>
    {

        private Context context;
        private ProgressDialog pdia;
        private String breitbartArticleURL = "https://newsapi.org/v1/articles?source=breitbart-news&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String breitBartJsonString;
        private String buzfeedArticleURL = "https://newsapi.org/v1/articles?source=buzzfeed&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String buzfeedJsonString;
        private String huffingtonPostArticleURL = "https://newsapi.org/v1/articles?source=the-huffington-post&sortBy=top&apiKey=c71b40ee96644c5ab0f06e63e56d59ff";
        private String huffingtonPostJsonString;



        private JSONObject breitbartJSONObject;
        private JSONObject huffpostJSONObject;
        private JSONObject buzzfeedJSONObject;
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
        protected String doInBackground(String... strings)
        {
            try
            {
                breitBartJsonString = Jsoup.connect(breitbartArticleURL).ignoreContentType(true).execute().body();
                huffingtonPostJsonString = Jsoup.connect(huffingtonPostArticleURL).ignoreContentType(true).execute().body();

            }
            catch (IOException e)
            {

            }

            try
            {
                huffpostJSONObject = new JSONObject(huffingtonPostJsonString);
                breitbartJSONObject = new JSONObject(breitBartJsonString);
                buzzfeedJSONObject = new JSONObject(buzfeedJsonString);

            }
            catch (JSONException e)
            {

            }



            try
            {
                JSONArray breitbartJSONArray = breitbartJSONObject.getJSONArray("articles");
                JSONArray buzfeedJSONArray = buzzfeedJSONObject.getJSONArray("articles");
                JSONArray huffpostJSONArray = huffpostJSONObject.getJSONArray("articles");




            }
            catch (JSONException e)
            {

            }

            return null;
        }


        protected void onPostExecute(String string)
        {


        }

    }


}
