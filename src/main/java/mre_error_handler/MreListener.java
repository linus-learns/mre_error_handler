package mre_error_handler;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MreListener {
    private final BookKeeper bookKeeper;

    @KafkaListener(id = "listener-test_topic",
                   topics = "test_topic",
                   autoStartup = "true",
                   containerFactory = "containerFactory")
    public void onTestTopic(final ConsumerRecord<byte[], byte[]> record,
                              final Acknowledgment acknowledgment) {
        accept(record, acknowledgment);
    }

    private void accept(final ConsumerRecord<byte[], byte[]> record,
                        final Acknowledgment acknowledgment) {
        if (record.offset() == 1) {
            throw new RuntimeException("Exception for error handler");
        } else {
            bookKeeper.getCountDownLatch().countDown();
            acknowledgment.acknowledge();
        }
    }
}
