package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accServ;
    MessageService msgServ;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        accServ = accountService;
        msgServ = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Optional<Account>> registerUser(@RequestBody Account account) {
        Optional<Account> newAcc = accServ.register(account);
        if (newAcc == null) return ResponseEntity.status(409).body(newAcc);
        return ResponseEntity.ok().body(newAcc);
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<Account>> loginUser(@RequestBody Account account) {
        Optional<Account> loginAcc = accServ.login(account);
        if (loginAcc == null) return ResponseEntity.status(401).body(loginAcc);
        return ResponseEntity.ok().body(loginAcc);
    }

    @PostMapping("/messages")
    public ResponseEntity<Optional<Message>> addMessage(@RequestBody Message message) {
        Optional<Message> newMsg = msgServ.addMessage(message);
        if (newMsg == null) return ResponseEntity.status(400).body(newMsg);
        return ResponseEntity.ok().body(newMsg);
    }

    @GetMapping("/messages")
    public ResponseEntity<Optional<List<Message>>> fetchMessages() {
        return ResponseEntity.ok().body(msgServ.fetchMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Optional<Message>> fetchMessageById(@PathVariable int messageId) {
        Optional<Message> message = msgServ.fetchMessageById(messageId);
        if (message.isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.ok(Optional.of(message.get()));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Optional<Integer>> deleteMessage(@PathVariable int messageId) {
        Optional<Integer> numRowsDel = msgServ.deleteMessage(messageId);
        return ResponseEntity.ok().body(numRowsDel);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Optional<Integer>> updateMessage(@PathVariable int messageId, @RequestBody Message msgUpdate) {
        Optional<Integer> numRowsUpdated = msgServ.updateMessage(messageId, msgUpdate);
        if (numRowsUpdated == null) return ResponseEntity.status(400).body(numRowsUpdated);
        return ResponseEntity.ok().body(numRowsUpdated);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<Optional<List<Message>>> fetchMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok().body(msgServ.fetchMessagesByUser(accountId));
    }
}
