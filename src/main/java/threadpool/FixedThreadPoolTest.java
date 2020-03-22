package threadpool;

import java.util.concurrent.*;

/**
 * @author: huangyibo
 * @Date: 2020/2/14 20:28
 * @Description: 演示newFixedThreadPool
 */
public class FixedThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}

class Task implements Runnable{

    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

