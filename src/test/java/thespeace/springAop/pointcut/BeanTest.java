package thespeace.springAop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import thespeace.springAop.order.OrderService;

/**
 * <h1>bean</h1>
 * 정의 : 스프링 전용 포인트컷 지시자, 빈의 이름으로 지정한다.
 *
 * <ul>설명
 *     <li>스프링 빈의 이름으로 AOP 적용 여부를 지정한다. 이것은 스프링에서만 사용할 수 있는 특별한 지시자이다.</li>
 *     <li>bean(orderService) || bean(*Repository)</li>
 *     <li>* 과 같은 패턴을 사용할 수 있다.</li>
 * </ul>
 */
@Slf4j
@Import(BeanTest.BeanAspect.class)
@SpringBootTest
public class BeanTest {

    @Autowired
    OrderService orderService;

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Aspect
    static class BeanAspect {

        //OrderService , *Repository(OrderRepository) 의 메서드에 AOP가 적용된다.
        @Around("bean(orderService) || bean(*Repository)")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[bean] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
