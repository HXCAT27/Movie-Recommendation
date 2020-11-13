package com.project.movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Movie {
    private String id;
    private String title;
    private String genres;
    private String MovieLogo;
    private String url;
    //private String description;
    //private Set<String> keywords;
    private boolean favorite;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("genres")
    public String getGenres() {
        return genres;
    }

    @JsonProperty("MovieLogo")
    public String getMovieLogo() {
        return MovieLogo;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Movie(){
    }

    public Movie(String id, String title, String genres, String MovieLogo, String url, boolean favorite) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.MovieLogo = MovieLogo;
        this.url = url;
        this.favorite = favorite;
    }
}
