package collections.predecessor;

import java.util.Vector;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 12:46
 * @Description: 演示Vector 主要看Vector的源码
 */
public class VectorDemo {

    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("test");
        System.out.println(vector.get(0));
    }
}
