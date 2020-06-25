package com.android.example.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class MovieNetworkUtils {

    public static final String MOVIE_BASE_URL = "https://api.themoviedb.org/";
    public static final String API_KEY = "api_key";

    public static URL buildUrl(String category){
        Uri buildUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .path("3/movie/"+ category)
                .appendQueryParameter(API_KEY, "<ENTER YOU OWN API KEY HERE>")
                .build();
        URL url = null;
        try{
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.v("MOVIE", "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            } else {
                return  null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }
}
