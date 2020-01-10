package cn.com.gary.thread.demo.id.service.impl;

import cn.com.gary.thread.demo.id.service.IdGenerator;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 16:05
 **/
public class SynchronizedIdGenerator implements IdGenerator {
    private int value = 0;

    @Override
    public synchronized int getNext() {
        try {
            Thread.sleep(100);
        } catch (Exception ex) {
        }
        return value++;
    }

    public int getNextV2() {
        synchronized (this) {
            return value++;
        }
    }

}
