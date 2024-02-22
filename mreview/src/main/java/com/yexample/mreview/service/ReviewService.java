package com.yexample.mreview.service;

import com.yexample.mreview.dto.ReviewDTO;
import com.yexample.mreview.entity.Member;
import com.yexample.mreview.entity.Movie;
import com.yexample.mreview.entity.Review;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getListOfMovie(Long mno);

    Long register(ReviewDTO reviewDTO);

    void modify(ReviewDTO reviewDTO);

    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO reviewDTO){
        Review movieReview = Review.builder()
                .reviewnum(reviewDTO.getReviewnum())
                .movie(Movie.builder().mno(reviewDTO.getMno()).build())
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();

        return movieReview;
    };

    default ReviewDTO entityToDto(Review movieReview) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getMid())
                .nickname(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();
        return reviewDTO;
    }

}
