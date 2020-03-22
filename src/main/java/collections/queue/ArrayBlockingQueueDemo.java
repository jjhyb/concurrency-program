package collections.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 20:45
 * @Description:
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        Interviewer interviewer = new Interviewer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(interviewer).start();
        new Thread(consumer).start();
    }

}

class Interviewer implements Runnable{
    private BlockingQueue<String> queue;

    public Interviewer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("10个候选人都来了");
        for (int i = 0; i < 10; i++) {
            String candidate = "Candidate" + i;
            try {
                queue.put(candidate);
                System.out.println("安排好了"+candidate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            queue.put("stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable{
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            String msg;
            while (!"stop".equals(msg = queue.take())){
                System.out.println("正在面试："+msg);
            }

            System.out.println("所有候选人都结束了");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
