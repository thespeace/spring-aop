package thespeace.springAop.proxyvs.code;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * <h1>AOP 프록시 생성을 위해 간단한 Aspect</h1>
 */
@Slf4j
@Aspect
public class ProxyDIAspect {

    @Before("execution(* thespeace.springAop..*.*(..))")
    public void doTrace(JoinPoint joinPoint) {
        log.info("[proxyDIAdvice] {}", joinPoint.getSignature());
    }
}
