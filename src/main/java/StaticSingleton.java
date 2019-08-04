public class StaticSingleton {
    private StaticSingleton(){
        System.out.println("StaticSingleton is create");
    }

    private static class SinletonHolder{
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance(){
        return SinletonHolder.instance;
    }
}
