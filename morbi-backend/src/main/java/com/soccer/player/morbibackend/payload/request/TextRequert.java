package com.soccer.player.morbibackend.payload.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class TextRequert {

    @NotBlank
    private String username;

    @NotBlank
    private String inbox;

    @NotBlank
    private Long fromid;

    @NotBlank
    private Long sendto;
}
