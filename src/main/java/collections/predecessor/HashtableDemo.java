package collections.predecessor;

import java.util.Hashtable;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 13:16
 * @Description:
 */
public class HashtableDemo {

    public static void main(String[] args) {
        Hashtable<String,String> hashtable = new Hashtable<>();
        hashtable.put("学完以后跳槽涨薪幅度","50%");
        System.out.println(hashtable.get("学完以后跳槽涨薪幅度"));
    }
}
