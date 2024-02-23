package com.yexample.mreview.entity;

import jakarta.persistence.*;
import lombok.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "movie")
public class MovieImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    public String getImageURL() {
        return URLEncoder.encode(path + "/" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }

    public String getThumbnailURL() {
        return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }

}
