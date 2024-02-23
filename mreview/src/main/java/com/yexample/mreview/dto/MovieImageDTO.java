package com.yexample.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@ToString
@Builder
@AllArgsConstructor
@Data
@Log4j2
public class MovieImageDTO {

    private String uuid;
    private String imgName;
    private String path;

    public String getImageURL() {
        return URLEncoder.encode(path + "/" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }

    public String getThumbnailURL() {
        return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }

}
