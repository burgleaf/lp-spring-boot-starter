package me.binf.component.lp.core;

import me.binf.component.lp.support.ArrayType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

@Aspect
public class LogProcessor {

    private static final Logger log = LoggerFactory.getLogger(LogProcessor.class);


    /**
     * 打印环绕日志
     *
     * @param joinPoint 切入点
     * @return 返回方法返回值
     * @throws Throwable 异常
     */
    @Around(value = "@annotation(me.binf.component.lp.core.LogPrint)")
    public Object aroundPrint(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        Method method = getMethod(joinPoint);
        String methodName = signature.getDeclaringTypeName() + "." + method.getName() + "()";
        LogPrint logAnnotation = null;
        //打印参数log
        try {
            logAnnotation = getMethod(joinPoint).getAnnotation(LogPrint.class);
            if (logAnnotation.hasParamLog()) {
                this.print(logAnnotation.level(), this.getBeforeInfo(logAnnotation.value(), method, methodName, args));
            }
            //打印结果log
            Object result = joinPoint.proceed(args);
            if (logAnnotation.hasResultLog()) {
                this.print(logAnnotation.level(), this.getAfterInfo(logAnnotation.value(), method, methodName, result));
            }
            return result;
        } catch (Throwable e) {
            if (logAnnotation != null && logAnnotation.hasThrowingLog()) {
                log.error(this.getThrowingInfo(logAnnotation.value(), method, methodName, args), e);
            }
            throw e;
        }
    }

    /**
     * 获取异常信息日志
     *
     * @param busName
     * @param method
     * @param methodName
     * @param params
     * @return
     */
    private String getThrowingInfo(String busName, Method method, String methodName, Object[] params) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.hasText(busName)) {
            builder.append("^V^")
                    .append(busName)
                    .append(";");
        }
        builder.append("invoke method throwing exception：")
                .append(methodName);

        builder.append(";request params：");
        List<String> paramNames = getMethodParamNames(method);
        int count = paramNames.size();
        if (count > 0) {
            Map<String, Object> paramMap = new HashMap<>(count);
            for (int i = 0; i < count; i++) {
                paramMap.put(paramNames.get(i), this.getParam(params[i]));
            }
            return builder.append(paramMap).append("^V^").toString();
        }
        return builder.append("{}^V^").toString();
    }


    /**
     * 获取方法调用之后日志
     *
     * @param busName
     * @param method
     * @param result
     * @return
     */
    public String getAfterInfo(String busName, Method method, String methodName, Object result) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.hasText(busName)) {
            builder.append("^V^")
                    .append(busName)
                    .append(";");
        }
        builder.append("invoke method：").append(methodName);
        builder.append(";return result：")
                .append(result)
                .append("^V^");
        return builder.toString();
    }


    /**
     * 获取方法调用之前日志
     *
     * @param busName
     * @param method
     * @param params
     * @return
     */
    public String getBeforeInfo(String busName, Method method, String methodName, Object[] params) {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.hasText(busName)) {
            builder.append("^V^")
                    .append(busName)
                    .append(";");
        }
        builder.append("invoke method:").append(methodName);
        builder.append(";request params:");
        List<String> paramNames = getMethodParamNames(method);
        int count = paramNames.size();
        if (count > 0) {
            Map<String, Object> paramMap = new HashMap<>(count);
            for (int i = 0; i < count; i++) {
                paramMap.put(paramNames.get(i), this.getParam(params[i]));
            }
            return builder.append(paramMap).append("^V^").toString();
        }
        return builder.append("{}^V^").toString();
    }

    /**
     * 获取方法名
     *
     * @param method
     * @return
     */
    private List<String> getMethodParamNames(Method method) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] params = u.getParameterNames(method);
        return Arrays.asList(params);
    }

    /**
     * 获取对应参数
     *
     * @param param 参数
     * @return 返回参数
     */
    private Object getParam(Object param) {
        Class<?> type = param.getClass();
        return type.isArray() ? this.getList(type, param) : param;
    }

    /**
     * 获取数组类型参数列表
     *
     * @param valueType 数组类型
     * @param value     参数值
     * @return 返回参数列表
     */
    private List<Object> getList(Class valueType, Object value) {
        if (valueType.isAssignableFrom(ArrayType.OBJECT_ARRAY.getType())) {
            Object[] array = (Object[]) value;
            List<Object> list = new ArrayList<>(array.length);
            Collections.addAll(list, array);
            return list;
        } else if (valueType.isAssignableFrom(ArrayType.INT_ARRAY.getType())) {
            int[] array = (int[]) value;
            List<Object> list = new ArrayList<>(array.length);
            for (int v : array) {
                list.add(v);
            }
            return list;
        } else if (valueType.isAssignableFrom(ArrayType.LONG_ARRAY.getType())) {
            long[] array = (long[]) value;
            List<Object> list = new ArrayList<>(array.length);
            for (long v : array) {
                list.add(v);
            }
            return list;
        } else if (valueType.isAssignableFrom(ArrayType.DOUBLE_ARRAY.getType())) {
            double[] array = (double[]) value;
            List<Object> list = new ArrayList<>(array.length);
            for (double v : array) {
                list.add(v);
            }
            return list;
        } else if (valueType.isAssignableFrom(ArrayType.FLOAT_ARRAY.getType())) {
            float[] array = (float[]) value;
            List<Object> list = new ArrayList<>(array.length);
            for (float v : array) {
                list.add(v);
            }
            return list;
        } else if (valueType.isAssignableFrom(ArrayType.CHAR_ARRAY.getType())) {
            char[] array = (char[]) value;
            List<Object> list = new ArrayList<>(array.length);
            for (char v : array) {
                list.add(v);
            }
            return list;
        } else if (valueType.isAssignableFrom(ArrayType.BOOLEAN_ARRAY.getType())) {
            boolean[] array = (boolean[]) value;
            List<Object> list = new ArrayList<>(array.length);
            for (boolean v : array) {
                list.add(v);
            }
            return list;
        } else if (valueType.isAssignableFrom(ArrayType.BYTE_ARRAY.getType())) {
            byte[] array = (byte[]) value;
            List<Object> list = new ArrayList<>(array.length);
            for (byte v : array) {
                list.add(v);
            }
            return list;
        } else if (valueType.isAssignableFrom(ArrayType.SHORT_ARRAY.getType())) {
            short[] array = (short[]) value;
            List<Object> list = new ArrayList<>(array.length);
            for (short v : array) {
                list.add(v);
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * 获取Method对象
     *
     * @param joinPoint
     * @return
     */
    private Method getMethod(JoinPoint joinPoint) {
        //获取参数的类型
        Method method = null;
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature msig = null;
            if (!(signature instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
            msig = (MethodSignature) signature;
            method = joinPoint.getTarget().getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.error("annotation no sucheMehtod", e);
        } catch (SecurityException e) {
            log.error("annotation SecurityException", e);
        }
        return method;
    }

    /**
     * 打印信息
     *
     * @param level 日志级别
     * @param msg   输出信息
     */
    private void print(Level level, String msg) {
        switch (level) {
            case DEBUG: {
                log.debug(msg);
                break;
            }
            case INFO: {
                log.info(msg);
                break;
            }
            case WARN: {
                log.warn(msg);
                break;
            }
            case ERROR: {
                log.error(msg);
                break;
            }
            default:
        }
    }

}
