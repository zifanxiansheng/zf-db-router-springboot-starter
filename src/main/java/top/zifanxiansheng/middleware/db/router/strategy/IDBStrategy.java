package top.zifanxiansheng.middleware.db.router.strategy;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 16:20
 **/
public interface IDBStrategy {


    void doRouter(String routeKeyValue);

    void clear();

    String getTBKey();

    void setTBKey(String tbKey);

    int dbCount();

    int tbCount();


}
