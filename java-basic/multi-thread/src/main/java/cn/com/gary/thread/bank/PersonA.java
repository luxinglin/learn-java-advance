package cn.com.gary.thread.bank;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-01-07 10:44
 **/
public class PersonA extends Thread {
    /**
     * 单次取钱金额
     */
    private final static double AMOUNT = 100;
    Bank bank;
    String mode;

    public PersonA(Bank bank, String mode) {
        this.mode = mode;
        this.bank = bank;
    }

    @Override
    public void run() {
        while (Bank.leftMoney >= AMOUNT) {
            try {
                bank.outMoney(AMOUNT, mode);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
