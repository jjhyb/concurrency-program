package collections.copyonwrite;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: huangyibo
 * @Date: 2020/2/18 18:24
 * @Description: 演示CopyOnWriteArrayList可以在迭代的过程中修改内容，但ArrayList不写
 */
public class CopyOnWriteArrayListDemo1 {

    public static void main(String[] args) {
//        ArrayList<String> list = new ArrayList<>();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println("list is"+list);
            String next = iterator.next();
            System.out.println(next);
            if("2".endsWith(next)){
                list.remove("5");
            }
            if("3".endsWith(next)){
                list.add("found");
            }
        }

    }
}
