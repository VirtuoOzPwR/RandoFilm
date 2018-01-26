package com.example.matthieuroulette.applirandofilm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by matthieuroulette on 24/01/2018.
 */

public class TMDBRandomActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Recherche aléatoire pour une catégorie");
        // Get the intent to get the query.
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);

        // Check if the NetworkConnection is active and connected.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //new TMDBRandomActivity.TMDBRandomManager().execute(query);
        } else {
            TextView textView = new TextView(this);
            textView.setText("No network connection.");
            setContentView(textView);
        }

    }
    public void ValiderRand(View view) {
        Spinner spinner1;
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        Intent intent = new Intent(this, TMDBRandomFilmActivity.class);
        String text = spinner1.getSelectedItem().toString();

        intent.putExtra("com.example.matthieuroulette.applirandofilm.QUERY",MovieGenre.getID(text));

        startActivity(intent);
    }
    /**
     * Updates the View with the results. This is called asynchronously
     * when the results are ready.
     * @param result The results to be presented to the user.
     */


}
