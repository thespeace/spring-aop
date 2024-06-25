package thespeace.springAop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * <h1>스프링 AOP 구현 - 포인트컷 참조</h1>
 * 다음과 같이 포인트컷을 공용으로 사용하기 위해 별도의 외부 클래스에 모아두어도 된다.
 * 참고로 외부에서 호출할 때는 포인트컷의 접근 제어자를 public 으로 열어두어야 한다.
 *
 * @see Pointcuts
 */
@Slf4j
@Aspect
public class AspectV4 {

    //패키지명을 포함한 클래스 이름과 포인트컷 시그니처를 모두 지정, 포인트컷을 여러 어드바이스에서 함께 사용할 때 이 방법을 사용하면 효과적이다.
    @Around("thespeace.springAop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("thespeace.springAop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            return e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

}
