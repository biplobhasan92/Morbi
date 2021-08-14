package com.soccer.player.morbibackend.repository;

import com.soccer.player.morbibackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u.isEnabled FROM User u WHERE u.username = ?1 ")
    Integer userIsEnable(String username);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1 ")
    public User findByVerificationCode(String verificationCode);

    @Query(" update User u set u.isEnabled = 1 where u.id=?1 ")
    @Modifying
    Integer enable(Long id);

    @Query("SELECT u FROM User u WHERE u.username = ?1 ")
    User getUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = ?1 ")
    User getUserByid(Long username);

    @Query("SELECT u.photos FROM User u WHERE u.id = ?1 ")
    String getPhotopath(Long id);


    @Query(" update User u set u.photos = ?1 where u.id=?2 ")
    @Modifying
    Integer updateUserPhoto(byte [] photo, Long id);


    @Query(" select u.photos from User u where u.id=?2 ")
    byte [] findPhotoById(Long id);

    @Query(" update User u set u.firstName = ?1, u.lastName = ?2, u.phone = ?3 where u.id =?4 ")
    @Modifying
    Integer updateUserById(String firstName, String lastname, String phone, Long userid);


    @Query("select u from User u where u.id <> ?1 ")
    List<User> findAllButThis(Long userid);
}
