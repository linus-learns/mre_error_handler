package mre_error_handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;


public class MreErrorHandler implements CommonErrorHandler {
    private final BookKeeper bookKeeper;

    public MreErrorHandler(final BookKeeper bookKeeper) {
        this.bookKeeper = bookKeeper;
    }
    @Override
    public boolean handleOne(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer,
                             MessageListenerContainer container) {
        bookKeeper.getCountDownLatch().countDown();
        return true;
    }
}
