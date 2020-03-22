package future;

import java.util.concurrent.*;

/**
 * @author: huangyibo
 * @Date: 2020/2/21 15:09
 * @Description:
 * 演示Future get的超时方法，需要注意超时后处理，调用future.cancel()，
 * 演示cancel()方法传入true和false的区别，代表是否中断正在执行的任务
 */
public class FutureDemo5 {

    private static final Ad DEFAULT_AD = new Ad("无网络时候的默认广告");

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        FutureDemo5 futureDemo5 = new FutureDemo5();
        futureDemo5.printAd();
    }

    static class Ad{
        String name;
        public Ad(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return "Ad{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static class FetchAdTask implements Callable<Ad>{

        @Override
        public Ad call() throws Exception {
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
                System.out.println("sleep期间被中断了");
                return new Ad("被中断时候的默认广告");
            }

            return new Ad("预订机票哪家强，去哪儿");
        }
    }

    public void printAd(){
        Ad ad;
        Future<Ad> future = executorService.submit(new FetchAdTask());
        try {
            ad = future.get(2000,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            ad = new Ad("InterruptedException，被中断时候的默认广告");
        } catch (ExecutionException e) {
            e.printStackTrace();
            ad = new Ad("异常时候的默认广告");
        } catch (TimeoutException e) {
            ad = new Ad("超时时候的默认广告");//超时时候的默认兜底
            System.out.println("超时了，未获取到广告");
            //超时中断执行
            boolean cancel = future.cancel(true);
            System.out.println("cancel的结果："+cancel);
        }
        executorService.shutdown();
        System.out.println(ad);
    }
}
