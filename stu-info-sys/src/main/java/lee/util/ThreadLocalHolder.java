package lee.util;



public class ThreadLocalHolder {

    private static ThreadLocal<Integer> TOTAL = new ThreadLocal<>();

    public static ThreadLocal<Integer> getTOTAL() {
        return TOTAL;
    }


   
}
