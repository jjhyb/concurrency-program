package atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author: huangyibo
 * @Date: 2020/2/17 14:58
 * @Description: 演示原子数组的使用方法
 */
public class AtomicArrayDemo1 {

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);
        Thread[] threads1 = new Thread[100];
        Thread[] threads2 = new Thread[100];
        Decrement decrement = new Decrement(atomicIntegerArray);
        Increment increment = new Increment(atomicIntegerArray);
        for (int i = 0; i < 100; i++) {
            threads1[i] = new Thread(decrement);
            threads2[i] = new Thread(increment);

            threads1[i].start();
            threads2[i].start();
        }

        for (int i = 0; i < 100; i++) {
            threads1[i].join();
            threads2[i].join();
        }

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if(atomicIntegerArray.get(i) != 0){
                System.out.println("发现了错误：错误的索引："+i);
            }
        }
        System.out.println("运行结束");
    }

}

class Decrement implements Runnable{

    private AtomicIntegerArray array;

    public Decrement(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndDecrement(i);
        }
    }
}

class Increment implements Runnable{

    private AtomicIntegerArray array;

    public Increment(AtomicIntegerArray array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndIncrement(i);
        }
    }
}