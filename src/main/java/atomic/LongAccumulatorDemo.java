package atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * @author: huangyibo
 * @Date: 2020/2/17 16:49
 * @Description: 演示LongAccumulator的用法
 *
 */
public class LongAccumulatorDemo {

    public static void main(String[] args) {
        LongAccumulator longAccumulator = new LongAccumulator((x,y) -> x+y,0);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        IntStream.range(1,10).forEach(i -> executorService.submit(() -> longAccumulator.accumulate(i)));
        executorService.shutdown();
        while(!executorService.isTerminated()){

        }
        System.out.println(longAccumulator.getThenReset());

        demo2();
    }

    public static void demo1(){
        LongAccumulator longAccumulator = new LongAccumulator((x,y) -> x+y,0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(2);
        System.out.println(longAccumulator.getThenReset());
    }

    public static void demo2(){
        LongAccumulator longAccumulator = new LongAccumulator((x,y) -> Math.max(x,y),0);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        IntStream.range(1,10).forEach(i -> executorService.submit(() -> longAccumulator.accumulate(i)));
        executorService.shutdown();
        while(!executorService.isTerminated()){

        }
        System.out.println(longAccumulator.getThenReset());
    }
}
