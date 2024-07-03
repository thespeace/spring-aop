package thespeace.springAop.internalcall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import thespeace.springAop.internalcall.aop.CallLogAspect;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

    @Autowired CallServiceV2 callServiceV2;

    @Test
    void external() {
        callServiceV2.external();
    }

}