package cn.com.gary.thread.running;

import lombok.extern.slf4j.Slf4j;

/**
 * 兔子
 *
 * @author luxinglin
 * @since 2020-01-07
 */
@Slf4j
public class Rabbit extends Animal {
    /**
     * 兔子缓冲休息米数（即每跑x米，休息一次）
     */
    final int BUFFER_LENGTH = 20;

    public Rabbit() {
        setName("兔子");
    }

    @Override
    public void running() {
        //兔子速度
        int dis = 5;
        leftLength -= dis;

        log.info("兔子跑了" + dis + "米，距离终点还有" + leftLength + "米");
        if (leftLength <= 0) {
            leftLength = 0;
            log.info("兔子获得了胜利");
            // 给回调对象赋值，让乌龟不要再跑了
            if (callback != null) {
                callback.win();
            }
        }

        try {
            // 每20米休息一次,休息时间是1秒
            if ((TOTAL_LENGTH - leftLength) % BUFFER_LENGTH == 0) {
                sleep(1000);
            }

            //每0.1秒跑5米
            else {
                sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}