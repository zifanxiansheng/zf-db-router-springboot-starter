package top.zifanxiansheng.middleware.db.router.config;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;
import top.zifanxiansheng.middleware.db.router.DBRouterJoinPoint;
import top.zifanxiansheng.middleware.db.router.dynamic.DynamicDataSource;
import top.zifanxiansheng.middleware.db.router.model.DBProperties;
import top.zifanxiansheng.middleware.db.router.model.ZFDataBaseConfig;
import top.zifanxiansheng.middleware.db.router.strategy.DefaultDBStrategyImpl;
import top.zifanxiansheng.middleware.db.router.strategy.IDBStrategy;
import top.zifanxiansheng.middleware.db.router.util.PropertyUtil;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 14:50
 **/
@Configuration
public class DataBaseAutoConfig implements EnvironmentAware, Constants {


    private ZFDataBaseConfig zfDataBaseConfig;


    @Bean
    public ZFDataBaseConfig zfDataBaseConfig() {

        return zfDataBaseConfig;
    }

    @Bean
    public IDBStrategy defaultDBStrategy() {

        DefaultDBStrategyImpl defaultDBStrategy = new DefaultDBStrategyImpl(zfDataBaseConfig);
        return defaultDBStrategy;
    }

    @Bean
    @ConditionalOnMissingBean
    public DBRouterJoinPoint dbRouterJoinPoint(ZFDataBaseConfig zfDataBaseConfig, IDBStrategy idbStrategy) {
        DBRouterJoinPoint dbRouterJoinPoint = new DBRouterJoinPoint(zfDataBaseConfig, idbStrategy);
        return dbRouterJoinPoint;
    }


    @Bean
    public DataSource dataSource() {
        DynamicDataSource dataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSource = new HashMap<>();

        zfDataBaseConfig.getDataBaseConfigMap().forEach((key, value) -> {
            targetDataSource.putIfAbsent(key, new DriverManagerDataSource(value.getUrl(), value.getUserName(), value.getPassword()));
        });
        dataSource.setTargetDataSources(targetDataSource);

        dataSource.setDefaultTargetDataSource(new DriverManagerDataSource(zfDataBaseConfig.getDefaultDataSource().getUrl(),
                zfDataBaseConfig.getDefaultDataSource().getUserName(),
                zfDataBaseConfig.getDefaultDataSource().getPassword())
        );

        return dataSource;

    }

    @Bean
    public TransactionTemplate transactionManager() {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionTemplate.setTransactionManager(transactionManager);
        transactionTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        return transactionTemplate;
    }


    @Override
    public void setEnvironment(Environment environment) {
        zfDataBaseConfig = new ZFDataBaseConfig();
        final String prefix = "zf-db-router.";
        Integer dbCount = Integer.valueOf(Objects.requireNonNull(environment.getProperty(prefix + DB_COUNT)));
        Integer tbCount = Integer.valueOf(Objects.requireNonNull(environment.getProperty(prefix + TB_COUNT)));
        String routerKey = environment.getProperty(prefix + ROUTER_KEY);
        String defaultDBKey = environment.getProperty(prefix + DEFAULT_DB);
        zfDataBaseConfig.setDbCount(dbCount);
        zfDataBaseConfig.setTbCount(tbCount);
        zfDataBaseConfig.setRouterKey(routerKey);
        String dbSplitNames = environment.getProperty(prefix + DB_LIST);
        assert dbSplitNames != null;
        for (String dbKey : dbSplitNames.split(",")) {
            DBProperties dbProperties = PropertyUtil.handle(environment, prefix + dbKey, DBProperties.class);
            zfDataBaseConfig.getDataBaseConfigMap().put(dbKey, dbProperties);
        }

        DBProperties dbProperties = zfDataBaseConfig.getDataBaseConfigMap().get(defaultDBKey);
        zfDataBaseConfig.setDefaultDataSource(dbProperties);


    }
}
