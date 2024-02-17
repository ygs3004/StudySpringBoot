package com.yexample.mreview.repository;

import com.yexample.mreview.entity.Member;
import com.yexample.mreview.entity.Movie;
import com.yexample.mreview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying // 삭제,업데이트시 필요
    @Query("DELETE FROM Review r WHERE r.member = :member")
    void deleteByMember(Member member);

}
