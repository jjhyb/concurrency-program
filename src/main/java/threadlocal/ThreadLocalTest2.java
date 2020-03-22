package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 14:41
 * @Description: 10个线程打印日期
 */
public class ThreadLocalTest2 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            final int seconds = i;
            new Thread(new Runnable() {
                public void run() {
                    String date = new ThreadLocalTest2().date(seconds);
                    System.out.println(date);
                }
            }).start();

            Thread.sleep(100);
        }
    }

    public String date(int seconds){
        //参数的单位是毫秒，从1970.1.1 00:00:00开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
