package thespeace.springAop.internalcall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import thespeace.springAop.internalcall.aop.CallLogAspect;

/**
 * 내부 호출 자체가 사라지고, callService -> internalService 를 호출하는 구조로 변경되었다.<br>
 * 덕분에 자연스럽게 AOP가 적용된다.<p><p>
 *
 * 여기서 구조를 변경한다는 것은 이렇게 단순하게 분리하는 것 뿐만 아니라 다양한 방법들이 있을 수 있다.<p><p>
 *
 * 예를 들어서 다음과 같이 클라이언트에서 둘다 호출하는 것이다.<br>
 * 클라이언트 -> external()<br>
 * 클라이언트 -> internal()<p><p>
 *
 * 물론 이 경우 external() 에서 internal() 을 내부 호출하지 않도록 코드를 변경해야 한다.<br>
 * 그리고 클라이언트가 external() , internal() 을 모두 호출하도록 구조를 변경하면 된다. (물론 가능한 경우에 한해서)<p><p>
 *
 * <h3>참고</h3>
 * AOP는 주로 트랜잭션 적용이나 주요 컴포넌트의 로그 출력 기능에 사용된다.
 * 쉽게 이야기해서 인터페이스에 메서드가 나올 정도의 규모에 AOP를 적용하는 것이 적당하다.
 * 더 풀어서 이야기하면 AOP는 public 메서드에만 적용한다. private 메서드처럼 작은 단위에는 AOP를 적용하지 않는다.
 * AOP 적용을 위해 private 메서드를 외부 클래스로 변경하고 public 으로 변경하는 일은 거의 없다.
 * 그러나 해당 예제와 같이 public 메서드에서 public 메서드를 내부 호출하는 경우에는 문제가 발생한다.
 * 실무에서 꼭 한번은 만나는 문제이기에 다루었고, AOP가 잘 적용되지 않으면 내부 호출을 의심해보자.
 */
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV3Test {

    @Autowired CallServiceV3 callServiceV3;

    @Test
    void external() {
        callServiceV3.external();
    }

}