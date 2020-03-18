# lp-spring-boot-starter
springboot 自动打印方法信息日志


#### 使用说明

1. 下载后放到maven私有库然后添加依赖：

```xml
<dependency>
     <groupId>me.binf</groupId>
     <artifactId>lp2-spring-boot-starter-autoconfigure</artifactId>
     <version>1.0-SNAPSHOT</version>
</dependency>
```

2. 激活starter:

```
binf.lp.enabled=true
```

3. 开始使用
* 在方法上面加上注解
```java
 @LogPrint(level = Level.DEBUG, value = "实例", hasParamLog = true, hasResultLog = false,   hasThrowingLog = true)
    public void sayHello(String name, List<String> persons) {
        System.out.println(name + "say" + persons);
    }
```

```java
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
```
* 效果
```
18:30:32.836 [main] DEBUG me.binf.component.lp.core.LogProcessor - ^V^你好业务;invoke method:tests.DemoService.sayHello();request params:{name=wang, persons=[sss, ddd, ccc]}^V^
wangsay[sss, ddd, ccc]
18:30:32.842 [main] DEBUG me.binf.component.lp.core.LogProcessor - ^V^你好业务;invoke method：tests.DemoService.sayHello();return result：null^V^
```

