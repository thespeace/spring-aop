package thespeace.springAop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import thespeace.springAop.member.MemberService;

/**
 * <h1>@annotation, @args</h1>
 *
 * <h2>@annotation</h2>
 * 정의 : 메서드가 주어진 애노테이션을 가지고 있는 조인 포인트를 매칭.<br>
 * 설명 : {@code @annotation(hello.aop.member.annotation.MethodAop)}<br><br>
 * 다음과 같이 메서드(조인 포인트)에 애노테이션이 있으면 매칭한다.
 * <pre>{@code
 *     public class MemberServiceImpl {
 *          @MethodAop("test value")
 *          public String hello(String param) {
 *              return "ok";
 *         }
 *     }
 * }</pre><p><p>
 *
 * <h2>@args</h2>
 * 정의 : 전달된 실제 인수의 런타임 타입이 주어진 타입의 애노테이션을 갖는 조인 포인트.<br>
 * 설명 : 전달된 인수의 런타임 타입에 @Check 애노테이션이 있는 경우에 매칭한다. {@code @args(test.Check)}
 */
@Slf4j
@Import({AtAnnotationTest.AtAnnotationAspect.class})
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect {

        @Around("@annotation(thespeace.springAop.member.annotation.MethodAop)")
        public Object doAtAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
