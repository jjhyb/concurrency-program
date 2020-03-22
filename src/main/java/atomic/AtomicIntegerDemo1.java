package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: huangyibo
 * @Date: 2020/2/17 14:45
 * @Description: 演示AtomicInteger的基本用法，对比非原子类的线程安全问题，
 *                使用了原子类之后不需要加锁，也可以保证线程安全
 *
 */
public class AtomicIntegerDemo1 implements Runnable {

    public static final AtomicInteger atomicInteger = new AtomicInteger();

    public void incrementAtomic(){
        atomicInteger.getAndIncrement();
    }

    public static volatile int basicCount = 0;

    /**
     * 普通变量必须要加锁才能保证线程安全问题
     */
    public synchronized void incrementBasic(){
        basicCount++;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo1 atomicIntegerDemo1 = new AtomicIntegerDemo1();
        Thread thread1 = new Thread(atomicIntegerDemo1);
        Thread thread2 = new Thread(atomicIntegerDemo1);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("原子类的结果："+atomicInteger.get());
        System.out.println("普通变量的结果："+basicCount);
    }
}
