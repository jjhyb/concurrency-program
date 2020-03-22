package future;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author: huangyibo
 * @Date: 2020/2/21 15:09
 * @Description: 演示批量提交任务时，用List来批量获取结果
 */
public class FutureDemo3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        ArrayList<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Future<Integer> future = executorService.submit(() -> {
                Thread.sleep(3000);
                return new Random().nextInt();
            });
            list.add(future);
        }

        for (int i = 0; i < list.size(); i++) {
            Future<Integer> future = list.get(i);
            System.out.println(future.get());
        }

        executorService.shutdown();
    }
}
