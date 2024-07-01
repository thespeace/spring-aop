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
 * <h1>this, target</h1>
 *
 * <ul>
 *     <li>properties = {"spring.aop.proxy-target-class=false"} : application.properties 에
 *         설정하는 대신에 해당 테스트에서만 설정을 임시로 적용한다. 이렇게 하면 각 테스트마다 다른 설정을
 *         손쉽게 적용할 수 있다.</li>
 *     <li>spring.aop.proxy-target-class=false : 스프링이 AOP 프록시를 생성할 때 JDK 동적 프록시를
 *         우선 생성한다. 물론 인터페이스가 없다면 CGLIB를 사용한다.</li>
 *     <li>spring.aop.proxy-target-class=true : 스프링이 AOP 프록시를 생성할 때 CGLIB 프록시를
 *         생성한다. 참고로 이 설정을 생략하면 스프링 부트에서 기본으로 CGLIB를 사용한다.</li>
 * </ul>
 * JDK 동적 프록시를 사용하면 this(hello.aop.member.MemberServiceImpl) 로 지정한 [this-impl] 부분이
 * 출력되지 않는 것을 확인할 수 있다.<p><p>
 *
 * 참고 : this , target 지시자는 단독으로 사용되기 보다는 파라미터 바인딩에서 주로 사용된다.
 *
 * @see /docs/3.pointcut/02.this_and_target.md
 */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false") //JDK 동적 프록시
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") //CGLIB
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        //부모 타입 허용
        @Around("this(thespeace.springAop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //부모 타입 허용
        @Around("target(thespeace.springAop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //this: 스프링 AOP 프록시 객체 대상
        //JDK 동적 프록시는 인터페이스를 기반으로 생성되므로 구현 클래스를 알 수 없음
        //CGLIB 프록시는 구현 클래스를 기반으로 생성되므로 구현 클래스를 알 수 있음
        @Around("this(thespeace.springAop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //target: 실제 target 객체 대상
        @Around("target(thespeace.springAop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
