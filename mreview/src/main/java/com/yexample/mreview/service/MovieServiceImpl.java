package com.yexample.mreview.service;

import com.yexample.mreview.dto.MovieDTO;
import com.yexample.mreview.entity.Movie;
import com.yexample.mreview.entity.MovieImage;
import com.yexample.mreview.repository.MovieImageRepository;
import com.yexample.mreview.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);
        imageRepository.saveAll(movieImageList);

        return movie.getMno();
    }

}
