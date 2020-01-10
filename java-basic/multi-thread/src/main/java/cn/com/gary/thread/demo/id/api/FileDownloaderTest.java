package cn.com.gary.thread.demo.id.api;

import cn.com.gary.thread.demo.id.facade.FileDownloader;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 17:42
 **/
@Slf4j
public class FileDownloaderTest {
    public static void main(String[] args) throws Throwable {
        FileDownloader fileDownloader = new FileDownloader();
        //idea 官方下载地址
        URL url = new URL("https://download.jetbrains.8686c.com/idea/ideaIU-2019.3.1.exe");
        //默认存放地址
        Path path = Paths.get("/data/multi-thread/ideaIU-2019.3.1.exe");
        boolean ok = false;
        while (!ok) {
            ok = fileDownloader.download(url, path);
        }
        fileDownloader.close();
    }
}
