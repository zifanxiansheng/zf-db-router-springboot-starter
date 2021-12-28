package top.zifanxiansheng.middleware.db.router;

import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import top.zifanxiansheng.middleware.db.router.annotation.DBRouter;
import top.zifanxiansheng.middleware.db.router.model.ZFDataBaseConfig;
import top.zifanxiansheng.middleware.db.router.strategy.IDBStrategy;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 16:03
 **/

@Aspect
public class DBRouterJoinPoint {

    private final Logger logger = LoggerFactory.getLogger(DBRouterJoinPoint.class);

    @Pointcut("@annotation(top.zifanxiansheng.middleware.db.router.annotation.DBRouter)")
    public void joinPoint() {

    }

    private ZFDataBaseConfig zfDataBaseConfig;
    private IDBStrategy idbStrategy;

    public DBRouterJoinPoint(ZFDataBaseConfig zfDataBaseConfig, IDBStrategy idbStrategy) {
        this.zfDataBaseConfig = zfDataBaseConfig;
        this.idbStrategy = idbStrategy;
    }

    @Around("joinPoint() && @annotation(dbRouter)")
    public void doRoute(ProceedingJoinPoint proceedingJoinPoint, DBRouter dbRouter) throws Throwable {
        String dbRouterKey = StringUtils.isEmpty( dbRouter.routerKey()) ? zfDataBaseConfig.getRouterKey() :  dbRouter.routerKey();
        if (StringUtils.isEmpty(dbRouterKey)) {
            throw new RuntimeException("dbRouterKey is not config");
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object routerKeyValue = getRouterKeyValue(dbRouterKey, args);
        idbStrategy.doRouter(routerKeyValue.toString());
        try {
            proceedingJoinPoint.proceed();
        } finally {
            idbStrategy.clear();
        }

    }

    private Object getRouterKeyValue(String key, Object[] args) {
        if (args.length == 1) {
          return args[0];
        }

        Object keyValue = null;

        for (Object arg : args) {
            if (keyValue != null) {
                break;
            }
            try {
                keyValue = BeanUtils.getProperty(arg, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return keyValue;
    }
}
