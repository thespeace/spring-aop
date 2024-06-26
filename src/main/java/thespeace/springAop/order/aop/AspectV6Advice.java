package thespeace.springAop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;


/**
 * <h1>스프링 AOP 구현 - 어드바이스 종류</h1>
 * 어드바이스는 앞서 살펴본 @Around 외에도 여러가지 종류가 있다.
 *
 * <ul>
 *     <li>@Around : 메서드 호출 전후에 수행, 가장 강력한 어드바이스, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등이 가능</li>
 *     <li>@Before : 조인 포인트 실행 이전에 실행</li>
 *     <li>@AfterReturning : 조인 포인트가 정상 완료후 실행</li>
 *     <li>@AfterThrowing : 메서드가 예외를 던지는 경우 실행</li>
 *     <li>@After : 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)</li>
 * </ul><br>
 *
 * 예제를 만들면서 학습하고 아래의 문서를 통해 더 자세히 알아보자.<br>
 *
 * 그리고 복잡해 보이지만 사실 @Around 를 제외한 나머지 어드바이스들은 @Around 가 할 수 있는 일의 일부만 제공할 뿐이다.<br>
 * 따라서 @Around 어드바이스만 사용해도 필요한 기능을 모두 수행할 수 있다.
 * @see /docs/2.implementation/4.advice_type.md
 */
@Slf4j
@Aspect
public class AspectV6Advice {
    
    @Around("thespeace.springAop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[around][트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();

            //@AfterReturning
            log.info("[around][트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[around][트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[around][리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("thespeace.springAop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "thespeace.springAop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "thespeace.springAop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
    }

    @After(value = "thespeace.springAop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }

}