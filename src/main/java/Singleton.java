public class Singleton {
    private  Singleton(){
        System.out.println("Singleton is create");
    }
    private static  Singleton instance = new Singleton();

    public  static Singleton getInstance(){
        return instance;
    }

    public static String createString(){
        return "String is ...";
    }


}
