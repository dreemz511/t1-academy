package org.dreemz.t1academy.kafka;

import lombok.extern.slf4j.Slf4j;
import org.dreemz.t1academy.dto.KafkaTaskDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate <String, KafkaTaskDto> kafkaTemplate;
    String topic = "task_topic";

    public KafkaProducerService(KafkaTemplate<String, KafkaTaskDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(KafkaTaskDto data) {
        try {
            String key = "Task#" + data.id().toString();
            kafkaTemplate.send(topic, key, data);
            kafkaTemplate.flush();
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
