package com.yexample.mreview.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Data
@Log4j2
public class UploadResultDTO {

    private String fileName;
    private String uuid;
    private String folderPath;
    private String imageURL;
    private String thumbnailURL;

    public UploadResultDTO(String fileName, String uuid, String folderPath) {
        this.fileName = fileName;
        this.uuid = uuid;
        this.folderPath = folderPath;
        this.imageURL = makeImageURL();
        this.thumbnailURL = makeThumbnailURL();
    }

    private String makeImageURL() {
        return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, StandardCharsets.UTF_8);
    }

    private String makeThumbnailURL() {
        return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, StandardCharsets.UTF_8);
    }

}
