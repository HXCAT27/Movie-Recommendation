package com.project.movie.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoryRequestBody {
    @JsonProperty("user_id")
    public String userId;

    public Movie favorite;
}
