package lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/16 20:57
 * @Description:
 */
public class CinemaBookSeat {

    private static ReentrantLock lock = new ReentrantLock();

    private static void bookSeat(){
        lock.lock();
        try{
            try {
                System.out.println(Thread.currentThread().getName()+"开始预订座位");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
        new Thread(() -> bookSeat()).start();
    }
}
