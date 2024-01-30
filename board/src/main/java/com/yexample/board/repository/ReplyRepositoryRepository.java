package com.yexample.board.repository;

import com.yexample.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepositoryRepository extends JpaRepository<Member, String> {
}
