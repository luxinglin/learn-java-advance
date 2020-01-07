package cn.com.gary.thread.demo.id.api;

import cn.com.gary.thread.demo.id.service.IdGenerator;
import cn.com.gary.thread.demo.id.service.impl.AtomicIdGenerator;
import cn.com.gary.thread.demo.id.service.impl.LockIdGenerator;
import cn.com.gary.thread.demo.id.service.impl.NoSyncIdGenerator;
import cn.com.gary.thread.demo.id.service.impl.SynchronizedIdGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程模拟测试类
 *
 * @author luxinglin
 * @since 2020-01-07
 */
@Slf4j
public class IdGeneratorTest {

    /**
     * 定义并发线程数量
     */
    public static final int THREAD_NUM = 5;

    private static long startTime = 0L;

    public static void main(String[] args) {
        IdGeneratorTest apiTest = new IdGeneratorTest();
        apiTest.init();
    }

    @PostConstruct
    public void init() {
        try {
            startTime = System.currentTimeMillis();
            System.out.println("CountDownLatch started at: " + startTime);

            // 初始化计数器为1
            CountDownLatch countDownLatch = new CountDownLatch(1);
            IdGenerator idGenerator = new NoSyncIdGenerator();
            for (int i = 0; i < THREAD_NUM; i++) {
                new Thread(new Run(countDownLatch, idGenerator)).start();
            }

            IdGenerator synchronizedIdGenerator = new SynchronizedIdGenerator();
            for (int i = 0; i < THREAD_NUM; i++) {
//                new Thread(new Run(countDownLatch, synchronizedIdGenerator)).start();
            }

            IdGenerator atomicIdGenerator = new AtomicIdGenerator();
            for (int i = 0; i < THREAD_NUM; i++) {
//                new Thread(new Run(countDownLatch, atomicIdGenerator)).start();
            }

            IdGenerator lockIdGenerator = new LockIdGenerator();
            for (int i = 0; i < THREAD_NUM; i++) {
//                new Thread(new Run(countDownLatch, lockIdGenerator)).start();
            }

            // 启动多个线程
            countDownLatch.countDown();

        } catch (Exception e) {
            log.error("Exception: " + e);
        }
    }

    /**
     * 多线程模拟类
     */
    class Run implements Runnable {
        private final CountDownLatch startLatch;
        private IdGenerator idGenerator;

        public Run(CountDownLatch startLatch, IdGenerator idGenerator) {
            this.startLatch = startLatch;
            this.idGenerator = idGenerator;
        }

        @Override
        public void run() {
            try {
                // 线程等待
                startLatch.await();
                System.out.println(String.format("%s id=: %d", idGenerator.getClass().getSimpleName(), idGenerator.getNext()));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}