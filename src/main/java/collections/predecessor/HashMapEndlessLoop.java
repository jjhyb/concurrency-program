package collections.predecessor;

import java.util.HashMap;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 16:14
 * @Description: HashMap在高并发下的死循环(仅在JDK7以前存在)
 */
public class HashMapEndlessLoop {

    public static HashMap<Integer,String> hashMap = new HashMap<>(2,1.5f);

    public static void main(String[] args) {
        hashMap.put(5,"C");
        hashMap.put(7,"B");
        hashMap.put(3,"A");

        new Thread(new Runnable() {
            @Override
            public void run() {
                hashMap.put(15,"D");
                System.out.println(hashMap);
            }
        },"Thread-1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                hashMap.put(1,"E");
                System.out.println(hashMap);
            }
        },"Thread-2").start();
    }
}
