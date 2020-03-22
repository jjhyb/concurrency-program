package future;

import java.util.concurrent.*;

/**
 * @author: huangyibo
 * @Date: 2020/2/21 16:23
 * @Description: 演示FutureTask的用法,线程池方式
 */
public class FutureTaskDemo2 {

    public static void main(String[] args) {
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(task);
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class Task implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            System.out.println("子线程正在计算");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
