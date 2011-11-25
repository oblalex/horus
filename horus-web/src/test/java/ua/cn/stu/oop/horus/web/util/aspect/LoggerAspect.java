package ua.cn.stu.oop.horus.web.util.aspect;

import org.apache.commons.logging.*;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 *
 * @author alex
 */
@Component
@Aspect
public class LoggerAspect {

    private final static Log logger = LogFactory.getLog(LoggerAspect.class);
    private final static StopWatch stopWatch = new StopWatch();
    
    private ProceedingJoinPoint savedJoinPoint;
    private Object joinPointReturnValue;

    @Pointcut("execution(* ua.cn.stu.oop.horus.web.util.pages.validator..*.*(..))")
    public void pointcutExecution() {
    }

    @Around("pointcutExecution()")
    public Object aroundPointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        savedJoinPoint = joinPoint;
        proceedJoinPoint();
        buildLog();
        return joinPointReturnValue;
    }

    private void proceedJoinPoint() throws Throwable {
        stopWatch.start();
        joinPointReturnValue = savedJoinPoint.proceed();
        stopWatch.stop();
    }

    private void buildLog() {
        StringBuilder logMessage = new StringBuilder();

        logMessage.append(savedJoinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(savedJoinPoint.getSignature().getName());
        logMessage.append("(");

        Object[] args = savedJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            logMessage.append(args[i]).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }

        logMessage.append(")");
        logMessage.append(" returns: ");
        logMessage.append(joinPointReturnValue);
        logMessage.append(". execution time: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");

        logger.debug(logMessage.toString());        
    }
}
