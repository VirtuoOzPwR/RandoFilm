package com.example.matthieuroulette.applirandofilm;

import java.util.Hashtable;

/**
 * Created by matthieuroulette on 24/01/2018.
 */

public class  MovieGenre {
    public static Hashtable<String, String> dic = new Hashtable();

    public static String getID(String query) {
        Hashtable<String, String> dic = new Hashtable();
        dic.put("Action","28");
        dic.put("Aventure", "12");
        dic.put("Animation", "16");
        dic.put("Comedie", "35");
        dic.put("Crime", "80");
        dic.put("Documentaire", "99");
        dic.put("Drame", "18");
        dic.put("Familial", "10751");
        dic.put("Fantastique", "14");
        dic.put("Histoire", "36");
        dic.put("Horreur", "27");
        dic.put("Musique", "10402");
        dic.put("Mystere", "9648");
        dic.put("Romance", "10749");
        dic.put("Science-Fiction", "878");
        dic.put("Telefilm", "10770");
        dic.put("Thriller", "53");
        dic.put("Guerre", "10752");
        dic.put("Western", "37");
        return dic.get(query);
    }
}
