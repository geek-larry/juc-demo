package com.jade.queue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WorkProducerService {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String msg) {
        try {
            String result = kafkaTemplate.send("business", msg).get().toString();
            if (result != null) {
            }
        } catch (Exception e) {
        }
    }
}
