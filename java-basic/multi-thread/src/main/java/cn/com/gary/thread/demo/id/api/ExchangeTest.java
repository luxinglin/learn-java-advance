package cn.com.gary.thread.demo.id.api;

import cn.com.gary.thread.demo.id.facade.SendAndReceiver;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 16:48
 **/
@Slf4j
public class ExchangeTest {
    /**
     * 定义并发线程数量
     */
    public static final int THREAD_NUM = 10;

    private static long startTime = 0L;

    public static void main(String[] args) {
        ExchangeTest apiTest = new ExchangeTest();
        apiTest.init();
    }

    @PostConstruct
    public void init() {
        try {
            startTime = System.currentTimeMillis();
            System.out.println("CountDownLatch started at: " + startTime);

            // 初始化计数器为1
            CountDownLatch countDownLatch = new CountDownLatch(1);
            SendAndReceiver sendAndReceiver = new SendAndReceiver();
            for (int i = 0; i < THREAD_NUM; i++) {
                new Thread(new ExchangeTest.Run(countDownLatch, sendAndReceiver)).start();
            }
            countDownLatch.countDown();

        } catch (Exception ex) {
        }
    }

    /**
     * 多线程模拟类
     */
    class Run implements Runnable {
        private final CountDownLatch startLatch;
        private SendAndReceiver sendAndReceiver;

        public Run(CountDownLatch startLatch, SendAndReceiver sendAndReceiver) {
            this.startLatch = startLatch;
            this.sendAndReceiver = sendAndReceiver;
        }

        @Override
        public void run() {
            try {
                // 线程等待
                startLatch.await();
                sendAndReceiver.exchange();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
