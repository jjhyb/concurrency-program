package lock.reentrantlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/16 21:39
 * @Description: 演示公平锁和非公平锁的情况
 */
public class FairLock {

    public static void main(String[] args) throws InterruptedException {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Job(printQueue));
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();
            Thread.sleep(100);
        }
    }

}

class Job implements Runnable{

    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"开始打印");
        printQueue.pringJob();
        System.out.println(Thread.currentThread().getName()+"打印完毕");
    }
}

class PrintQueue{
    private Lock queueLock = new ReentrantLock(true);

    public void pringJob(){
        queueLock.lock();
        try{
            int duration = new Random().nextInt(10)+1;
            System.out.println(Thread.currentThread().getName()+"正在打印，需要"+duration+"秒");
            Thread.sleep(duration*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }

        queueLock.lock();
        try{
            int duration = new Random().nextInt(10)+1;
            System.out.println(Thread.currentThread().getName()+"正在打印，需要"+duration+"秒");
            Thread.sleep(duration*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }
}