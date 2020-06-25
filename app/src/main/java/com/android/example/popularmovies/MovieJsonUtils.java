package com.android.example.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieJsonUtils {

    private static final String MOVIE_RESULTS = "results" ;

    public static Movie[] getMovieListFromJson(String urlStr) throws JSONException {

        final String MOVIE_BASE_URL = "https://image.tmdb.org/t/p/" ;
        final String MOVIE_POSTER_SIZE = "w342" ;

        JSONObject movieJson = new JSONObject(urlStr);

        JSONArray movieArray = movieJson.getJSONArray(MOVIE_RESULTS);

        Movie[] movieList = new Movie[movieArray.length()];;

        for(int i = 0; i<movieArray.length(); i++){

            JSONObject movie = movieArray.getJSONObject(i);
            String baseURL = MOVIE_BASE_URL + MOVIE_POSTER_SIZE  ;
            String imagePath = movie.optString("poster_path","backdrop_path");
            String imageUrl = baseURL  + imagePath;
            String title = movie.optString("title");
            String overview = movie.optString("overview");
            String popularity = movie.optString("popularity");
            String voteCount = movie.optString("vote_count");
            String releaseDate = movie.optString("release_date");

            movieList[i] = new Movie(imageUrl,title,overview, popularity, voteCount, releaseDate);
        }

        return movieList;
    }

}
