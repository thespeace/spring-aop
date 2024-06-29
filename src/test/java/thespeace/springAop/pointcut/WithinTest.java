package thespeace.springAop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import thespeace.springAop.member.MemberServiceImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <h1>within</h1>
 * within 지시자는 특정 타입 내의 조인 포인트들로 매칭을 제한한다.<br>
 * 쉽게 이야기해서 해당 타입이 매칭되면 그 안의 메서드(조인 포인트)들이 자동으로 매칭된다.<br>
 * 문법은 단순한데 execution 에서 타입 부분만 사용한다고 보면 된다.<p><p>
 *
 * <h3>주의</h3>
 * 그런데 within 사용시 주의해야 할 점이 있다. 표현식에 부모 타입을 지정하면 안된다는 점이다.<br>
 * 정확하게 타입이 맞아야 한다. 이 부분에서 execution 과 차이가 난다.
 */
@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        pointcut.setExpression("within(thespeace.springAop.member.MemberServiceImpl)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within(thespeace.springAop.member.*Service*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(thespeace.springAop..*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * <h2>withinSuperTypeFalse() vs executionSuperTypeTrue()</h2>
     * 부모 타입(여기서는 MemberService 인터페이스) 지정시 within 은 실패하고, execution 은 성공하는 것을 확
     * 인할 수 있다.
     */
    @Test
    @DisplayName("타켓의 타입에만 직접 적용, 인터페이스를 선정하면 안된다.")
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(thespeace.springAop.member.MemberService)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("execution은 타입 기반, 인터페이스를 선정 가능.")
    void executionSuperTypeTrue() {
        pointcut.setExpression("execution(* thespeace.springAop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}