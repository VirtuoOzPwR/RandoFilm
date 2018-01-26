package com.example.matthieuroulette.applirandofilm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.matthieuroulette.applirandofilm.MovieResult.Builder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by matthieuroulette on 24/01/2018.
 */

public class TMDBSearchResultActivity extends FragmentActivity {

    public HashMap<String, Integer> mItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdbsearch_result);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(" Recherche");
        // Get the intent to get the query.
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);


        // Check if the NetworkConnection is active and connected.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new TMDBQueryManager().execute(query);
        } else {
            TextView textView = new TextView(this);
            textView.setText("No network connection.");
            setContentView(textView);
        }

    }


    /**
     * Updates the View with the results. This is called asynchronously
     * when the results are ready.
     * @param result The results to be presented to the user.
     */
    public void updateViewWithResults(ArrayList<MovieResult> result) {

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mItems = new HashMap<>();
        mItems=ReturnHashmap(result);

        MyAdapter mAdapter = new MyAdapter(mItems);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }
    public static HashMap<String, Integer> ReturnHashmap (ArrayList<MovieResult> result)
    {
        HashMap<String, Integer> mItems = new HashMap<String, Integer>();
        int size = result.size();
        for (int i=0; i<size; i++)
        {
            MovieResult movieResult = result.get(i);
            String title = movieResult.getTitle() ;

            mItems.put(title, R.drawable.film2);

        }
        return mItems;

    }

    private class TMDBQueryManager extends AsyncTask {
        
        private final String TMDB_API_KEY = "54a76659d169778331e840d991c41337";
        private final String LANGUAGE = "fr-FR";
        private static final String DEBUG_TAG = "TMDBQueryManager";
        
        @Override
        protected ArrayList<MovieResult> doInBackground(Object... params) {
            try {
                return searchIMDB((String) params[0]);
            } catch (IOException e) {
                return null;
            }
        }
        
        @Override
        protected void onPostExecute(Object result) {
            updateViewWithResults((ArrayList<MovieResult>) result);
        };


        public ArrayList<MovieResult> searchIMDB(String query) throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://api.themoviedb.org/3/search/movie");
            stringBuilder.append("?api_key=" + TMDB_API_KEY);
            stringBuilder.append("&language=" + LANGUAGE);
            stringBuilder.append("&query=" + query);
            URL url = new URL(stringBuilder.toString());

            InputStream stream = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.connect();

                int responseCode = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response code is: " + responseCode + " " + conn.getResponseMessage());

                stream = conn.getInputStream();
                return parseResult(stringify(stream));
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }
        private ArrayList<MovieResult> parseResult(String result) {
            String streamAsString = result;
            ArrayList<MovieResult> results = new ArrayList<MovieResult>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                JSONArray array = (JSONArray) jsonObject.get("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonMovieObject = array.getJSONObject(i);
                    Builder movieBuilder = MovieResult.newBuilder(
                            Integer.parseInt(jsonMovieObject.getString("id")),
                            jsonMovieObject.getString("title"))
                            .setBackdropPath(jsonMovieObject.getString("backdrop_path"))
                            .setOriginalTitle(jsonMovieObject.getString("original_title"))
                            .setPopularity(jsonMovieObject.getString("popularity"))
                            .setPosterPath(jsonMovieObject.getString("poster_path"))
                            .setReleaseDate(jsonMovieObject.getString("release_date"));
                    results.add(movieBuilder.build());
                }
            } catch (JSONException e) {
                System.err.println(e);
                Log.d(DEBUG_TAG, "Error parsing JSON. String was: " + streamAsString);
            }
            return results;
        }
        
        public String stringify(InputStream stream) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            return bufferedReader.readLine();
        }
    }

}
