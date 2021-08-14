package com.soccer.player.morbibackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(	name = "users",
        uniqueConstraints = { @UniqueConstraint(columnNames = "email")})
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(max = 30)
    @JoinColumn(name = "f_name")
    private String firstName;
    @NotBlank
    @Size(max = 30)
    @JoinColumn(name = "m_name")
    private String middleName;
    @NotBlank
    @Size(max = 30)
    @JoinColumn(name = "l_name")
    private String lastName;
    @NotBlank
    @Size(max = 15)
    private String phone;
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @JoinColumn(name = "address_line1")
        private String addressLine1;
    @JoinColumn(name = "address_line2")
    private String addressLine2;
    @JoinColumn(name = "state_name")
    private String stateName;
    @JoinColumn(name = "city_name")
    private String cityName;
    @JoinColumn(name = "post_code")
    private String postCode;
    private Boolean isReadTermsAndCondition;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    private Integer isEnabled;

    @Lob
    @Column()
    private byte[] photos;
}
