package flowcontrol.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/19 16:14
 * @Description: 演示Condition的基本用法
 */
public class ConditionDemo1 {

    public ReentrantLock lock = new ReentrantLock();

    public Condition condition = lock.newCondition();

    public void method1(){
        lock.lock();
        try{
            System.out.println("条件不满足，开始await");
            condition.await();
            System.out.println("条件满足，开始执行后续任务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void method2(){
        lock.lock();
        try{
            System.out.println("准备工作完成，开始唤醒其他线程");
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionDemo1 conditionDemo1 = new ConditionDemo1();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    conditionDemo1.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        conditionDemo1.method1();
    }
}
