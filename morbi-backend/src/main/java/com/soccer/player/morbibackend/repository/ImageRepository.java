package com.soccer.player.morbibackend.repository;

import com.soccer.player.morbibackend.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
     Optional<ImageModel> findByName(String name);



     @Query(" update ImageModel u set u.picByte = ?1, u.name =?2, u.type =?3 where u.id =?4 ")
     @Modifying
     Integer updateUserPhoto(byte [] photo, String name, String type, Long id);

     @Query("SELECT u FROM ImageModel u WHERE u.userId = ?1 ")
     Optional<ImageModel> getPhotoByUserid(Long userid);


     @Query("SELECT u FROM ImageModel u WHERE u.userId = ?1 ")
     ImageModel getImageByUserid(Long userid);
}