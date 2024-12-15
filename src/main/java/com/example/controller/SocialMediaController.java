package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    @PostMapping(value="/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account body){

        if(accountService.accountExist(body))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        
        if(body.getPassword().length() < 4)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(accountService.registerUser(body), HttpStatus.OK);
    }

    @PostMapping(value="login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account){

        Account verifiedAccount = accountService.verifyCridentials(account); 
        if(verifiedAccount == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        
        return new ResponseEntity<>(verifiedAccount, HttpStatus.OK);
    }

    @PostMapping(value = "messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message){

        Boolean posterVerified = messageService.accountExist(message.getPostedBy());

        if(posterVerified){
            Message postedMessage = messageService.postMessage(message);
            if(postedMessage != null)
                return new ResponseEntity<>(postedMessage, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    @GetMapping(value="accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllUserMessages(@PathVariable int accountId){
        return new ResponseEntity<>(messageService.getAllUserMessages(accountId), HttpStatus.OK);
    }

    @GetMapping(value="messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        
        return new ResponseEntity<>(messageService.getAllMessages(),HttpStatus.OK);
    }

    @GetMapping(value="messages/{messageId}")
    public ResponseEntity<Message> getMessages(@PathVariable int messageId){

        return new ResponseEntity<>(messageService.getMessages(messageId), HttpStatus.OK);
    }

    @DeleteMapping(value="messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId){

        if(messageService.messageExist(messageId)){
            return new ResponseEntity<>(1, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value="messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message msg){

        if(!msg.getMessageText().isEmpty() && msg.getMessageText().length() < 255){
            if(messageService.messageExist(messageId))
                if(messageService.updateMessage(msg) != null)
                    return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
