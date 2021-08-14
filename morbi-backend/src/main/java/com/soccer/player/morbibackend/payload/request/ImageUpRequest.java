package com.soccer.player.morbibackend.payload.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ImageUpRequest{

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @NotBlank
    private byte[] userimage;

    @NotBlank
    private Long userid;
}
