package top.zifanxiansheng.middleware.db.router.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import top.zifanxiansheng.middleware.db.router.DBContextHolder;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 15:36
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return  "db" + DBContextHolder.getDbKey();
    }
}
