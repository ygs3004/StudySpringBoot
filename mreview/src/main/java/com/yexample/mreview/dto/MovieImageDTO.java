package com.yexample.mreview.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Data
@Log4j2
public class MovieImageDTO {

    private String uuid;
    private String imgName;
    private String path;
    private String imageURL;
    private String thumbnailURL;

    public MovieImageDTO(String imgName, String uuid, String path) {
        this.imgName = imgName;
        this.uuid = uuid;
        this.path = path;
        this.imageURL = makeImageURL();
        this.thumbnailURL = makeThumbnailURL();
    }

    private String makeImageURL() {
        return URLEncoder.encode(path + "/" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }

    private String makeThumbnailURL() {
        return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }

}
