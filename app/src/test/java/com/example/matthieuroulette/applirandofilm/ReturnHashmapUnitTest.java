package com.example.matthieuroulette.applirandofilm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by matthieuroulette on 26/01/2018.
 */

public class ReturnHashmapUnitTest {
    @Test
    public void FReturnHashmapUnitTest () throws Exception {
        HashMap<String, Integer> mItems = new HashMap<String, Integer>();
        ArrayList<MovieResult> result = new ArrayList<>();
        MovieResult.Builder movieBuilder = MovieResult.newBuilder(0,"Star wars");
        result.add(movieBuilder.build());
        movieBuilder = MovieResult.newBuilder(1,"Star trek");
        result.add(movieBuilder.build());
        mItems=TMDBSearchResultActivity.ReturnHashmap(result);
        Map.Entry<String,Integer> entry = mItems.entrySet().iterator().next();
        String key = entry.getKey();
        if (key != result.get(1).getTitle() || key != "Star trek")
        {
            fail();
        }



    }
}
