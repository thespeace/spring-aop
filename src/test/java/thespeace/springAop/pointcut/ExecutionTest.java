package thespeace.springAop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import thespeace.springAop.member.MemberServiceImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

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




    /**
     * <h1>execution - 1</h1>
     * <h3>execution 문법</h3>
     * <pre>{@code
     *     execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern)throws-pattern?)
     *     execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
     * }</pre>
     * <ul>
     *     <li>메소드 실행 조인 포인트를 매칭한다.</li>
     *     <li>?는 생략할 수 있다.</li>
     *     <li> * 같은 패턴을 지정할 수 있다.</li>
     * </ul>
     * 실제 코드를 하나씩 보면서 execution 을 이해해보자.<p><p>
     *
     * <h2>가장 정확한 포인트 컷 : exactMatch()</h2>
     * MemberServiceImpl.hello(String) 메서드와 가장 정확하게 모든 내용이 매칭되는 표현식.
     * <ul>
     *     <li>AspectJExpressionPointcut 에 pointcut.setExpression 을 통해서 포인트컷 표현식을 적용할 수 있다.</li>
     *     <li>pointcut.matches(메서드, 대상 클래스) 를 실행하면 지정한 포인트컷 표현식의 매칭 여부를 true ,false 로 반환한다.</li>
     * </ul><p><p>
     *
     * <h3>매칭 조건</h3>
     * <ul>
     *     <li>접근제어자?: public</li>
     *     <li>반환타입: String</li>
     *     <li>선언타입?: hello.aop.member.MemberServiceImpl</li>
     *     <li>메서드이름: hello</li>
     *     <li>파라미터: (String)</li>
     *     <li>예외?: 생략</li>
     * </ul>
     * MemberServiceImpl.hello(String) 메서드와 포인트컷 표현식의 모든 내용이 정확하게 일치한다.<br>
     * 따라서 true 를 반환한다
     */
    @Test
    void exactMatch(){
        //public java.lang.String thespeace.springAop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String thespeace.springAop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * <h2>가장 많이 생략한 포인트컷 : allMatch()</h2>
     * <h2>매칭 조건</h2>
     * <ul>
     *     <li>접근제어자?: 생략</li>
     *     <li>반환타입: *</li>
     *     <li>선언타입?: 생략</li>
     *     <li>메서드이름: *</li>
     *     <li>파라미터: (..)</li>
     *     <li>예외?: 없음</li>
     * </ul>
     * * 은 아무 값이 들어와도 된다는 뜻이다.<br>
     * 파라미터에서 .. 은 파라미터의 타입과 파라미터 수가 상관없다는 뜻이다. ( 0..* )<br>
     * 파라미터는 나중에 더 알아보자.
     */
    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* thespeace.springAop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * <h3>hello.aop.member.*(1).*(2)</h3>
     * <ul>
     *     <li>(1): 타입</li>
     *     <li>(2): 메서드 이름</li>
     * </ul>
     */
    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* thespeace.springAop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse() {
        pointcut.setExpression("execution(* thespeace.springAop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        pointcut.setExpression("execution(* thespeace.springAop.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * <h3>패키지에서 . , .. 의 차이를 이해해야 한다.</h3>
     * <ul>
     *     <li> . : 정확하게 해당 위치의 패키지</li>
     *     <li>.. : 해당 위치의 패키지와 그 하위 패키지도 포함</li>
     * </ul>
     */
    @Test
    void packageMatchSubPackage2() {
        pointcut.setExpression("execution(* thespeace.springAop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
