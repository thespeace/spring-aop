package thespeace.springAop.internalcall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import thespeace.springAop.internalcall.aop.CallLogAspect;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV1Test {

    @Autowired CallServiceV1 callServiceV1;

    /**
     * 실행 결과를 보면 이제는 internal() 을 호출할 때 자기 자신의 인스턴스를 호출하는 것이 아니라
     * 프록시 인스턴스를 통해서 호출하는 것을 확인할 수 있다. 당연히 AOP도 잘 적용된다.<br><br>
     *
     * 사진 참고 : /docs/4.working-level caution/internal call/2.Alternative_1-Self_injection.PNG
     */
    @Test
    void external() {
        callServiceV1.external();
    }

}