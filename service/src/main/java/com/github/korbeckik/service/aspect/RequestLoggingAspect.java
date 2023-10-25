package com.github.korbeckik.service.aspect;

import com.github.korbeckik.common.utils.CommonUtils;
import com.github.korbeckik.common.utils.StopWatch;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Aspect
@Component
@Log4j2
public class RequestLoggingAspect {

    @Around("@annotation(Loggable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopwatch = StopWatch.start();
        var result = joinPoint.proceed();
        if (result instanceof Mono<?> monoResult)
            return monoResult
                    .doOnSuccess(o -> logRequestAndResponse(joinPoint, o, stopwatch));
        else
            return result;
    }

    private static void logRequestAndResponse(ProceedingJoinPoint joinPoint, Object o, StopWatch stopwatch) {
        String response = CommonUtils.getStringOrEmptyIfNull(o.toString());

        log.info("Request: {}.{}() with argument[s] = {}",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                joinPoint.getArgs());
        log.info("Response: {}.{}() had arguments = {}, with result = {}, Execution time = {} ms",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                joinPoint.getArgs()[0],
                response, stopwatch.stop());
    }
}