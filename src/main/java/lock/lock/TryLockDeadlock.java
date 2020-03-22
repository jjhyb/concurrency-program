package lock.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 21:40
 * @Description: 用tryLock来避免死锁
 */
public class TryLockDeadlock implements Runnable {

    int flag = 1;
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        TryLockDeadlock deadlock1 = new TryLockDeadlock();
        TryLockDeadlock deadlock2 = new TryLockDeadlock();

        deadlock1.flag = 1;
        deadlock1.flag = 2;

        new Thread(deadlock1).start();
        new Thread(deadlock2).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(flag == 1){
                try {
                    if(lock1.tryLock(800, TimeUnit.MILLISECONDS)){
                        try{
                            System.out.println(Thread.currentThread().getName()+"线程获取到了锁1");
                            Thread.sleep(new Random().nextInt(1000));

                            if(lock2.tryLock(800,TimeUnit.MILLISECONDS)){
                                try{
                                    System.out.println(Thread.currentThread().getName()+"线程获取到了锁2");
                                    System.out.println(Thread.currentThread().getName()+"线程成功获取到了2把锁");
                                    break;
                                }finally {
                                    lock2.unlock();
                                }
                            }else {
                                System.out.println(Thread.currentThread().getName()+"线程获取锁2失败，已重试");
                            }
                        }finally {
                            lock1.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    }else {
                        System.out.println(Thread.currentThread().getName()+"线程获取锁1失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    if(lock2.tryLock(800, TimeUnit.MILLISECONDS)){
                        try{
                            System.out.println(Thread.currentThread().getName()+"线程获取到了锁2");
                            Thread.sleep(new Random().nextInt(3000));

                            if(lock1.tryLock(800,TimeUnit.MILLISECONDS)){
                                try{
                                    System.out.println(Thread.currentThread().getName()+"线程获取到了锁1");
                                    System.out.println(Thread.currentThread().getName()+"线程成功获取到了2把锁");
                                    break;
                                }finally {
                                    lock1.unlock();
                                }
                            }else {
                                System.out.println(Thread.currentThread().getName()+"线程获取锁1失败，已重试");
                            }
                        }finally {
                            lock2.unlock();
                            Thread.sleep(new Random().nextInt(3000));
                        }
                    }else {
                        System.out.println(Thread.currentThread().getName()+"线程获取锁2失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
