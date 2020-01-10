package cn.com.gary.thread.demo.id.facade;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * 对象交换器<br>
 * 适合于两个线程需要进行数据交换的场景。（一个线程完成后，把结果交给另一个线程继续处理）
 * java.util.concurrent.Exchanger类，提供了这种对象交换能力，两个线程共享一个Exchanger类的对象，一个线程完成对数据的处理之后，
 * 调用Exchanger类的exchange()方法把处理之后的数据作为参数发送给另外一个线程。而exchange方法的返回结果是另外一个线程锁提供的相同类型的对象。
 * 如果另外一个线程未完成对数据的处理，那么exchange()会使当前线程进入等待状态，直到另外一个线程也调用了exchange方法来进行数据交换。
 *
 * @author luxinglin
 * @since 2020-01-07
 */
@Slf4j
public class SendAndReceiver {
    private final Exchanger<StringBuilder> exchanger = new Exchanger<StringBuilder>();

    public void exchange() {
        new Thread(new Sender()).start();
        new Thread(new Receiver()).start();
    }

    private class Sender implements Runnable {
        @Override
        public void run() {
            try {
                StringBuilder content = new StringBuilder("Hello");
                content = exchanger.exchange(content);
                log.info("Sender {}", content);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class Receiver implements Runnable {
        @Override
        public void run() {
            try {
                StringBuilder content = new StringBuilder("World");
                content = exchanger.exchange(content);
                log.info("Receiver {}", content);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}