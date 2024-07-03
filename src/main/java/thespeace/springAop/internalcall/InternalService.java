package thespeace.springAop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <h1>내부 호출을 InternalService 라는 별도의 클래스로 분리</h1>
 */
@Slf4j
@Component
public class InternalService {

    public void internal() {
        log.info("call internal");
    }
}
