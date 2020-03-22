package cas;

/**
 * @author: huangyibo
 * @Date: 2020/2/17 17:20
 * @Description: 模拟CAS操作，等价代码
 */
public class SimulatedCAS {

    private volatile int value;

    public synchronized int compareAndSwap(int expectedValue,int newValue){
        int oldValue = value;
        if(oldValue == expectedValue){
            value = newValue;
        }
        return oldValue;
    }

    public static void main(String[] args) {
        String a = "zhang2";
        final String b = "zhang";
        String d = "zhang";
        String c = b+2;
        String e = d+2;
        System.out.println(a==c);//true
        System.out.println(a==e);//false
    }
}
