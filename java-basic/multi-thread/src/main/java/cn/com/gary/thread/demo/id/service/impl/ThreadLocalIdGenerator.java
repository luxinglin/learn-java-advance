package cn.com.gary.thread.demo.id.service.impl;

/**
 * 线程局部变量，把一个共享变量变为一个线程的私有对象。
 * 不同线程访问一个ThreadLocal类的对象时，锁访问和修改的是每个线程变量各自独立的对象。
 * 通过ThreadLocal可以快速把一个非线程安全的对象转换成线程安全的对象。
 */
public class ThreadLocalIdGenerator {
    private static final ThreadLocal<NoSyncIdGenerator> idGenerator = new ThreadLocal<NoSyncIdGenerator>() {
        @Override
        protected NoSyncIdGenerator initialValue() {
            return new NoSyncIdGenerator();
        }
    };

    public static int getNext() {
        return idGenerator.get().getNext();
    }
}