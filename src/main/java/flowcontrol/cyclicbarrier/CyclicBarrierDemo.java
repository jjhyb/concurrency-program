package flowcontrol.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: huangyibo
 * @Date: 2020/2/19 16:56
 * @Description: 演示CyclicBarrier的使用
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有人都到场了，大家统一出发");
            }
        });
        for (int i = 0; i < 10; i++) {
            new Thread(new Task(i,cyclicBarrier)).start();
        }
    }

    static class Task implements Runnable{

        private int id;

        private CyclicBarrier cyclicBarrier;

        public Task(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"，id："+id+"现前往集合地点");
            try {
                Thread.sleep(new Random().nextInt(10000));
                System.out.println(Thread.currentThread().getName()+"到了集合地点，开始等待其他人到达");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+"出发了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
