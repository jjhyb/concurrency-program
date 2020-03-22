package threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: huangyibo
 * @Date: 2020/2/14 21:03
 * @Description:
 */
public class ScheduledThreadPoolTest {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        //延迟5秒执行
        scheduledExecutorService.schedule(new Task(),5, TimeUnit.SECONDS);

        //启动后1秒钟执行任务，之后每隔5秒钟执行一次任务
        scheduledExecutorService.scheduleAtFixedRate(new Task(),1,3,TimeUnit.SECONDS);


    }
}
