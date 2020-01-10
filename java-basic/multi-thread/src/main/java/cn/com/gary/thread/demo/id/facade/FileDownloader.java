package cn.com.gary.thread.demo.id.facade;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.*;

/**
 * 多线程下载
 *
 * @author luxinglin
 * @since 2020-01-07
 */
public class FileDownloader {
    /**
     * 线程池
     */
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * 文件下载类
     *
     * @param url  文件的网络链接
     * @param path 下载文件存放完整路径
     * @return
     */
    public boolean download(final URL url, final Path path) {
        //submit提交任务
        Future<Path> future = executor.submit(new Callable<Path>() {
            @Override
            public Path call() throws IOException {
                //这里就省略IOException的处理了
                InputStream is = url.openStream();
                Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
                return path;
            }
        });

        try {
            return future.get() != null ? true : false;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    /***
     * 当不再使用FileDownloader类的对象时，应该使用close方法关闭其中包含的ExecutorService接口的实现对象，否则虚拟机不会退出，占用内存不释放
     */
    public void close() {
        // 发出关闭请求，此时不会再接受新任务
        executor.shutdown();
        try {
            // awaitTermination 来等待一段时间，使正在执行的任务或等待的任务有机会完成
            if (!executor.awaitTermination(3, TimeUnit.MINUTES)) {
                // 如果等待时间过后还有任务没完成，则强制结束
                executor.shutdownNow();
                // 再等待一段时间，使被强制结束的任务完成必要的清理工作
                executor.awaitTermination(1, TimeUnit.MINUTES);
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}