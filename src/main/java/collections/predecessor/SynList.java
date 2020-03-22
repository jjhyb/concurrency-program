package collections.predecessor;

import java.util.*;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 13:23
 * @Description: 演示Collections.synchronizedList(new ArrayList<E>())
 */
public class SynList {

    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        list.add(5);
        System.out.println(list.get(0));

        Map<Object, Object> map = Collections.synchronizedMap(new HashMap<>());
    }
}
