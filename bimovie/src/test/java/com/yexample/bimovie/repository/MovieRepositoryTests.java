package com.yexample.bimovie.repository;

import com.yexample.bimovie.entity.Movie;
import com.yexample.bimovie.entity.Poster;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("Insert")
    public void testInsert(){
        log.info("Test Insert Movie.......................................");
        Movie movie = Movie.builder().title("극한직업").build();

        movie.addPoster(Poster.builder().fname("극한직업 포스터1.jpg").build());
        movie.addPoster(Poster.builder().fname("극한직업 포스터2.jpg").build());

        movieRepository.save(movie);
        log.info(movie.getMno());
    }

    @Test
    @Transactional
    @Commit
    public void testAddPoster() {
        log.info("Test Add Poster.......................................");
        Movie movie = movieRepository.getReferenceById(2L);
        movie.addPoster(Poster.builder().fname("극한직업 포스터3.jpg").build());
        movieRepository.save(movie);
    }

    @Test
    @Transactional
    @Commit
    public void testRemovePoster() {
        log.info("Test Remove Poster.......................................");
        Movie movie = movieRepository.getReferenceById(1L);
        movie.removePoster(2L);
        movieRepository.save(movie);
    }

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(10, 100).forEach(i -> {
            Movie movie = Movie.builder().title("세계명작" + i).build();
            movie.addPoster(Poster.builder().fname("셰계명작" + i + " 포스터1.jpg").build());
            movie.addPoster(Poster.builder().fname("셰계명작" + i + " 포스터2.jpg").build());

            movieRepository.save(movie);
        });
    }

    @Test
    @Transactional
    public void testPaging1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Movie> result = movieRepository.findAll(pageable);
        result.getContent().forEach(movie -> {
            log.info(movie.getMno());
            log.info(movie.getTitle());
            log.info(movie.getPosterList().size());
            log.info("=====================================");
        });
    }

    @Test
    public void testPaging2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Movie> result = movieRepository.findAll2(pageable);
        result.getContent().forEach(movie -> {
            log.info(movie.getMno());
            log.info(movie.getTitle());
            log.info(movie.getPosterList());
            log.info("=====================================");
        });
    }

    @Test
    public void testPaging3() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.findAll3(pageable);
        result.getContent().forEach(row -> {
            log.info(Arrays.toString(row));
            log.info("=====================================");
        });
    }

}
