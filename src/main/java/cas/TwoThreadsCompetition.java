package cas;

/**
 * @author: huangyibo
 * @Date: 2020/2/17 17:20
 * @Description: 模拟CAS操作，等价代码
 */
public class TwoThreadsCompetition implements Runnable {

    private volatile int value;

    public synchronized int compareAndSwap(int expectedValue,int newValue){
        int oldValue = value;
        if(oldValue == expectedValue){
            value = newValue;
        }
        return oldValue;
    }

    public static void main(String[] args) throws InterruptedException {
        TwoThreadsCompetition r = new TwoThreadsCompetition();
        r.value = 0;
        Thread thread1 = new Thread(r,"Thread-1");
        Thread thread2 = new Thread(r,"Thread-2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread1.join();
        System.out.println(r.value);
    }

    @Override
    public void run() {
        compareAndSwap(0,1);
    }
}
