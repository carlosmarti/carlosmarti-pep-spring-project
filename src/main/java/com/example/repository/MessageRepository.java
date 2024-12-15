package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    public Message findByPostedBy(int postedBy);
    public List<Message> findAllByPostedBy(int postedBy);
    public Message findByMessageId(int messageId);
}
