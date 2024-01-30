package com.yexample.board.entity;

import jakarta.persistence.*;

@Entity
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String text;
    private String replyer;

    @ManyToOne
    private Board board;
}
