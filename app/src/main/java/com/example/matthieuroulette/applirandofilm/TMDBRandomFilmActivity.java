package com.example.matthieuroulette.applirandofilm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
import java.util.Random;


/**
 * Created by matthieuroulette on 24/01/2018.
 */

public class TMDBRandomFilmActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_film);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Film aléatoire");
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new TMDBRandomFilmActivity.TMDBRandomFilmManager().execute(query);
        } else {
            TextView textView = new TextView(this);
            textView.setText("No network connection.");
            setContentView(textView);
        }
    }

    public void updateViewWithResults(ArrayList<MovieResult> result) {
        ImageView imageView;
        MovieResult movieResult;
        movieResult = result.get(0);
        String title = movieResult.getTitle() ;

        TextView total = findViewById(R.id.texttitre);
        total.setText(title);
        if (movieResult.getReleaseDate() != "null")
        {
            total = findViewById(R.id.textedition);
            total.setText(movieResult.getReleaseDate());
        }
        else
        {
            total = findViewById(R.id.textedition);
            total.setText("Release date not found");
        }
        if (movieResult.getOverview() != "null")
        {
            total = findViewById(R.id.textsyn);
            total.setText(movieResult.getOverview());
        }
        else
        {
            total = findViewById(R.id.textsyn);
            total.setText("Synopsis date not found");
        }
        if (movieResult.getOriginalTitle() != "null")
        {
            total = findViewById(R.id.textorititle);
            total.setText(movieResult.getOriginalTitle());
        }
        else
        {
            total = findViewById(R.id.textorititle);
            total.setText("Original title not found");
        }
        if (movieResult.getPopularity() != "null")
        {
            total = findViewById(R.id.textpop);
            total.setText(movieResult.getPopularity());
        }
        else
        {
            total = findViewById(R.id.textpop);
            total.setText("Popularity not found");
        }
        imageView = findViewById(R.id.imageView);

        Picasso.with(getBaseContext()).load("https://image.tmdb.org/t/p/w500"+movieResult.getPosterPath()).into(imageView);


    }
    public void Retry(View view) {
        Intent intent = new Intent(this, TMDBRandomActivity.class);
        startActivity(intent);
    }
    private class TMDBRandomFilmManager extends AsyncTask {

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
        }

        public ArrayList<MovieResult> searchIMDB(String query) throws IOException {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://api.themoviedb.org/3/genre/"+ query);
            stringBuilder.append("/movies?api_key=" + TMDB_API_KEY);
            stringBuilder.append("&language=" + LANGUAGE);
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
            Random rand = new Random();

            String streamAsString = result;
            ArrayList<MovieResult> results = new ArrayList<MovieResult>();
            try {
                JSONObject jsonObject = new JSONObject(streamAsString);
                JSONArray array = (JSONArray) jsonObject.get("results");
                int nombre = array.length();
                int nombreAleatoire = rand.nextInt(nombre);
                    JSONObject jsonMovieObject = array.getJSONObject(nombreAleatoire);
                    MovieResult.Builder movieBuilder = MovieResult.newBuilder(
                            Integer.parseInt(jsonMovieObject.getString("id")),
                            jsonMovieObject.getString("title"))
                            .setBackdropPath(jsonMovieObject.getString("backdrop_path"))
                            .setOriginalTitle(jsonMovieObject.getString("original_title"))
                            .setPopularity(jsonMovieObject.getString("popularity"))
                            .setPosterPath(jsonMovieObject.getString("poster_path"))
                            .setOverview(jsonMovieObject.getString("overview"))
                            .setReleaseDate(jsonMovieObject.getString("release_date"));
                    results.add(movieBuilder.build());

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
