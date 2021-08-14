package com.soccer.player.morbibackend.repository;

import com.soccer.player.morbibackend.model.ImageModel;
import com.soccer.player.morbibackend.model.Inbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Index;
import java.util.List;

public interface InboxRepo extends JpaRepository<Inbox, Long> {


    @Query("SELECT i FROM Inbox i ") // .inbox, (select u.firstName from User u where u.username=i.username) as firstName
    Inbox findAllInboxWithName();

    @Override
    List<Inbox> findAllById(Iterable<Long> longs);

    @Query("SELECT i FROM Inbox i where i.fromid = ?1 or i.sendto = ?1 ")
    List<Inbox> findMessageByFrom(Long userid);


}
