package thespeace.springAop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //thespeace.springAop.order 패키지와 하위 패키지
    @Pointcut("execution(* thespeace.springAop.order..*(..))")
    public void allOrder(){}

    //타입 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    //allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
