package top.zifanxiansheng.middleware.db.router.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 14:40
 **/
public class ZFDataBaseConfig {


    private String routerKey;
    private int dbCount;
    private int tbCount;
    private String defaultDB;


    private Map<String, DBProperties> dataBaseConfigMap = new ConcurrentHashMap<>();

    private DBProperties defaultDataSource ;

    public DBProperties getDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(DBProperties defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    public String getRouterKey() {
        return routerKey;
    }

    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTbCount() {
        return tbCount;
    }

    public void setTbCount(int tbCount) {
        this.tbCount = tbCount;
    }

    public String getDefaultDB() {
        return defaultDB;
    }

    public void setDefaultDB(String defaultDB) {
        this.defaultDB = defaultDB;
    }

    public Map<String, DBProperties> getDataBaseConfigMap() {
        return dataBaseConfigMap;
    }

    public void setDataBaseConfigMap(Map<String, DBProperties> dataBaseConfigMap) {
        this.dataBaseConfigMap = dataBaseConfigMap;
    }



}
