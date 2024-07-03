package thespeace.springAop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * ObjectProvider(Provider), ApplicationContext를 사용해서 지연(LAZY) 조회.
 */
@Slf4j
@Component
public class CallServiceV2 {

//    private final ApplicationContext applicationContext;

    /**
     * <h2>ApplicationContext 는 너무 많은 기능을 제공하기 때문에 ObjectProvider 사용</h2>
     * ObjectProvider는 객체를 스프링 컨테이너에서 조회하는 것을 스프링 빈 생성 시점이 아니라
     * 실제 객체를 사용하는 시점으로 지연할 수 있다.
     */
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject(); //호출하는 시점에 스프링 컨테이너에서 빈을 조회한다.
        callServiceV2.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
