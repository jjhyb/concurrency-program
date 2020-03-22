package flowcontrol.condition;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/19 16:23
 * @Description: 演示Condition实现生产者和消费者模式
 */
public class ConditionDemo2 {

    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);

    private ReentrantLock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo2 conditionDemo2 = new ConditionDemo2();
        Producer producer = conditionDemo2.new Producer();
        Consumer consumer = conditionDemo2.new Consumer();

        new Thread(producer).start();
        new Thread(consumer).start();
    }

    class Consumer implements Runnable{
        @Override
        public void run() {
            consume();
        }

        public void consume(){
            while(true){
                lock.lock();
                try{
                    while (queue.size() == 0){
                        System.out.println("队列空，等待数据");
                        notEmpty.await();
                    }
                    Integer poll = queue.poll();//走过await()证明队列不为空，取出数据
                    System.out.println("消费者消费数据："+poll+"，队列剩余数据数量："+queue.size());

                    notFull.signalAll();//获取数据之后，队列肯定有空闲，那么唤醒生产者进行生产
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Producer implements Runnable{
        @Override
        public void run() {
            produce();
        }

        public void produce(){
            while(true){
                lock.lock();
                try{
                    while (queue.size() == queueSize){
                        System.out.println("队列已满，等待空余");
                        notFull.await();
                    }
                    queue.offer(1);//走过await()证明队列有空闲，开始往队列里生产数据
                    System.out.println("生产者向队列生产一个数据，队列剩余空间："+(queueSize-queue.size()));

                    notEmpty.signalAll();//向队列生产数据之后，队列不为空，那么唤醒消费者进行消费
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
