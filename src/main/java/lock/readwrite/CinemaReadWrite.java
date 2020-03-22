package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/16 22:25
 * @Description:
 */
public class CinemaReadWrite {

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public static void read(){
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"得到了读锁，并正在读取中");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放了读锁");
        }
    }

    public static void write(){
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"得到了写锁，并正在写入中");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放了写锁");
        }
    }

    public static void main(String[] args) {
        new Thread(() -> read(),"Thread-1").start();
        new Thread(() -> read(),"Thread-2").start();
        new Thread(() -> write(),"Thread-3").start();
        new Thread(() -> write(),"Thread-4").start();
    }
}
