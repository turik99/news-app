package zaba.constituent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.TweetEntity;
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

        Toast.makeText(getContext(), "Note: this feature is currently unfinished", Toast.LENGTH_SHORT).show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter, container, false);


    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        AdView adView = (AdView) view.findViewById(R.id.twitterAdView);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

    }


    public class GetTweets extends AsyncTask<String, String, String> {
        private ArrayList<twitter4j.Status> tweets = new ArrayList<twitter4j.Status>();
        private ProgressDialog dialog;
        private ArrayList<Tweet> tweetArray;
        protected void onPreExecute() {

            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Loading tweets...");
            dialog.show();
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

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            try {

                if (sharedPreferences.getBoolean("bernie", true)) {
                    tweets.addAll(twitter.getUserTimeline("berniesanders"));
                }

                if (sharedPreferences.getBoolean("trump", true)) {
                    tweets.addAll(twitter.getUserTimeline("realDonaldTrump"));

                }

                if (sharedPreferences.getBoolean("hillary", true)) {
                    tweets.addAll(twitter.getUserTimeline("HillaryClinton"));

                }
                if (sharedPreferences.getBoolean("chuckgrassley", true)) {
                    tweets.addAll(twitter.getUserTimeline("ChuckGrassley"));
                }

                if (sharedPreferences.getBoolean("chuckschumer", true)) {
                    tweets.addAll(twitter.getUserTimeline("chuckschumer"));
                }

                tweetArray = new ArrayList<Tweet>();

                for ( int i = 0; i<tweets.size(); i++)
                {
                    tweetArray.add(new Tweet(tweets.get(i).getUser().getName(),
                        tweets.get(i).getText(),
                        tweets.get(i).getUser()
                                .getProfileImageURL(),
                        tweets.get(i).getCreatedAt()
                                .toString()));

                }


                Collections.sort(tweetArray);
            }
            catch (Exception e) {

            }


            return null;
        }

        protected void onPostExecute(String string) {

            ListView listView = (ListView) getView().findViewById(R.id.twitterListView);
            TwitterCustomList adapter = new TwitterCustomList(getActivity(), tweetArray );
            listView.setAdapter(adapter);
            dialog.dismiss();

        }

    }


}



