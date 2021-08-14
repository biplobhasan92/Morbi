package com.soccer.player.morbibackend.repository;

import com.soccer.player.morbibackend.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepo extends JpaRepository<Video, Long> {

    @Query(" select vt from Video vt where vt.userid=?1 ")
    public List<Video> findAllVNameById(Long userid);

    @Query(" select vt.contentType from Video vt where vt.filename=?1 ")
    public String getContentType(String fileName);

}
