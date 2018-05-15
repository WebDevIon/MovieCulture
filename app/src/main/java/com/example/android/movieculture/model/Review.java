package com.example.android.movieculture.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class is responsible for creating Review objects which will store the needed review
 * attributes received from TMDB API.
 */
public class Review {

    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";

    @SerializedName(AUTHOR)
    private String author;
    @SerializedName(CONTENT)
    private String content;

    /**
     * Constructor of the Review object.
     * @param author the author of the comment.
     * @param content the content of the comment.
     */
    public Review(String author, String content){
        this.author = author;
        this.content = content;
    }

    /**
     * Here we declare all the setters and getters of the Review object parameters.
     */
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
