package thespeace.springAop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <h1>스프링 AOP 구현 - 어드바이스 추가</h1>
 * 앞서 로그를 출력하는 기능에 추가로 트랜잭션을 적용하는 코드도 추가해보자.<br>
 * 여기서는 진짜 트랜잭션을 실행하는 것은 아니다. 기능이 동작한 것 처럼 로그만 남기겠다.<p><p>
 *
 * 트랜잭션 기능은 보통 다음과 같이 동작한다.
 * <ul>
 *     <li>핵심 로직 실행 직전에 트랜잭션을 시작</li>
 *     <li>핵심 로직 실행</li>
 *     <li>핵심 로직 실행에 문제가 없으면 커밋</li>
 *     <li>핵심 로직 실행에 예외가 발생하면 롤백</li>
 * </ul>
 *
 * @see /docs/2.implementation/2.add_advice.md
 */
@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* thespeace.springAop.order..*(..))") //pointcut expression
    private void allOrder(){} //pointcut signature

    @Pointcut("execution(* *..*Service.*(..))") //타입(클래스,인터페이스) 이름 패턴이 `*Service`,  ex) `XxxService`...
    private void allService(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    /**
     * <h2>@Around("allOrder() && allService()")</h2>
     * <ul>
     *     <li>포인트컷은 이렇게 조합할 수 있다. && (AND), || (OR), ! (NOT) 3가지 조합이 가능하다.</li>
     *     <li>thespeace.springAop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service인 것을 대상으로 한다.</li>
     *     <li>결과적으로 doTransaction() 어드바이스는 OrderService 에만 적용된다.</li>
     *     <li>doLog() 어드바이스는 OrderService , OrderRepository 에 모두 적용된다.</li>
     * </ul><p>
     *
     * <h2>포인트컷이 적용된 AOP 결과는 다음과 같다.</h2>
     * <ul>
     *     <li>orderService : doLog() , doTransaction() 어드바이스 적용</li>
     *     <li>orderRepository : doLog() 어드바이스 적용</li>
     * </ul>
     */
    @Around("allOrder() && allService()") //thespeace.springAop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
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
