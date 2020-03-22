package collections.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 17:32
 * @Description: ConcurrentHashMap组合操作，并不保证线程安全
 */
public class ConcurrentHashMapDemo implements Runnable {

    public static ConcurrentHashMap<String,Integer> concurrentHashMap = new ConcurrentHashMap();

    public static void main(String[] args) throws InterruptedException {
        concurrentHashMap.put("小明",0);
        Thread thread1 = new Thread(new ConcurrentHashMapDemo());
        Thread thread2 = new Thread(new ConcurrentHashMapDemo());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(concurrentHashMap);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            while (true){
                Integer score = concurrentHashMap.get("小明");
                int newScore = score + 1;

                boolean replace = concurrentHashMap.replace("小明", score, newScore);
                if(replace){
                    break;
                }
            }
        }
    }
}
