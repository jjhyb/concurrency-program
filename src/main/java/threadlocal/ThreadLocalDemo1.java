package threadlocal;

/**
 * @author: huangyibo
 * @Date: 2020/2/15 16:03
 * @Description: 演示ThreadLocal用法2，避免传递参数的麻烦
 */
public class ThreadLocalDemo1 {

    public static void main(String[] args) {
        Service1 service1 = new Service1();
        service1.process();
    }
}

class Service1{
    public void process(){
        User user = new User("小明");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2{
    public void process(){
        User user = UserContextHolder.holder.get();
        System.out.println("service2："+user.name);
        new Service3().process();
    }
}

class Service3{
    public void process(){
        User user = UserContextHolder.holder.get();
        System.out.println("service3："+user.name);
        UserContextHolder.holder.remove();//使用完ThreadLocal之后调用remove()方法，避免内存泄漏
    }
}

class UserContextHolder{
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User{
    String name;

    public User(String name) {
        this.name = name;
    }
}
