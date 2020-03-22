package threadlocal;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 20:18
 * @Description:
 */
public class ThreadLocalNPE {

    ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    public void set(){
        threadLocal.set(Thread.currentThread().getId());
    }

    /**
     * 这里的返回值使用long的时候，如果没有set()就调用get()那么会报空指针异常，因为牵涉到拆箱转换(将对象类型转换为基本类型)
     * @return
     */
    public Long get(){
        return threadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalNPE threadLocalNPE = new ThreadLocalNPE();
        System.out.println(threadLocalNPE.get());
        new Thread(() -> {
            threadLocalNPE.set();
            long threadId = threadLocalNPE.get();
            System.out.println(threadId);
        }).start();

    }
}
