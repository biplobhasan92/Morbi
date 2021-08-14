package com.soccer.player.morbibackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Inbox")
public class Inbox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 250)
    @Column(name = "inbox")
    private String inbox;

    @NotBlank
    @Column(name = "fromid")
    private Long fromid;

    @NotBlank
    @Column(name = "sendto")
    private Long sendto;

}
