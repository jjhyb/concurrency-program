package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/16 22:25
 * @Description:
 */
public class Upgrading {

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public static void readUpgrading(){
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"得到了读锁，并正在读取中");
            Thread.sleep(1000);
            System.out.println("升级会带来阻塞");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName()+"获取到了写锁，升级成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放了读锁");
        }
    }

    public static void writeDowngrading(){
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"得到了写锁，并正在写入中");
            Thread.sleep(1000);
            readLock.lock();
            System.out.println("在不释放写锁的情况下，直接获取读锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放了写锁");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("先演示降级是可以的");
        Thread thread1 = new Thread(() -> writeDowngrading(), "Thread-1");
        thread1.start();
        System.out.println("----------------------------------");
        thread1.join();
        System.out.println("先演示升级是不可以的");
        new Thread(() -> readUpgrading(),"Thread-2").start();
    }
}
