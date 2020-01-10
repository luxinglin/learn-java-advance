package cn.com.gary.thread.demo.id.service.impl;

import cn.com.gary.thread.demo.id.service.IdGenerator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 15:32
 **/
@Slf4j
public class NoSyncIdGenerator implements IdGenerator {
    private int id = 0;

    @Override
    public int getNext() {
        try {
            Thread.sleep(100);
        } catch (Exception ex) {
        }
        return id++;
    }
}
