package com.android.example.popularmovies;

public class Movie {

    private String mTitle;
    private String mImageUrl;
    private String mOverview;
    private String mPopularity;
    private String mViewCount;
    private String mReleaseDate;

    public Movie(String imageUrl, String title, String overview, String popularity, String viewCount, String releaseDate) {
        mTitle = title;
        mImageUrl = imageUrl;
        mOverview = overview;
        mPopularity = popularity;
        mViewCount = viewCount;
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String popularity) {
        mPopularity = popularity;
    }

    public String getViewCount() {
        return mViewCount;
    }

    public void setViewCount(String viewCount) {
        mViewCount = viewCount;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }
}
