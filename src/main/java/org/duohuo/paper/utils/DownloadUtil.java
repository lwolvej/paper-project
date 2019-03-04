package org.duohuo.paper.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public final class DownloadUtil {

    private DownloadUtil() {

    }

    public static ResponseEntity<byte[]> getResponseEntity(String fileName, byte[] data) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        httpHeaders.add("Pragma", "No-cache");
        httpHeaders.add("Cache-Control", "No-cache");
        httpHeaders.add("Access-Control-Expose-Headers", "Content-Disposition");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(data.length)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(data);
    }
}
