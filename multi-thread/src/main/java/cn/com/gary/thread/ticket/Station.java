package cn.com.gary.thread.ticket;

import lombok.extern.slf4j.Slf4j;

/**
 * 站台多窗口售票问题
 *
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 10:21
 **/
@Slf4j
public class Station extends Thread {
    /**
     * 为了保持票数的一致，票数要静态
     */
    static int tick = 20;
    /**
     * 共享锁
     */
    static Object ob = "ticket_lock";

    /**
     * 通过构造方法给线程名字赋值
     */
    public Station(String name) {
        super(name);
    }

    /**
     * 重写run方法，实现买票操作
     */
    @Override
    public void run() {
        while (tick > 0) {
            // 这个很重要，必须使用一个锁，
            synchronized (ob) {
                // 进去的人会把钥匙拿在手上，出来后才把钥匙拿让出来
                if (tick > 0) {
                    log.info(getName() + "卖出了第" + tick + "张票");
                    tick--;
                } else {
                    log.info("票卖完了");
                }
            }
            try {
                //休息50ms
                sleep(500);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
