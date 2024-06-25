package thespeace.springAop;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import thespeace.springAop.order.OrderRepository;
import thespeace.springAop.order.OrderService;
import thespeace.springAop.order.aop.AspectV1;
import thespeace.springAop.order.aop.AspectV2;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@code @Aspect} 는 애스펙트라는 표식이지 컴포넌트 스캔이 되는 것은 아니다.
 * 따라서 AspectV1 를 AOP로 사용하려면 스프링 빈으로 등록해야 한다.<p><p>
 *
 * 스프링 빈으로 등록하는 방법은 다음과 같다.
 * <ul>
 *     <li>@Bean 을 사용해서 직접 등록</li>
 *     <li>@Component 컴포넌트 스캔을 사용해서 자동 등록</li>
 *     <li>@Import 주로 설정 파일을 추가할 때 사용( @Configuration )</li>
 * </ul>
 *
 * {@code @Import} 는 주로 설정 파일을 추가할 때 사용하지만, 이 기능으로 스프링 빈도 등록할 수 있다.
 * 테스트에서는 버전을 올려가면서 변경할 예정이어서 간단하게 @Import 기능을 사용하자.<p>
 *
 * @see /docs/2.implementation/1.start.PNG
 */
@Slf4j
//@Import(AspectV1.class)
@Import(AspectV2.class)
@SpringBootTest
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        assertThatThrownBy(() -> orderService.orderItem("ex")).isInstanceOf(IllegalStateException.class);
    }

}
