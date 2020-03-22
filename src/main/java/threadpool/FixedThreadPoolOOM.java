package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: huangyibo
 * @Date: 2020/2/14 20:40
 * @Description: 演示newFixedThreadPool内存占用过多的情况
 */
public class FixedThreadPoolOOM {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        for(int i=0;i<Integer.MAX_VALUE;i++){
            executorService.execute(new SubThread());
        }
    }
}

class SubThread implements Runnable{

    public void run() {
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
