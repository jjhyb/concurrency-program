package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author: huangyibo
 * @Date: 2020/2/17 15:44
 * @Description: 演示AtomicIntegerFieldUpdater用法
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable {

    static Candidate tom;
    static Candidate peter;

    public static AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            peter.score++;
            scoreUpdater.getAndIncrement(tom);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        tom = new Candidate();
        peter = new Candidate();
        AtomicIntegerFieldUpdaterDemo updaterDemo = new AtomicIntegerFieldUpdaterDemo();
        Thread thread1 = new Thread(updaterDemo);
        Thread thread2 = new Thread(updaterDemo);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("普通变量自增："+peter.score);
        System.out.println("AtomicIntegerFieldUpdater操作的变量："+tom.score);
    }

    public static class Candidate{
        volatile int score;
    }
}
