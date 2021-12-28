package top.zifanxiansheng.middleware.db.router.annotation;


import java.lang.annotation.*;

/**
 * @author luoyong
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DBRouter {

    String routerKey() default "";
}
