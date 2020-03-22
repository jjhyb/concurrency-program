package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 14:41
 * @Description: 1000个打印日期的任务，用线程池来执行
 */
public class ThreadLocalTest4 {

    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            final int seconds = i;
            executorService.submit(new Runnable() {
                public void run() {
                    String date = new ThreadLocalTest4().date(seconds);
                    System.out.println(date);
                }
            });
            executorService.shutdown();
        }
    }

    public String date(int seconds){
        //参数的单位是毫秒，从1970.1.1 00:00:00开始计时
        Date date = new Date(1000 * seconds);
        return simpleDateFormat.format(date);
    }
}
