package com.yexample.board;

import com.yexample.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void testSearch() {
        boardRepository.search1();
    }


    @Test
    public void testSearchPage() {
        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("bno").descending()
                        .and(Sort.by("title").ascending())
        );

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }

}
