package zaba.constituent;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class CongressmanActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String api;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressman);
        api = getIntent().getStringExtra("congressman");
        String stateDistrict;
        stateDistrict = getIntent().getStringExtra("district");

        TextView stateText = (TextView) findViewById(R.id.congressmanActivityState);
        stateText.setText(stateDistrict);

        GetCongressmanData getCongressmanData = new GetCongressmanData(api);
        getCongressmanData.execute();

        String[] data = {
                "Recent Votes",
                "Missed Votes",
                "Sponsored Bills"

        };

        Spinner s = (Spinner) findViewById(R.id.congressmanActivitySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, data);
        s.setAdapter(adapter);
    }

    protected void congressmanTwitterClick(View view)
    {
        Button button = (Button) findViewById(R.id.congressActivityOpenTwitter);
        try
        {
            Uri uri = Uri.parse("googlechrome://navigate?url=" + "https://www.twitter.com/"
                    + button.getText().toString().substring(1, button.getText().toString().length()));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e)
        {
            // Chrome is probably not installed
        }

    }

    private class GetCongressmanData extends AsyncTask<String, String, String>
    {

        private String api;
        private String twitterName;
        private String facebook;
        private String name;
        private String party;
        private String state;
        private String district;
        private String twitterURL;
        private Bill[] bills = new Bill[100];

        //UI ELEMENTS

        private ImageView profileImage;
        private TextView twitterText;
        private TextView facebookText;
        private TextView nameText;
        private boolean twitterWorked;


        public GetCongressmanData (String apiArg)
        {
            this.api=apiArg;
        }
        protected void onPreExecute()
        {
            this.nameText = (TextView) findViewById(R.id.congressmanActivityName);
            this.twitterText = (TextView) findViewById(R.id.congressActivityOpenTwitter);
            this.profileImage = (ImageView) findViewById(R.id.congressmanActivityProfileImage);
        }

        @Override
        protected String doInBackground(String... params)
        {

            try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet memberGet = new HttpGet();
                URI senateURL = new URI(this.api);

                Log.v("Congres Activity", this.api);
                memberGet.setURI(senateURL);
                memberGet.setHeader("X-API-Key", "jUr5eCGK2h5opZ9xnlEAB8pU4v6v8h4g1rWmxO6f");
                HttpResponse response = httpclient.execute(memberGet);
                String responseString = new BasicResponseHandler().handleResponse(response);
                Log.v("Congress class", responseString);
                JSONObject jsonObject = new JSONObject(responseString);


                Log.v("congress class", jsonObject.getJSONArray("results").getJSONObject(0).getString("first_name"));

                try{
                    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
                    configurationBuilder.setDebugEnabled(true)
                            .setOAuthConsumerKey("e5iNBbqkhKEn5siDd5WBOb6wz")
                            .setOAuthConsumerSecret("wz5IKhUkr1M5yN73LeUHKyJdQHFRkUAGY9OwWyTNiMMmbI1Cg9")
                            .setOAuthAccessToken("563281826-HmrpDTrFE6SIizLVjvf7NOUeq04QzJxjGvgwlAU8")
                            .setOAuthAccessTokenSecret("S6SofR4sNtqaRkxexvjZtOHLDW9G2oHCuce1aLnqJJodX");

                    TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
                    Twitter twitter = twitterFactory.getInstance();
                    this.twitterName = jsonObject.getJSONArray("results").getJSONObject(0).getString("twitter_account");
                    this.twitterURL = twitter.showUser(twitterName).getBiggerProfileImageURL();
                    twitterWorked = true;

                }
                catch (TwitterException e)
                {
                    e.printStackTrace();
                    twitterWorked = false;

                }


                this.name = jsonObject.getJSONArray("results").getJSONObject(0).getString("first_name") +
                        " " + jsonObject.getJSONArray("results").getJSONObject(0).getString("last_name");
                //You could be a hypercard developer!


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet memberGet = new HttpGet();
                URI votesURL = new URI(this.api.substring(0, 54) + "/votes.json");

                Log.v("Congres Activity", this.api.substring(0, 54) + "/votes.json");
                memberGet.setURI(votesURL);
                memberGet.setHeader("X-API-Key", "jUr5eCGK2h5opZ9xnlEAB8pU4v6v8h4g1rWmxO6f");
                HttpResponse response = httpclient.execute(memberGet);
                String responseString = new BasicResponseHandler().handleResponse(response);
                Log.v("Congress votes test", responseString);
                JSONObject jsonObject = new JSONObject(responseString);

                JSONArray jsonArray = jsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("votes");
                Log.v("Congress test votes", String.valueOf(jsonArray.length()));


                for (int i = 0; i< jsonArray.length(); i++)
                {
                    bills[i] = (new Bill("HR 2030 EXAMPLE",
                            jsonArray.getJSONObject(i).getString("description"),
                            jsonArray.getJSONObject(i).getString("question"),
                            jsonArray.getJSONObject(i).getString("position")
                    ));
                    Log.v("Billstest", bills[i].getName());
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }















            return null;
        }

        protected void onPostExecute(String string)
        {
            this.nameText.setText(this.name);

            if (twitterWorked)
            {
                this.twitterText.setText("@" + this.twitterName);
                Picasso.with(getApplicationContext()).load(this.twitterURL).into(profileImage);

            }
            else {
                this.twitterText.setMaxWidth(0);
                this.twitterText.setMaxHeight(0);
            }

            VotesCustomList adapter = new VotesCustomList(CongressmanActivity.this, bills);
            ListView listView = (ListView) findViewById(R.id.congressmanActivityVotesListView);

            listView.setAdapter(adapter);



        }
    }

}
