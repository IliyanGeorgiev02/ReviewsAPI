package com.example.reviewsapi.services;

import com.example.reviewsapi.models.Review;
import com.example.reviewsapi.models.dtos.*;
import com.example.reviewsapi.repositories.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    public Review getById(Long id) {
        Optional<Review> reviewOptional = this.reviewRepository.findById(id);
        return reviewOptional.orElse(null);
    }

    public DisplayReviewDto mapReviewsToDisplayReviewDto(List<Review> reviews) {
        List<MovieReviewInfoDto> movieReviewInfoDtos = reviews.stream()
                .map(this::convertToMovieReviewInfoDto)
                .collect(Collectors.toList());
        DisplayReviewDto displayReviewDto = new DisplayReviewDto();
        displayReviewDto.setReviews(movieReviewInfoDtos);
        return displayReviewDto;
    }

    public MovieReviewInfoDto convertToMovieReviewInfoDto(Review review) {
        MovieReviewInfoDto movieReviewInfoDto = new MovieReviewInfoDto();

        movieReviewInfoDto.setReviewTitle(review.getReviewTitle());
        movieReviewInfoDto.setReviewRating(String.valueOf(review.getRating()));
        return movieReviewInfoDto;
    }

    public ReviewFullInfoDto mapToReviewFullInfoDto(Review review) {
        ReviewFullInfoDto map = this.modelMapper.map(review, ReviewFullInfoDto.class);
        map.setLikes(review.getLikeIds().size());
        return map;
    }

    public Review postReview(ReviewFullInfoDto reviewFullInfoDto) {
        Review map = this.modelMapper.map(reviewFullInfoDto, Review.class);
        map.setId(null);
        map.setMovieRelease(LocalDate.parse(reviewFullInfoDto.getMovieRelease()));
        map.setUsername(reviewFullInfoDto.getUsername());
        map.setDirector(reviewFullInfoDto.getDirector());
        map.setPosterUrl(reviewFullInfoDto.getPosterUrl());
        map.setCreated(LocalDate.now());
        return this.reviewRepository.save(map);
    }


    public boolean deleteReview(Long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        } else {
            return false;
        }
    }


    public void updateReview(UpdateReviewRequest updateReviewRequest) {
        ReviewFullInfoDto reviewFullInfoDto = updateReviewRequest.getReviewFullInfoDto();
        Optional<Review> byId = this.reviewRepository.findById(reviewFullInfoDto.getId());
        byId.ifPresent(review -> updateReviewFields(updateReviewRequest.getNewReviewData(), review));
    }

    public void updateReviewFields(EditReviewDto newReviewData, Review review) {
        updateFieldIfNotBlank(newReviewData::getReviewTitle, review::setReviewTitle);
        updateFieldIfValidRating(newReviewData.getReviewRating(), review::setRating);
        updateFieldIfNotBlank(newReviewData::getReviewText, review::setReviewText);
        review.setEdited(LocalDate.now());
        reviewRepository.save(review);
    }

    private void updateFieldIfNotBlank(Supplier<String> newValueSupplier, Consumer<String> updateFunction) {
        String newValue = newValueSupplier.get();
        if (!newValue.isBlank()) {
            updateFunction.accept(newValue);
        }
    }

    private void updateFieldIfValidRating(int newRating, Consumer<Integer> updateFunction) {
        if (newRating > 0) {
            updateFunction.accept(newRating);
        }
    }

    public List<Review> getAllReviews() {
        return this.reviewRepository.findAll();
    }

    public void likeReview(Review byId, long userId) {
        Set<Long> likeIds = byId.getLikeIds();
        if (!likeIds.contains(userId)) {
            likeIds.add(userId);
            this.reviewRepository.save(byId);
        }
    }

    public void dislikeReview(Review byId, long userId) {
        Set<Long> likeIds = byId.getLikeIds();
        if (likeIds.contains(userId)) {
            likeIds.remove(userId);
            this.reviewRepository.save(byId);
        }
    }

    public List<Review> findAllByMovieId(long id) {
        return this.reviewRepository.findAllByMovieId(id);
    }

    public List<Review> getAllByUserId(long id) {
        return this.reviewRepository.findAllByUserId(id);
    }

    public boolean addComment(Review byId, Long commentId) {
        List<Long> commentIds = byId.getCommentIds();
        if (!commentIds.contains(commentId)){
            commentIds.add(commentId);
            return true;
        }
        return false;
    }
}
