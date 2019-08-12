package com.summer.isnow.aspect;
import com.google.gson.Gson;
import com.summer.icommon.utils.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


import java.lang.reflect.Method;

/**
 * 切面 打印请求、返回参数信息
 * 定义一个切面
 */
@Aspect
@Configuration
public class AspectDto {

    private static final Logger logger = LoggerFactory.getLogger(AspectDto.class);

    long start = System.currentTimeMillis();

    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(* com.summer.isnow.api.*Api.*(..))")
    public void excudeService() {
    }
      //  阻塞式的，不建议使用
//    @Around("excudeService()")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        String paraString = JSON.toJSONString(request.getParameterMap());
//        logger.info("***************************************************");
//        logger.info("请求开始, time:{} , URI: {}, method: {}, params: {}", start,uri, method, paraString);
//
//        // result的值就是被拦截方法的返回值
//        Object result = pjp.proceed();
//        logger.info("请求结束，time-consuming:{} , controller的返回值是 :{} " ,System.currentTimeMillis()-start,JSON.toJSONString(result));
//        return result;
//    }


    @Before("within(com.summer.isnow.api.*)")
    public void before(JoinPoint joinPoint){
        Object args[] = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        logger.info("{}.{} 请求参数： {}" ,method.getDeclaringClass().getName(),method.getName(), StringUtil.join(args,":"));
    }
    @AfterReturning(value = "within(com.summer.isnow.api.*)",returning ="rvt")
    public void after(JoinPoint joinPoint,Object rvt){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        logger.info("{}.{} 返回参数： {}" ,method.getDeclaringClass().getName(),method.getName(),new Gson().toJson(rvt));
    }

}