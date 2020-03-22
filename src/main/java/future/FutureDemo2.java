package future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author: huangyibo
 * @Date: 2020/2/21 15:09
 * @Description: 演示Future的使用方法，lambda表达式方法
 */
public class FutureDemo2 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<Integer> callable = () -> {
            Thread.sleep(3000);
            return new Random().nextInt();
        };
        Future<Integer> future = executorService.submit(callable);
        try {
            Integer integer = future.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
