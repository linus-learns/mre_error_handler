package mre_error_handler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

//@Testcontainers
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class MreErrorHandlerApplicationTests {
	@Autowired
	KafkaTemplate<String, String> kafkaProducer;
	@Autowired
	BookKeeper bookKeeper;

	@Test
	public void errorHandledShouldNotPauseConsumer() throws Exception {
		for (int i = 0; i < 6; i++) {
			kafkaProducer.send("test_topic", 0, null, "message contents").get(1, TimeUnit.SECONDS);
		}
		bookKeeper.getCountDownLatch().await();
	}

}
