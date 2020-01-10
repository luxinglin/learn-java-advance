package cn.com.gary.thread.demo.id.api;

import cn.com.gary.thread.demo.id.facade.PrinterManager;
import javafx.print.Printer;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟公司内部打印机共享程序
 *
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 16:48
 **/
@Slf4j
public class SemaphoreTest {
    /**
     * 定义并发线程数量
     */
    public static final int THREAD_NUM = 14;

    private static long startTime = 0L;

    public static void main(String[] args) {
        SemaphoreTest apiTest = new SemaphoreTest();
        apiTest.init();
    }

    @PostConstruct
    public void init() {
        try {
            startTime = System.currentTimeMillis();
            System.out.println("CountDownLatch started at: " + startTime);

            // 初始化计数器为1
            CountDownLatch countDownLatch = new CountDownLatch(1);

            PrinterManager printerManager = new PrinterManager(Printer.getAllPrinters());
            for (int i = 0; i < THREAD_NUM; i++) {
                new Thread(new SemaphoreTest.Run(countDownLatch, printerManager)).start();
            }
            countDownLatch.countDown();

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * 多线程模拟类
     */
    class Run implements Runnable {
        private final CountDownLatch startLatch;
        private PrinterManager printerManager;

        public Run(CountDownLatch startLatch, PrinterManager printerManager) {
            this.startLatch = startLatch;
            this.printerManager = printerManager;
        }

        @Override
        public void run() {
            try {
                // 线程等待
                startLatch.await();
                Printer printer = printerManager.acquirePrinter();

                //模拟打印时长
                Thread.sleep(new Random().nextInt(10 * 1000));

                printerManager.releasePrinter(printer);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
