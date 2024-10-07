package com.example.reviewsapi.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "review_title", nullable = false)
    private String reviewTitle;
    @Column(name = "review_text", nullable = false)
    private String reviewText;
    private int rating;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "review_like_ids", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "like_id")
    private Set<Long> likeIds;
    @Column(name = "created_date")
    private LocalDate created;
    @Column(name = "edited_date")
    private LocalDate edited;
    @Column(name = "movie_title")
    private String movieTitle;
    @Column(name = "movie_release")
    private LocalDate movieRelease;
    private String director;
    private String username;
    @Column(name = "poster_url")
    private String posterUrl;
    @Column(name = "movie_id")
    private long movieId;
    @Column(name = "user_id")
    private long userId;
    @ElementCollection
    @CollectionTable(name = "review_comment_ids", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "comment_id")
    private List<Long> commentIds;

    public Review() {
        this.commentIds=new ArrayList<>();
        this.likeIds=new HashSet<>();
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalDate getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(LocalDate movieRelease) {
        this.movieRelease = movieRelease;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Set<Long> getLikeIds() {
        return likeIds;
    }

    public void setLikeIds(Set<Long> likeIds) {
        this.likeIds = likeIds;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getEdited() {
        return edited;
    }

    public void setEdited(LocalDate edited) {
        this.edited = edited;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<Long> commentIds) {
        this.commentIds = commentIds;
    }
}
