package com.yexample.bimovie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie")
@Table(name = "tbl_poster")
public class Poster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String fname;

    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
