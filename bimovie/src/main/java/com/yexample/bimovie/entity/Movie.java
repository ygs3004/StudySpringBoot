package com.yexample.bimovie.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "posterList")
@Table(name = "tbl_movie")
public class Movie extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true // 참조가 없는 하위 객체제 삭제
    )
    private List<Poster> posterList = new ArrayList<>();

    public void addPoster(Poster poster) {
        poster.setIdx(this.posterList.size());
        poster.setMovie(this);
        posterList.add(poster);
    }

    public void removePoster(Long ino) {
        Optional<Poster> result = posterList.stream().filter(poster -> poster.getIno() == ino).findFirst();
        result.ifPresent( poster -> {
            poster.setMovie(null);
            posterList.remove(poster);
        });

        changeIdx();
    }

    private void changeIdx() {
        for (int i = 0; i < posterList.size(); i++) {
            posterList.get(i).setIdx(i);
        }
    }

}
