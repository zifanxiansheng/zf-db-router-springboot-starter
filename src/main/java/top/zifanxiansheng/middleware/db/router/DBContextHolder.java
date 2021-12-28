package top.zifanxiansheng.middleware.db.router;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 15:38
 **/
public class DBContextHolder {


    private static ThreadLocal<String> DB_KEY = new ThreadLocal<>();
    private static ThreadLocal<String> TB_KEY = new ThreadLocal<>();


    public static String getDbKey() {
        return DB_KEY.get();
    }

    public static void setDbKey(String dbKey) {
        DB_KEY.set(dbKey);
    }

    public static String getTbKey() {
        return TB_KEY.get();
    }

    public static void setTbKey( String tbKey) {
        TB_KEY.set(tbKey);
    }


    public static void remove() {
        DB_KEY.remove();
        TB_KEY.remove();
    }
}
