package thespeace.springAop.member;

import org.springframework.stereotype.Component;
import thespeace.springAop.member.annotation.ClassAop;
import thespeace.springAop.member.annotation.MethodAop;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService{

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }
}
