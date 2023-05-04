package mre_error_handler;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class BookKeeper {
    CountDownLatch countDownLatch = new CountDownLatch(6);

    public BookKeeper() {}

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}
