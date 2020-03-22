package threadpool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: huangyibo
 * @Date: 2020/2/14 22:44
 * @Description: 演示每隔任务执行的前后放钩子函数
 */
public class PauseThreadPool extends ThreadPoolExecutor {

    private boolean isPaused;
    private final ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public PauseThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public PauseThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public PauseThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public PauseThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        lock.lock();
        try {
            while(isPaused){
                //休眠线程
                condition.wait();
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 暂停函数
     */
    public void pause(){
        lock.lock();
        try{
            isPaused = true;
        }finally {
            lock.unlock();
        }
    }

    /**
     * 恢复函数
     */
    public void resume(){
        lock.lock();
        try{
            isPaused = false;
            //唤醒线程
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PauseThreadPool pauseThreadPool =
                new PauseThreadPool(10, 20, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("我被执行了");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 10000; i++) {
            pauseThreadPool.execute(runnable);
        }

        Thread.sleep(1500);
        pauseThreadPool.pause();
        System.out.println("线程池被暂停了");

        Thread.sleep(1500);
        pauseThreadPool.resume();
        System.out.println("线程池被恢复了");
    }
}
