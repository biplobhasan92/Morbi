package com.soccer.player.morbibackend.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Response {

    @NotBlank
    private String fileName;
    @NotBlank
    private String fileDownloadUri;
    @NotBlank
    private String fileType;
    @NotBlank
    private long size;

    public Response(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
