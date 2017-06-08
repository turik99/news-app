package zaba.constituent;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class CongressFragment extends Fragment {

    public CongressFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CongressFragment newInstance() {
        CongressFragment fragment = new CongressFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        GetCongressStuff getCongressStuff = new GetCongressStuff();
        getCongressStuff.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_congress, container, false);

        TabHost host = (TabHost) view.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Reps");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Senators");
        host.addTab(spec);

//        //Tab 3
//        spec = host.newTabSpec("Tab Three");
//        spec.setContent(R.id.tab3);
//        spec.setIndicator("Bills");
//        host.addTab(spec);

        return view;

    }




    protected class GetCongressStuff extends AsyncTask<String, String, String> {

        private JSONArray jsonArray;
        private JSONArray houseJsonArray;
        private ArrayList<Congressman> congressmanArrayList;
        private Congressman[] senatorArray;
        private ProgressDialog pdia;
        private Congressman[] congressmen;
        private boolean codeWorked;

        protected void onPreExecute() {
            pdia = new ProgressDialog(getContext());
            pdia.setMessage("Loading...");
            pdia.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList arrayList = new ArrayList<JSONObject>();

            String JSONString;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet senateGet = new HttpGet();
                URI senateURL = new URI("https://api.propublica.org/congress/v1/115/senate/members.json?offset=20");
                senateGet.setURI(senateURL);
                senateGet.setHeader("X-API-Key", "jUr5eCGK2h5opZ9xnlEAB8pU4v6v8h4g1rWmxO6f");

                HttpResponse response = httpclient.execute(senateGet);

                String responseString = new BasicResponseHandler().handleResponse(response);
                Log.v("Congress class", responseString);
                JSONObject jsonObject = new JSONObject(responseString);
                jsonArray = jsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("members");

                Log.v("jsonarraytest", String.valueOf(jsonArray));

                ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
                configurationBuilder.setDebugEnabled(true)
                        .setOAuthConsumerKey("e5iNBbqkhKEn5siDd5WBOb6wz")
                        .setOAuthConsumerSecret("wz5IKhUkr1M5yN73LeUHKyJdQHFRkUAGY9OwWyTNiMMmbI1Cg9")
                        .setOAuthAccessToken("563281826-HmrpDTrFE6SIizLVjvf7NOUeq04QzJxjGvgwlAU8")
                        .setOAuthAccessTokenSecret("S6SofR4sNtqaRkxexvjZtOHLDW9G2oHCuce1aLnqJJodX");

                TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
                Twitter twitter = twitterFactory.getInstance();
                User[] users = new User[this.jsonArray.length()];


                senatorArray = new Congressman[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
//                    users[i] = twitter.showUser(twitterString);


                    senatorArray[i] = (new Congressman(this.jsonArray.getJSONObject(i).getString("first_name")
                            + " " + this.jsonArray.getJSONObject(i).getString("last_name"),
                            this.jsonArray.getJSONObject(i).getString("party"),
                            this.jsonArray.getJSONObject(i).getString("state"),
                            this.jsonArray.getJSONObject(i).getString("twitter_account"),
                            this.jsonArray.getJSONObject(i).getString("facebook_account"),
                            this.jsonArray.getJSONObject(i).getString("twitter_account"),
                            this.jsonArray.getJSONObject(i).getString("api_uri")));


                    Log.v("Congress test", senatorArray[i].getName());
                }


                //THIS IS THE BEGGINING OF THE HOUSE OF REPRESENTATIVES CODE!



                HttpGet houseGet = new HttpGet();
                URI houseURL = new URI("https://api.propublica.org/congress/v1/115/house/members.json?offset=20");
                houseGet.setURI(houseURL);
                houseGet.setHeader("X-API-Key", "jUr5eCGK2h5opZ9xnlEAB8pU4v6v8h4g1rWmxO6f");

                HttpResponse houseResponse = httpclient.execute(houseGet);

                String houseResponseString = new BasicResponseHandler().handleResponse(houseResponse);
                Log.v("Congress class", houseResponseString);
                JSONObject houseJsonObject = new JSONObject(houseResponseString);
                houseJsonArray = houseJsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("members");


                congressmen = new Congressman[houseJsonArray.length()];

                for (int i = 0; i<houseJsonArray.length(); i++)
                {
                    congressmen[i] = new Congressman(this.houseJsonArray.getJSONObject(i).getString("first_name")
                            + " " + this.houseJsonArray.getJSONObject(i).getString("last_name"),
                            this.houseJsonArray.getJSONObject(i).getString("party"),
                            this.houseJsonArray.getJSONObject(i).getString("state")
                                    +
                                    "-"
                                    + this.houseJsonArray.getJSONObject(i).getString("district"),
                            this.houseJsonArray.getJSONObject(i).getString("twitter_account"),
                            this.houseJsonArray.getJSONObject(i).getString("facebook_account"),
                            this.houseJsonArray.getJSONObject(i).getString("twitter_account"),
                            this.houseJsonArray.getJSONObject(i).getString("api_uri"));

                    Log.v("House Test", congressmen[i].getName());
                }


                codeWorked = true;

            } catch (Exception e) {
                e.printStackTrace();
                codeWorked = false;
            }
            return null;
        }

        protected void onPostExecute(String string) {

            if (codeWorked)
            {
                CongressCustomList adapter = new CongressCustomList(getContext(), senatorArray);
                ListView senatorList = (ListView) getView().findViewById(R.id.senatorList);
                senatorList.setAdapter(adapter);

                CongressCustomList adapter2 = new CongressCustomList(getContext(), congressmen);
                ListView houseList = (ListView) getView().findViewById(R.id.congressmanList);
                houseList.setAdapter(adapter2);

                adapter2.getFilter().filter("IL");

            }
            else
            {
                Toast.makeText(getContext(),
                        "The data failed to load. Check internet connection", Toast.LENGTH_LONG).show();
            }

            pdia.dismiss();
        }




    }


}
