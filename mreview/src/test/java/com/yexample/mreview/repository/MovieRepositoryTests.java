package com.yexample.mreview.repository;

import com.yexample.mreview.entity.Movie;
import com.yexample.mreview.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository imageRepository;

//    @Commit
//    @Transactional
    @Test
    public void insertMovies() {

        IntStream.rangeClosed(1, 100).forEach( i -> {
            Movie movie = Movie.builder().title("Movie ... " + i).build();
            System.out.println("===============================================");

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1;

            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg")
                        .build();

                imageRepository.save(movieImage);
            }
            System.out.println("===============================================");
        });

    }

    @Test
    public void testListPage() {

        Sort sort = Sort.by(Sort.Direction.DESC, "mno");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        result.getContent().forEach(objects -> {
            System.out.println(Arrays.toString(objects));
        });

    }

    @Test
    public void testGetMovieWithAll() {

        // 리뷰가 많은 임의 Movie로 테스트
        Long mno = 13L;

        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }


    }

}
