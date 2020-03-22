package lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 22:04
 * @Description:
 */
public class LockInterruptibly implements Runnable {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        LockInterruptibly lockInterruptibly = new LockInterruptibly();
        Thread thread1 = new Thread(lockInterruptibly);
        Thread thread2 = new Thread(lockInterruptibly);
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.interrupt();//中断线程
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程获取锁");
        try {
            lock.lockInterruptibly();
            try{
                System.out.println(Thread.currentThread().getName()+"线程拿到了锁");
                Thread.sleep(3000);
            }catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"线程睡眠期间被中断了");
            }finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName()+"线程释放了锁");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"线程获取锁期间被中断了");
        }
    }
}
