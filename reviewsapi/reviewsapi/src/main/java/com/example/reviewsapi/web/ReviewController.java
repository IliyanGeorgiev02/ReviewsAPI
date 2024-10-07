package com.example.reviewsapi.web;

import com.example.reviewsapi.models.Review;
import com.example.reviewsapi.models.dtos.*;
import com.example.reviewsapi.services.ReviewService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<ReviewFullInfoDto> getReviewById(@PathVariable("id") Long id) {
        Review byId = reviewService.getById(id);
        return ResponseEntity.ok(this.reviewService.mapToReviewFullInfoDto(byId));
    }

    @Transactional
    @PostMapping("/review/save")
    public ResponseEntity<?> createReview(@RequestBody @Valid ReviewFullInfoDto reviewFullInfoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        Review review = this.reviewService.postReview(reviewFullInfoDto);
        return ResponseEntity.ok(reviewService.mapToReviewFullInfoDto(review));
    }

    @DeleteMapping("/review/delete/{id}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable("id") Long reviewId) {
        boolean result = this.reviewService.deleteReview(reviewId);

        if (result) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @PutMapping("/review/update")
    public ResponseEntity<Void> updateReview(@RequestBody UpdateReviewRequest updateReviewRequest) {
        this.reviewService.updateReview(updateReviewRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/review/all")
    public ResponseEntity<List<ReviewFullInfoDto>> getAllReviews() {
        List<Review> allReviews = this.reviewService.getAllReviews();
        List<ReviewFullInfoDto> reviewCollection = allReviews.stream().map(reviewService::mapToReviewFullInfoDto).toList();
        return ResponseEntity.ok(reviewCollection);
    }

    @PostMapping("/review/like/{reviewId}/{userId}")
    public ResponseEntity<Void> likeReview(@PathVariable("reviewId") long reviewId, @PathVariable("userId") long userId) {
        Review byId = this.reviewService.getById(reviewId);
        this.reviewService.likeReview(byId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/review/dislike/{reviewId}/{userId}")
    public ResponseEntity<Void> dislikeReview(@PathVariable("reviewId") long reviewId, @PathVariable("userId") long userId) {
        Review byId = this.reviewService.getById(reviewId);
        this.reviewService.dislikeReview(byId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviews/movie/{id}")
    public ResponseEntity<List<ReviewFullInfoDto>> getAllByMovieId(@PathVariable("id") long id) {
        List<Review> allByMovieId = this.reviewService.findAllByMovieId(id);
        List<ReviewFullInfoDto> reviewFullInfoDtos = allByMovieId.stream().map(reviewService::mapToReviewFullInfoDto).toList();
        return ResponseEntity.ok(reviewFullInfoDtos);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<List<ReviewFullInfoDto>> getAllByUserId(@PathVariable("id") long id) {
        List<Review> allByUserId = this.reviewService.getAllByUserId(id);
        List<ReviewFullInfoDto> reviewFullInfoDtos = allByUserId.stream().map(reviewService::mapToReviewFullInfoDto).toList();
        return ResponseEntity.ok(reviewFullInfoDtos);
    }


    @Transactional
    @PostMapping("/review/comment/{reviewId}/{commentId}")
    public ResponseEntity<Boolean> postComment(@PathVariable("reviewId") Long reviewId, @PathVariable("commentId") Long commentId) {
        Review byId = this.reviewService.getById(reviewId);
        boolean result = this.reviewService.addComment(byId, commentId);
        return ResponseEntity.ok(result);
    }


}
