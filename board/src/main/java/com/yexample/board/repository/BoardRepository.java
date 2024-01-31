package com.yexample.board.repository;

import com.yexample.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 한개의 Board row 안에 Object[] 형태로 연관관계 객체 있는 형태
    @Query("SELECT b, w FROM Board b LEFT JOIN b.writer w WHERE b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "SELECT b, r, count(r)" +
            " FROM Board b" +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.board = b " +
            " GROUP BY b",
            countQuery = "SELECT COUNT(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query(value = "SELECT b, r, count(r)" +
            " FROM Board b" +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.board = b " +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
