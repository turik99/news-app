package zaba.constituent;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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



//    private class DownloadTwitterTask extends AsyncTask<String, Void, String> {
//        final static String CONSUMER_KEY = "nW88XLuFSI9DEfHOX2tpleHbR";
//        final static String CONSUMER_SECRET = "hCg3QClZ1iLR13D3IeMvebESKmakIelp4vwFUICuj6HAfNNCer";
//        final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
//        final static String TwitterStreamURL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
//        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog.setTitle("Loading");
//            dialog.setMessage("Please wait");
//            dialog.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... screenNames) {
//            String result = null;
//
//            if (screenNames.length > 0) {
//                result = getTwitterStream(screenNames[0]);
//            }
//            return result;
//        }
//
//        // onPostExecute convert the JSON results into a Twitter object (which is an Array list of tweets
//        @Override
//        protected void onPostExecute(String result) {
//            Log.e("result",result);
//            dialog.dismiss();
//
//            try {
//                JSONArray jsonArray_data = new JSONArray(result);
//                al_text.clear();
//                for (int i=0; i<jsonArray_data.length();i++){
//
//                    JSONObject jsonObject = jsonArray_data.getJSONObject(i);
//                    al_text.add(jsonObject.getString("text"));
//
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//
//
//            // send the tweets to the adapter for rendering
//            obj_adapter= new Adapter(getApplicationContext(), al_text);
//            lv_list.setAdapter(obj_adapter);
//        }
//
//
//        // convert a JSON authentication object into an Authenticated object
//        private Authenticated jsonToAuthenticated(String rawAuthorization) {
//            Authenticated auth = null;
//            if (rawAuthorization != null && rawAuthorization.length() > 0) {
//                try {
//                    Gson gson = new Gson();
//                    auth = gson.fromJson(rawAuthorization, Authenticated.class);
//                } catch (IllegalStateException ex) {
//                    // just eat the exception
//                }
//            }
//            return auth;
//        }
//
//        private String getResponseBody(HttpRequestBase request) {
//            StringBuilder sb = new StringBuilder();
//            try {
//
//                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
//                HttpResponse response = httpClient.execute(request);
//                int statusCode = response.getStatusLine().getStatusCode();
//                String reason = response.getStatusLine().getReasonPhrase();
//
//                if (statusCode == 200) {
//
//                    HttpEntity entity = response.getEntity();
//                    InputStream inputStream = entity.getContent();
//
//                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
//                    String line = null;
//                    while ((line = bReader.readLine()) != null) {
//                        sb.append(line);
//                    }
//                } else {
//                    sb.append(reason);
//                }
//            } catch (UnsupportedEncodingException ex) {
//            } catch (ClientProtocolException ex1) {
//            } catch (IOException ex2) {
//            }
//            return sb.toString();
//        }
//
//        private String getTwitterStream(String screenName) {
//            String results = null;
//
//            // Step 1: Encode consumer key and secret
//            try {
//                // URL encode the consumer key and secret
//                String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
//                String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");
//
//                // Concatenate the encoded consumer key, a colon character, and the
//                // encoded consumer secret
//                String combined = urlApiKey + ":" + urlApiSecret;
//
//                // Base64 encode the string
//                String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);
//
//                // Step 2: Obtain a bearer token
//                HttpPost httpPost = new HttpPost(TwitterTokenURL);
//                httpPost.setHeader("Authorization", "Basic " + base64Encoded);
//                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//                httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
//                String rawAuthorization = getResponseBody(httpPost);
//                Authenticated auth = jsonToAuthenticated(rawAuthorization);
//
//                // Applications should verify that the value associated with the
//                // token_type key of the returned object is bearer
//                if (auth != null && auth.token_type.equals("bearer")) {
//
//                    // Step 3: Authenticate API requests with bearer token
//                    HttpGet httpGet = new HttpGet(TwitterStreamURL + screenName);
//
//                    // construct a normal HTTPS request and include an Authorization
//                    // header with the value of Bearer <>
//                    httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
//                    httpGet.setHeader("Content-Type", "application/json");
//                    // update the results with the body of the response
//                    results = getResponseBody(httpGet);
//                }
//            } catch (UnsupportedEncodingException ex) {
//            } catch (IllegalStateException ex1) {
//            }
//            return results;
//        }
//    }


}



