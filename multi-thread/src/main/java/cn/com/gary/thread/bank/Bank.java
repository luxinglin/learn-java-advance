package cn.com.gary.thread.bank;

import cn.com.gary.util.number.AccuracyUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 两个人AB通过一个账户A在柜台取钱和B在ATM机取钱！
 *
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 10:42
 **/
@Slf4j
public class Bank {
    public static final String MODE_ATM = "ATM";
    public static final String MODE_Counter = "Counter";

    public static final double TOTAL_MONEY = 1000.00103;
    /**
     * 假设一个账户有1000块钱
     */
    static double leftMoney = TOTAL_MONEY;

    /**
     * 柜台Counter取钱的方法
     */
    private void Counter(double money) {
        Bank.leftMoney = AccuracyUtil.sub(Bank.leftMoney, money);
        log.info("柜台取钱" + money + "元，还剩" + Bank.leftMoney + "元！");
    }

    /**
     * ATM取钱的方法
     */
    private void ATM(double money) {
        Bank.leftMoney = AccuracyUtil.sub(Bank.leftMoney, money);
        log.info("ATM取钱" + money + "元，还剩" + Bank.leftMoney + "元！");
    }

    /**
     * 提供一个对外取款途径，防止直接调取方法同时取款时，并发余额显示错误
     */
    public synchronized void outMoney(double money, String mode) throws Exception {
        if (money > Bank.leftMoney) {
            //校验余额是否充足
            throw new Exception("取款金额" + money + ",余额只剩" + Bank.leftMoney + "，取款失败");
        }

        if (Objects.equals(MODE_ATM, mode)) {
            ATM(money);
        } else {
            Counter(money);
        }
    }
}
