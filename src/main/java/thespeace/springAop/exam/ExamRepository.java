package thespeace.springAop.exam;

import org.springframework.stereotype.Repository;
import thespeace.springAop.exam.annotation.Retry;
import thespeace.springAop.exam.annotation.Trace;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * <h2>5번에 1번 실패하는 요청</h2>
     */
    @Trace
    @Retry(value = 4) //횟수 4번
    public String save(String itemId) {
        seq++;
        if(seq % 5 == 0) {
            throw new IllegalStateException("예외 발생");
        }
        return "ok";
    }
}
