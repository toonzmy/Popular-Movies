package com.android.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieViewAdapter.MovieOnClickListener {

    private TextView mErrorMessageTextView;
    private RecyclerView mMoviesRecyclerView;
    private MovieViewAdapter mMoviesAdapter;
    private Movie[] mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesRecyclerView = findViewById(R.id.rv_movies);
        mErrorMessageTextView = findViewById(R.id.tv_error_message_display);

        RecyclerView.LayoutManager moviesLayoutManager = new GridLayoutManager(this, 2);
        mMoviesRecyclerView.setLayoutManager(moviesLayoutManager);

        mMoviesAdapter = new MovieViewAdapter(mMovieList, this);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);

        mMoviesRecyclerView.setHasFixedSize(true);

        loadMoviesInBackground("popular");
     }

    private void showMoviesResults(){
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.action_most_popular){
            loadMoviesInBackground("popular");
            return true;
        }
        if(menuItemSelected == R.id.action_top_rated){
            loadMoviesInBackground("top_rated");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMoviesInBackground(String category) {
        showMoviesResults();
        new MovieAsyncTask().execute(category);
    }

    @Override
    public void onMovieClick(int movieIndex) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        String imageUrl = mMovieList[movieIndex].getImageUrl();
        String title = mMovieList[movieIndex].getTitle();
        String overview = mMovieList[movieIndex].getOverview();
        String popularity = mMovieList[movieIndex].getPopularity();
        String viewCount = mMovieList[movieIndex].getViewCount();
        String releaseDate = mMovieList[movieIndex].getReleaseDate();
        intent.putExtra(Intent.EXTRA_TEXT, movieIndex);
        intent.putExtra("movie_image", imageUrl);
        intent.putExtra("movie_title",title);
        intent.putExtra("movie_overview", overview);
        intent.putExtra("movie_popularity", popularity);
        intent.putExtra("movie_view_count", viewCount);
        intent.putExtra("movie_release_date", releaseDate);
        startActivity(intent);
    }

    //Param, Progress, Result
    public class MovieAsyncTask extends AsyncTask<String, Void, Movie[]>{

        @Override
        protected Movie[] doInBackground(String... params) {

            if(params.length == 0){
                return null;
            }
            String category = params[0];

            URL movieRequestUrl = MovieNetworkUtils.buildUrl(category);

            try {
                if(isOnline()){
                    String jsonMovieResponse = MovieNetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                    mMovieList = MovieJsonUtils.getMovieListFromJson(jsonMovieResponse);
                }
                return mMovieList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Movie[] movies) {
            if(movies != null){
                showMoviesResults();
                mMoviesAdapter.setMoviesData(movies);
            }else {
                showErrorMessage();
            }
        }

        //From StackOverflow post -> https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
        public boolean isOnline() {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                int     exitValue = ipProcess.waitFor();
                return (exitValue == 0);
            }
            catch (IOException e)          { e.printStackTrace(); }
            catch (InterruptedException e) { e.printStackTrace(); }

            return false;
        }
    }
}
