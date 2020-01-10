package cn.com.gary.thread.running;

import lombok.extern.slf4j.Slf4j;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 10:56
 **/
@Slf4j
public class Tortoise extends Animal {
    public Tortoise() {
        setName("乌龟");
    }

    @Override
    public void running() {
        // 乌龟速度
        int dis = 2;
        leftLength -= dis;
        log.info("乌龟跑了" + dis + "米，距离终点还有" + leftLength + "米");
        if (leftLength <= 0) {
            leftLength = 0;
            log.info("乌龟获得了胜利");
            // 让兔子不要在跑了
            if (callback != null) {
                callback.win();
            }
        }
        try {
            //没0.1秒跑2米
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
