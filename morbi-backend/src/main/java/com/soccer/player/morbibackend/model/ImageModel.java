package com.soccer.player.morbibackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_table")
public class ImageModel{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;
    @Lob
    @Column(name = "picByte", length = 100000)
    private byte[] picByte;

    @Column(name = "user_id")
    private Long userId;

    public ImageModel(String name, String type, byte[] picByte, Long userId) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
        this.userId = userId;
    }

    public ImageModel(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }
}
