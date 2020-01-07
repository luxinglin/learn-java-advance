package cn.com.gary.thread.demo.id.facade;

import javafx.print.Printer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 打印机服务类
 * 信号量。<br>
 * 信号量一般用来数量有限的资源，每类资源有一个对象的信号量，信号量的值表示资源的可用数量。
 * <p>
 * 在使用资源时，需要从该信号量上获取许可，成功获取许可，资源的可用数-1；完成对资源的使用，释放许可，资源可用数+1； 当资源数为0时，
 * 需要获取资源的线程以阻塞的方式来等待资源，或过段时间之后再来检查资源是否可用。（上面的SimpleResourceManager类实际上时信号量的一个简单实现）
 * <p>
 * java.util.concurrent.Semaphore类，在创建Semaphore类的对象时指定资源的可用数
 * <p>
 * a、acquire()，以阻塞方式获取许可
 * <p>
 * b、tryAcquire()，以非阻塞方式获取许可
 * <p>
 * c、release()，释放许可。
 * <p>
 * d、accquireUninterruptibly()，accquire()方法获取许可以的过程可以被中断，如果不希望被中断，使用此方法。
 *
 * @author luxinglin
 * @since 2020-01-07
 */
@Slf4j
public class PrinterManager {
    private final Semaphore semaphore;
    private final List<Printer> printers = new ArrayList<>();

    public PrinterManager(Collection<? extends Printer> printers) {
        this.printers.addAll(printers);
        //这里重载方法，第二个参数为true，以公平竞争模式，防止线程饥饿
        this.semaphore = new Semaphore(this.printers.size(), true);
    }

    public Printer acquirePrinter() throws InterruptedException {
        semaphore.acquire();
        Printer printer = getAvailablePrinter();
        log.info("acquire printer {}", printer.getName());
        return printer;
    }

    public void releasePrinter(Printer printer) {
        log.info("release printer {}", printer.getName());
        putBackPrinter(printer);
        semaphore.release();
    }

    private synchronized Printer getAvailablePrinter() {
        Printer result = printers.get(0);
        printers.remove(0);
        return result;
    }

    private synchronized void putBackPrinter(Printer printer) {
        printers.add(printer);
    }

}