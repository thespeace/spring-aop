package thespeace.springAop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import thespeace.springAop.member.MemberServiceImpl;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    //AspectJExpressionPointcut이 바로 포인트컷 표현식을 처리해주는 클래스다.
    //여기에 포인트컷 표현식을 지정하면 된다. AspectJExpressionPointcut 는 상위에 Pointcut 인터페이스를 가진다.
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    /**
     * MemberServiceImpl.hello(String) 메서드의 정보를 출력해준다.<br>
     * execution 으로 시작하는 포인트컷 표현식은 이 메서드 정보를 매칭해서 포인트컷 대상을 찾아낸다.
     */
    @Test
    void printMethod() {
        //public java.lang.String thespeace.springAop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

}
