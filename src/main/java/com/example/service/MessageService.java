package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository msgRepo;
    private AccountRepository accRepo;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        msgRepo = messageRepository;
        accRepo = accountRepository;
    }

    public Optional<Message> addMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255 || !accRepo.existsById(message.getPostedBy())) {
            return null;
        }
        return Optional.of(msgRepo.save(message));
    }

    public Optional<List<Message>> fetchMessages() {
        return Optional.of(msgRepo.findAll());
    }

    public Optional<Message> fetchMessageById(int id) {
        return msgRepo.findById(id);
    }

    public Optional<Integer> deleteMessage(int id) {
        if (msgRepo.existsById(id)) {
            msgRepo.deleteById(id);
            return Optional.of(1);
        }
        return null;
    }

    public Optional<Integer> updateMessage(int id, Message msg) {
        if (msgRepo.existsById(id)) {
            Optional<Message> oldMsg = Optional.of(msgRepo.getById(id));
            if (oldMsg.isPresent() && msg != null && msg.getMessageText().length() <= 255 && !msg.getMessageText().isBlank()) {
                Message message = oldMsg.get();
                message.setMessageText(msg.getMessageText());
                msgRepo.save(message);
                return Optional.of(1);
            }
            else return null;
        } else return null;
    }

    public Optional<List<Message>> fetchMessagesByUser(int id) {
        return Optional.of(msgRepo.findByPostedBy(id));
    }
}
