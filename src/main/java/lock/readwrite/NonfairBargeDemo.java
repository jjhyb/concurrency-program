package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/16 22:51
 * @Description:
 */
public class NonfairBargeDemo {

    //公平锁，读锁无法插队，非公平锁，读锁可以插队
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);

    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public static void read(){
        System.out.println(Thread.currentThread().getName()+"开始尝试获取读锁");
        readLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"得到读锁正在读取");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放读锁");
        }
    }

    public static void write(){
        System.out.println(Thread.currentThread().getName()+"开始尝试获取写锁");
        writeLock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"得到写锁正在写入");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放写锁");
        }
    }

    public static void main(String[] args) {
        new Thread(() -> write(),"Thread-1").start();
        new Thread(() -> read(),"Thread-2").start();
        new Thread(() -> read(),"Thread-3").start();
        new Thread(() -> write(),"Thread-4").start();
        new Thread(() -> read(),"Thread-5").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread[] threads = new Thread[1000];
                for (int i = 0; i < 1000; i++) {
                    threads[i] = new Thread(() -> read(),"子线程创建的Thread" + i);
                }

                for (int i = 0; i < 1000; i++) {
                    threads[i].start();
                }
            }
        }).start();
    }
}
