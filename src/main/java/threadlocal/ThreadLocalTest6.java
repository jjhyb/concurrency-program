package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 14:41
 * @Description: 1000个打印日期的任务，用线程池来执行 使用ThreadLocal来解决线程安全问题
 */
public class ThreadLocalTest6 {

    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            final int seconds = i;
            executorService.submit(new Runnable() {
                public void run() {
                    String date = new ThreadLocalTest6().date(seconds);
                    System.out.println(date);
                }
            });
//            executorService.shutdown();
        }
    }

    public String date(int seconds){
        //参数的单位是毫秒，从1970.1.1 00:00:00开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.dateFormatThreadLocal2.get();
        return simpleDateFormat.format(date);
    }
}

class ThreadSafeFormatter{
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            //初始化SimpleDateFormat
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2 =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
}