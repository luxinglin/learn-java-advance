package cn.com.gary.thread.demo.join;

import lombok.extern.slf4j.Slf4j;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 14:02
 **/
@Slf4j
public class MyJoinThread extends Thread {
    private String name;

    public MyJoinThread(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void run() {
        log.info(Thread.currentThread().getName() + " 线程运行开始!");
        for (int i = 0; i < 10; i++) {
            log.info("子线程" + name + "运行 : " + i);
            try {
                sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info(Thread.currentThread().getName() + " 线程运行结束!");
    }

}
