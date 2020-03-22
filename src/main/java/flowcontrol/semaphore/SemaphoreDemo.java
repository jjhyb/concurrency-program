package flowcontrol.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: huangyibo
 * @Date: 2020/2/19 15:31
 * @Description: 演示Semaphore用法
 */
public class SemaphoreDemo {

    public static Semaphore semaphore = new Semaphore(3,true);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(50);

        for (int i = 0; i < 100; i++) {
            executorService.execute(new Task());
        }
        executorService.shutdown();
    }

    static class Task implements Runnable{

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+"拿到了许可证");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"释放了许可证");
                semaphore.release();
            }
        }
    }
}


