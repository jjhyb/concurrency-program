package collections.predecessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 14:21
 * @Description: 演示Map的常用方法
 */
public class MapDemo {

    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        System.out.println(map.isEmpty());
        map.put("yibo",21);
        map.put("zhangwuji",20);

    }
}
