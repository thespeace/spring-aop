package thespeace.springAop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    /**
     * <h2>자기 자신을 의존관계 주입</h2>
     * 생성자 주입은 순환 사이클을 만들기 때문에 실패한다.<br>
     * 수정자를 통해서 자기 자신을 주입 받을 수 있다.<br>
     * 스프링에서 AOP가 적용된 대상을 의존관계 주입 받으면 주입 받은 대상은 실제 자신이 아니라 프록시 객체이다.<br><br>
     *
     * external() 을 호출하면 callServiceV1.internal() 를 호출하게 된다.<br>
     * 주입받은 callServiceV1 은 프록시이다. 따라서 프록시를 통해서 AOP를 적용할 수 있다.
     */
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        //log.info("callServiceV1 setter={}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); //외부 메서드 호출( != this.internal())
    }

    public void internal() {
        log.info("call internal");
    }
}
