package top.zifanxiansheng.middleware.db.router.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zifanxiansheng.middleware.db.router.DBContextHolder;
import top.zifanxiansheng.middleware.db.router.model.ZFDataBaseConfig;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 16:37
 **/
public class DefaultDBStrategyImpl  implements IDBStrategy{

    private final Logger logger = LoggerFactory.getLogger(DefaultDBStrategyImpl.class);

    private ZFDataBaseConfig zfDataBaseConfig;
    public DefaultDBStrategyImpl(ZFDataBaseConfig zfDataBaseConfig) {
        this.zfDataBaseConfig = zfDataBaseConfig;
    }
    @Override
    public void doRouter(String routeKeyValue) {

        int size = zfDataBaseConfig.getDbCount() * zfDataBaseConfig.getTbCount();

        // 扰动函数
        int idx = (size - 1) & (routeKeyValue.hashCode() ^ (routeKeyValue.hashCode() >>> 16));

        // 库表索引
        int dbIdx = idx / zfDataBaseConfig.getTbCount() + 1;
        int tbIdx = idx - zfDataBaseConfig.getTbCount() * (dbIdx - 1);

        // 设置到 ThreadLocal
        DBContextHolder.setDbKey(String.format("%02d", dbIdx));
        DBContextHolder.setTbKey(String.format("%03d", tbIdx));
        logger.debug("数据库路由 dbIdx：{} tbIdx：{}",  dbIdx, tbIdx);
    }

    @Override
    public void clear() {
        DBContextHolder.remove();
    }

    @Override
    public String getTBKey() {
        return DBContextHolder.getTbKey();
    }

    @Override
    public void setTBKey(String tbKey) {
        DBContextHolder.setTbKey(tbKey);
    }

    @Override
    public int dbCount() {
       return  zfDataBaseConfig.getDbCount();
    }

    @Override
    public int tbCount() {
        return zfDataBaseConfig.getTbCount();
    }


}
