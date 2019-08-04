### 设计模式 | 单例模式

单例模式：是一种对象创建模式，用于产生一个对象的具体实例，确保系统中只有一个类只有
实例。
---

优点：
* 对于频繁使用的对象，可以省略创建对象所花费的时间，这对于一些重量级的对象而言，是非常
可观的一笔系统开销。
* 由于 new 操作次数的减少，因此对于系统内存的使用频率也会降低，减轻 GC 压力，缩短
GC 停顿时间。

对于系统的关键组件和被频繁使用的对象，使用单例模式，可以有效的改善系统的性能。

---

对于单例模式的参与者有：
* 单例类：提供单例工厂，返回单例
* 使用者：获取并使用单例

---

> 单例模式的核心在于通过一个接口返回唯一的实例对象

单例模式首先必须要有一个 private 的构造方法，保证单例不会在系统中的其他代码内被实例化
其次 instance 成员变量( private ) 和 getInstance（） 方法( public )必须是 static 的

```
public class Singleton {
    private Singleton(){
        System.out.println("Singleton is create");
    }
    private static  Singleton instance = new Singleton();

    public  static Singleton getInstance(){
        return instance;
    }


    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        
    }
}

```

---
结果：`Singleton is create`

---

这样的实现很简单，可靠。但是有一点问题是，无法对 instance 实例做延迟加载。
如果单例的创建过程比较慢，而由于 instance 是 static ，因此在 JVM 加载
单例类的时候，单例对象就会创建，如果这个时候单例类在系统中还扮演了其他的角色，那么
在任何使用这个单例类的地方都会初始化这个单例变量，而不管是否会被用到。
例如：
```java
public class Singleton {
    private  Singleton(){
        System.out.println("Singleton is create");
    }
    public static  Singleton instance = new Singleton();

    public  static Singleton getInstance(){
        return instance;
    }

    public static String createString(){
        return "String is ...";
    }

}

public class Main {
    public static void main(String[] args) {
        //Singleton singleton = Singleton.instance;
        String string = Singleton.createString();
        System.out.println(string);
    }
}

```
结果：
```
Singleton is create
String is ...
```
    
---
可以看到没有使用单例类，但是还是被创建出来了。
为了解决这个问题，需要加入延迟机制。

```java
public class LazySingleton {
    private LazySingleton(){
        System.out.println("LazySingleton is create");
    }

    private static LazySingleton instance = null;
    public static synchronized LazySingleton getInstance(){
        if(instance == null){
            instance = new LazySingleton();
        }
        return instance;
    }

    public static String createString(){
        return "String is ...";
    }
}

public class Main {
    public static void main(String[] args) {
       // LazySingleton singleton = LazySingleton.getInstance();

        String string = LazySingleton.createString();
        System.out.println(string);
    }
}


```

结果： `String is ...`

---

首先静态成员变量 instance 初始值为 null ，确保系统启动时没有而外的负载，
将创建对象放到了 getInstance（） 方法中。
由于有多线程使用该类的可能，为保证该类只有一个实例对象，所以要同步 getInstance（）
保证，只有一个实例对象。
效果虽然是实现了的，但是 synchronized 系统开销较大。




