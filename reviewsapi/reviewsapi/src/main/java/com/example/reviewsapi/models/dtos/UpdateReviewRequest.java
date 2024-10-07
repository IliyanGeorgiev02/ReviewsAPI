package com.example.reviewsapi.models.dtos;

public class UpdateReviewRequest {

    private EditReviewDto newReviewData;
    private ReviewFullInfoDto reviewFullInfoDto;


    public EditReviewDto getNewReviewData() {
        return newReviewData;
    }

    public void setNewReviewData(EditReviewDto newReviewData) {
        this.newReviewData = newReviewData;
    }

    public ReviewFullInfoDto getReviewFullInfoDto() {
        return reviewFullInfoDto;
    }

    public void setReviewFullInfoDto(ReviewFullInfoDto reviewFullInfoDto) {
        this.reviewFullInfoDto = reviewFullInfoDto;
    }
}
