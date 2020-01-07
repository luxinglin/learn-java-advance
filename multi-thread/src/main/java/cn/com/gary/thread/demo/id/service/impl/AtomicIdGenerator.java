package cn.com.gary.thread.demo.id.service.impl;

import cn.com.gary.thread.demo.id.service.IdGenerator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 采用原始的int值，确保多线程获取id不重复
 *
 * @author luxinglin
 */
public class AtomicIdGenerator implements IdGenerator {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public int getNext() {
        try {
            Thread.sleep(100);
        } catch (Exception ex) {
        }
        return counter.getAndIncrement();
    }
}
