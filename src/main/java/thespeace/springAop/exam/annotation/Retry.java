package thespeace.springAop.exam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>@Retry 애노테이션으로 예외 발생시 재시도 하기</h1>
 * 주의 : 횟수를 정하지 않으면 셀프 디도스를 만들 수 있다. 조심하자.<br>
 * 참고 : 스프링이 제공하는 @Transactional 은 가장 대표적인 AOP이다.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {
    int value() default 3;
}
