package com.yexample.mreview.service;

import com.yexample.mreview.dto.ReviewDTO;
import com.yexample.mreview.entity.Movie;
import com.yexample.mreview.entity.Review;
import com.yexample.mreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        return result.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review movieReview = this.dtoToEntity(reviewDTO);
        reviewRepository.save(movieReview);
        return movieReview.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {

        Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewnum());
        result.ifPresent( review -> {
            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());

            reviewRepository.save(review);
        });

    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }
}
