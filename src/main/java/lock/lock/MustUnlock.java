package lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 21:29
 * @Description:
 * lock()不会像synchronized一样在异常的时候自动释放锁
 * 因此最佳实践是，在finally中释放锁，以保证发生异常时锁一定被释放
 */
public class MustUnlock {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try{
            //获取本锁保护的资源
            System.out.println(Thread.currentThread().getName());
        }finally {
            lock.unlock();
        }
    }
}
