package cn.com.gary.thread.demo.id.service.impl;

import cn.com.gary.thread.demo.id.service.IdGenerator;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用lock机制，确保多线程环境下获取id不重复
 *
 * @author luxinglin
 */
public class LockIdGenerator implements IdGenerator {
    private int value = 0;
    //new ReentrantLock(true)是重载，使用更加公平的加锁机制，在锁被释放后，会优先给等待时间最长的线程，避免一些线程长期无法获得锁
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public int getNext() {


        //进来就加锁，没有锁会等待
        lock.lock();
        try {
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
            }

            //实际操作
            return value++;
        } finally {
            lock.unlock();//释放锁
        }
    }
}
