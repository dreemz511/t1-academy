package org.dreemz.t1academy.kafka;

import lombok.extern.slf4j.Slf4j;
import org.dreemz.t1academy.dto.KafkaTaskDto;
import org.dreemz.t1academy.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {
    private final NotificationService notificationService;


    public KafkaConsumerService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            id = "${dreemz.kafka.consumer.group-id}",
            topics = "${dreemz.kafka.topic.name}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload KafkaTaskDto data, Acknowledgment ack) {
        try {
            log.info("Received data: {}", data);
            notificationService.sendSimpleEmail(data);
        }
        catch (KafkaException ex) {
            log.warn(ex.getMessage());
        }
        finally {
            ack.acknowledge();
        }

    }
}
