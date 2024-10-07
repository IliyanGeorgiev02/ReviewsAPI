package com.example.reviewsapi.repositories;

import com.example.reviewsapi.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.movieId = :movieId")
    List<Review> findAllByMovieId(@Param("movieId") long id);

    @Query("SELECT r FROM Review r WHERE r.userId = :userId")
    List<Review> findAllByUserId(@Param("userId") long id);
}
