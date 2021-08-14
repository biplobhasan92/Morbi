package com.soccer.player.morbibackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(max = 30)
    private String firstName;
    private String middleName;
    @NotBlank
    @Size(max = 30)
    private String lastName;
    @NotBlank
    @Size(max = 15)
    private String phone;
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 100)
    private String addressLine1;
    private String addressLine2;

    @NotBlank
    private String stateName;

    @NotBlank
    private String cityName;

    @NotBlank
    @Size(max = 10)
    private String postCode;
    private Boolean isReadTermsAndCondition;

    @Builder.Default
    private Integer isEnabled =0;
    private String verification_code;
    private String baseFrontURL;
}
