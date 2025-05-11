package org.dreemz.t1academy.kafka;

import lombok.extern.slf4j.Slf4j;
import org.dreemz.t1academy.dto.KafkaTaskDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(id = "task_group", topics = "task_topic", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload KafkaTaskDto data, Acknowledgment ack) {
        log.info("Received data: {}", data);
        ack.acknowledge();
    }
}
