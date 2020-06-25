package com.android.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView detailImageView = findViewById(R.id.iv_movie_detail);
        TextView titleTextView = findViewById(R.id.tv_detail_title);
        TextView overviewTextView = findViewById(R.id.tv_detail_overview);
        TextView popularityTextView = findViewById(R.id.tv_detail_popularity);
        TextView viewCountTextView = findViewById(R.id.tv_detail_view_count);
        TextView releaseDateTextView = findViewById(R.id.tv_detail_release_date);
        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            String imageUrl = intent.getStringExtra("movie_image");
            String title = intent.getStringExtra("movie_title");
            String overview = intent.getStringExtra("movie_overview");
            String popularity = intent.getStringExtra("movie_popularity");
            String viewCount = intent.getStringExtra("movie_view_count");
            String releaseDate = intent.getStringExtra("movie_release_date");

            titleTextView.setText(title);
            popularityTextView.setText("Popularity: "+ popularity);
            viewCountTextView.setText("View Count: "+viewCount);
            releaseDateTextView.setText(releaseDate);
            overviewTextView.setText(overview);
            Picasso.get()
                    .load(imageUrl).fit().centerInside()
                    .into(detailImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.v("Movie", "Success");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.v("Movie", e.getMessage());
                        }
                    });
        }
    }
}