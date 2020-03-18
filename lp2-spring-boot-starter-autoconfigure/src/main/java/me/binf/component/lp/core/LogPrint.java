package me.binf.component.lp.core;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPrint {
    /**
     * 业务名称
     * @return
     */
     String value() default "";

    /**
     * 日志级别
     * @return
     */
    Level level() default Level.INFO;

    /**
     * 是否有传参数日志,默认有
     * @return
     */
    boolean hasParamLog() default true;

    /**
     * 是否有传返回值日志,默认有
     * @return
     */
    boolean hasResultLog() default true;

    /**
     * 是否有异常日志,默认有
     * @return
     */
    boolean hasThrowingLog() default true;
}
