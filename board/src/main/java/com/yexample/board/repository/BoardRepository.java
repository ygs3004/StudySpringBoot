package com.yexample.board.repository;

import com.yexample.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Member, String> {
}
