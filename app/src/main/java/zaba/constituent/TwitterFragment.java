package zaba.constituent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Set;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFragment extends Fragment {

    public TwitterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TwitterFragment newInstance() {
        TwitterFragment fragment = new TwitterFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> userList = new ArrayList<String>();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("bernie", true);
        editor.commit();

        GetTweets getTweets = new GetTweets();
        getTweets.execute();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter, container, false);



    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {

    }


    public class GetTweets extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(String... params) {



            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey("e5iNBbqkhKEn5siDd5WBOb6wz")
                    .setOAuthConsumerSecret("wz5IKhUkr1M5yN73LeUHKyJdQHFRkUAGY9OwWyTNiMMmbI1Cg9")
                    .setOAuthAccessToken("563281826-HmrpDTrFE6SIizLVjvf7NOUeq04QzJxjGvgwlAU8")
                    .setOAuthAccessTokenSecret("S6SofR4sNtqaRkxexvjZtOHLDW9G2oHCuce1aLnqJJodX");

            TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());

            Twitter twitter = twitterFactory.getInstance();
            try {
                ArrayList<twitter4j.Status> tweets = (ArrayList) twitter.getUserTimeline("realDonaldTrump");
                tweets.add(  twitter.getUserTimeline("BernieSanders").get(10));

            } catch (TwitterException e) {
                e.printStackTrace();
            }





            return null;
        }

        protected void onPostExecute(String string)
        {

        }

    }





}



