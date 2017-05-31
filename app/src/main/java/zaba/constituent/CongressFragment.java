package zaba.constituent;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;

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
        View view =  inflater.inflate(R.layout.fragment_congress, container, false);

        TabHost host = (TabHost) view.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Senators");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Representatives");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Bills");
        host.addTab(spec);

        spec = host.newTabSpec("Tab Four");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Floor Updates");
        host.addTab(spec);




        return view;

    }



    protected class GetCongressStuff extends AsyncTask<String, String, String>
    {

        private JSONArray jsonArray;
        private ArrayList<Congressman> congressmanArrayList;
        protected void onPreExecute()
        {

        }
        @Override
        protected String doInBackground(String... params) {

            ArrayList arrayList = new ArrayList<JSONObject>();
            String JSONString;
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet senateGet = new HttpGet();
                URI senateURL = new URI("https://api.propublica.org/congress/v1/115/senate/members.json?offset=4");
                senateGet.setURI(senateURL);
                senateGet.setHeader("X-API-Key", "jUr5eCGK2h5opZ9xnlEAB8pU4v6v8h4g1rWmxO6f");

                HttpResponse response = httpclient.execute(senateGet);

                String responseString = new BasicResponseHandler().handleResponse(response);
                Log.v("Congress class", responseString);
                JSONObject jsonObject = new JSONObject(responseString);
                jsonArray = jsonObject.getJSONArray("results").getJSONObject(0).getJSONArray("members");

                Log.v("jsonarraytest", String.valueOf(jsonArray));
                for (int i =0; i<jsonArray.length(); i++)
                {
                    String firstname = this.jsonArray.getJSONObject(i).getString("first_name");
                    firstname = firstname + this.jsonArray.getJSONObject(i).getString("last_name");

                    String party = jsonArray.getJSONObject(i).getString("party");
                    String state = jsonArray.getJSONObject(i).getString("state");
                    congressmanArrayList.add(
                            new Congressman(firstname, party, state));
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

            CongressCustomList adapter = new CongressCustomList(getContext(), congressmanArrayList);
            ListView senatorList = (ListView) getView().findViewById(R.id.senatorList);
            senatorList.setAdapter(adapter);
        }



    }





}
