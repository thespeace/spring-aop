package thespeace.springAop.pointcut;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import thespeace.springAop.member.MemberServiceImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <h1>args</h1>
 * <ul>
 *     <li>args : 인자가 주어진 타입의 인스턴스인 조인 포인트로 매칭</li>
 *     <li>기본 문법은 execution 의 args 부분과 같다.</li>
 * </ul><p><p>
 *
 * <h2>execution과 args의 차이점</h2>
 * <ul>
 *     <li>execution 은 파라미터 타입이 정확하게 매칭되어야 한다.<br>
 *         execution 은 클래스에 선언된 정보를 기반으로 판단한다.</li>
 *     <li>args 는 부모 타입을 허용한다.<br>
 *         args 는 실제 넘어온 파라미터 객체 인스턴스를 보고 판단한다.</li>
 * </ul>
 */
public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    void args() {
        //hello(String)과 매칭
        assertThat(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args()")
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();
        assertThat(pointcut("args(..)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(*)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(String,..)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적)<br>
     * args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)<p>
     *
     * 정적으로 클래스에 선언된 정보만 보고 판단하는 execution(* *(Object)) 는 매칭에 실패하고,
     * 동적으로 실제 파라미터로 넘어온 객체 인스턴스로 판단하는 args(Object) 는 매칭에 성공한다. (부모 타입
     * 허용)<p><p>
     *
     * <h3>참고</h3>
     * args 지시자는 단독으로 사용되기 보다는 뒤에서 설명할 파라미터 바인딩에서 주로 사용된다.
     */
    @Test
    void argsVsExecution() {

        //Args
        assertThat(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(java.io.Serializable)") //부모 타입인 Serializable 허용.
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(Object)") //부모 타입인 Object도 허용.
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();

        //Execution
        assertThat(pointcut("execution(* *(String))")
                .matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("execution(* *(java.io.Serializable))") //매칭 실패, 정확하게 매칭해야함.
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();
        assertThat(pointcut("execution(* *(Object))") //매칭 실패, 정확하게 매칭해야함.
                .matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
}
