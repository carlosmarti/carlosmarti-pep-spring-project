package com.example.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageRepository messageRepo;

    public Message postMessage(Message message){
        
        if(message.getMessageText().isBlank())
            return null;
        
        if(message.getMessageText().length() > 255)
            return null;
        
        return messageRepo.save(message);
    }

    public Boolean accountExist(int pb){

        Message message = messageRepo.findByPostedBy(pb);
        if(message != null)
            return true;
        
        return false;
    }

    public List<Message> getAllMessages(){
        return messageRepo.findAll();
    }

    public Message getMessages(int msgId){

        return messageRepo.findByMessageId(msgId);
    }

    public List<Message> getAllUserMessages(int accountId){
        return messageRepo.findAllByPostedBy(accountId);
    }

    public boolean messageExist(int messageId){

        Message msg = messageRepo.findByMessageId(messageId); 
        if( msg != null)
            return true;
        
        return false;
    }

    public void deleteMessage(int messageId){
        messageRepo.deleteById(messageId);
    }

    public Message updateMessage(Message msg){

        return messageRepo.save(msg);
    }
}
