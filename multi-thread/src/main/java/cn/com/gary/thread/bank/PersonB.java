package cn.com.gary.thread.bank;

public class PersonB extends Thread {
    private final static double AMOUNT = 200;
    Bank bank;
    String mode;

    public PersonB(Bank bank, String mode) {
        this.bank = bank;
        this.mode = mode;
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