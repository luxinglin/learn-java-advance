package cn.com.gary.thread;

import cn.com.gary.thread.bank.Bank;
import cn.com.gary.thread.bank.PersonA;
import cn.com.gary.thread.bank.PersonB;
import cn.com.gary.thread.demo.join.MyJoinThread;
import cn.com.gary.thread.demo.print.MyPrintThread;
import cn.com.gary.thread.demo.yield.MyYieldThread;
import cn.com.gary.thread.running.Animal;
import cn.com.gary.thread.running.LetOneStop;
import cn.com.gary.thread.running.Rabbit;
import cn.com.gary.thread.running.Tortoise;
import cn.com.gary.thread.ticket.Station;
import lombok.extern.slf4j.Slf4j;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 10:25
 **/
@Slf4j
public class ThreadTest {
    public static void main(String[] args) throws Throwable {
        log.info("***********Multiple thread test***********");
//        testSaleTicket();
//
//        testBank();
//
//        testRunning();
//
//        testJoin();
//
//        testYield();
//
//        testPrintThread();
//
//        ThreadPool.testThreadPool();

    }

    private static void testSaleTicket() {
        //实例化站台对象，并为每一个站台取名字
        Station station1 = new Station("窗口1");
        Station station2 = new Station("窗口2");
        Station station3 = new Station("窗口3");
        Station station4 = new Station("窗口4");
        Station station5 = new Station("窗口5");
        Station station6 = new Station("窗口6");
        Station station7 = new Station("窗口7");
        Station station8 = new Station("窗口8");
        Station station9 = new Station("窗口9");

        // 让每一个站台对象各自开始工作
        station1.start();
        station2.start();
        station3.start();
        station4.start();
        station5.start();
        station6.start();
        station7.start();
        station8.start();
        station9.start();
    }

    private static void testBank() {
        log.info("init money of bank: {} yuan!", Bank.TOTAL_MONEY);
        Bank bank = new Bank();
        // 实例化两个人，传入同一个银行的对象
        PersonA a = new PersonA(bank, Bank.MODE_Counter);
        PersonB b = new PersonB(bank, Bank.MODE_ATM);

        a.start();
        b.start();
    }

    private static void testRunning() {
        log.info("total running length: {}", Animal.TOTAL_LENGTH);
        // 实例化乌龟和兔子
        Tortoise tortoise = new Tortoise();
        Rabbit rabbit = new Rabbit();
        // 回调方法的使用，谁先调用callback方法，另一个就不跑了
        LetOneStop letOneStop1 = new LetOneStop(tortoise);
        // 让兔子的回调方法里面存在乌龟对象的值，可以把乌龟stop
        rabbit.callback = letOneStop1;
        LetOneStop letOneStop2 = new LetOneStop(rabbit);
        // 让乌龟的回调方法里面存在兔子对象的值，可以把兔子stop
        tortoise.callback = letOneStop2;
        // 开始跑
        tortoise.start();
        rabbit.start();
    }

    private static void testJoin() {
        log.info(Thread.currentThread().getName() + "主线程运行开始！");
        MyJoinThread t1 = new MyJoinThread("subA");
        MyJoinThread t2 = new MyJoinThread("subB");
        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }
        try {
            t2.join();
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }
        log.info(Thread.currentThread().getName() + "主线程运行结束！");
    }

    private static void testYield() {
        MyYieldThread t1 = new MyYieldThread("subA");
        MyYieldThread t2 = new MyYieldThread("subB");
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
    }

    private static void testPrintThread() throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        MyPrintThread pa = new MyPrintThread("A", c, a);
        MyPrintThread pb = new MyPrintThread("B", a, b);
        MyPrintThread pc = new MyPrintThread("C", b, c);

        //确保按顺序A、B、C执行
        new Thread(pa).start();
        Thread.sleep(100);
        new Thread(pb).start();
        Thread.sleep(100);
        new Thread(pc).start();
        Thread.sleep(100);
    }
}
