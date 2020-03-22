package future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: huangyibo
 * @Date: 2020/2/21 15:09
 * @Description:
 * 演示Future get过程中抛出异常，
 * for循环为了演示抛出Exception的时机，并不是说一产生异常就抛出，直到get执行时，才会抛出
 *
 */
public class FutureDemo4 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        Future<Integer> future = executorService.submit(() -> {
            throw new IllegalArgumentException("callable抛出异常");
        });

        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            System.out.println(future.isDone());

            Integer integer = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterruptedException");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("ExecutionException");
        }

        executorService.shutdown();
    }
}
