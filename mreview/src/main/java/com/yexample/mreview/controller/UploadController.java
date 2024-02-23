
package com.yexample.mreview.controller;

import com.yexample.mreview.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFiles(String fileName) {

        try {
            String srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            log.info("decoding fileName: " + srcFileName);
            File file = new File(uploadPath + File.separator + srcFileName);

            log.info("File :" + file);

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()));
            return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {

        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            // 파일 타입체크
            boolean isNotImage = !uploadFile.getContentType().startsWith("image");
            if (isNotImage) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalName = uploadFile.getOriginalFilename();
            // 브라우저에 따라 전체경로가 들어오는 경우가 있음
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            log.info("upload file: " + uploadFile.getName());
            log.info("upload original name: " + uploadFile.getOriginalFilename());
            log.info("file name: " + fileName);

            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);

                // Thumbnail 생성
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                int thumbnailWidth = 100;
                int thumbnailHeight = 100;

                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, thumbnailWidth, thumbnailHeight);
                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);

    }

    private String makeFolder() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = date.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);
        boolean isNotExistsFolder = !uploadPathFolder.exists();
        if (isNotExistsFolder) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName) {

        log.info("Delete File: " + fileName);
        String srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        File file = new File(uploadPath + File.separator + srcFileName);
        boolean result = file.delete();
        if (Boolean.FALSE.equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        File thumbnail = new File(file.getParent(), "s_" + file.getName());
        result = thumbnail.delete();

        return result
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
