package flowcontrol.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 22:05
 * @Description: 模拟100米跑步，5名选手都准备好了，只等裁判员一声令下，所有人同时开跑
 *                  当所有人都到终点后，比赛结束
 */
public class CountDownLatchDemo3 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i+1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("No." + no + "，准备完毕，等待发令枪");
                    try {
                        begin.await();
                        System.out.println("No." + no + "，开始跑步");
                        Thread.sleep(new Random().nextInt(10000));
                        System.out.println("No." + no + "，跑到终点了");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        end.countDown();
                    }
                }
            };
            executorService.submit(runnable);
        }

        //裁判员检查发令枪......
        Thread.sleep(5000);
        System.out.println("发令枪响，比赛开始......");
        begin.countDown();

        //等待5个线程都执行完毕之后
        end.await();
        System.out.println("所有人到达终点，比赛结束");
    }
}
