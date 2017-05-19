package zaba.constituent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter, container, false);
    }



    public class GetTwitterFeed extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {

        }
        @Override
        protected String doInBackground(String... params) {
            return null;

        }

        protected void onPostExecute(String string)
        {

        }

    }


}



