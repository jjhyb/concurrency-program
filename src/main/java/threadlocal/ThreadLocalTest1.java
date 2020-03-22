package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 14:41
 * @Description: 两个线程打印日期
 */
public class ThreadLocalTest1 {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                String date = new ThreadLocalTest1().date(10);
                System.out.println(date);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                String date = new ThreadLocalTest1().date(1007);
                System.out.println(date);
            }
        }).start();
    }

    public String date(int seconds){
        //参数的单位是毫秒，从1970.1.1 00:00:00开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
