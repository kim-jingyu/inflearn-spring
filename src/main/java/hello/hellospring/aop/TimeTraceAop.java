package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component // 컴포넌트 스캔. 스프링 빈에 등록.
public class TimeTraceAop {

    /**
     * 공통 관심 사항 타겟팅
     */
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        /**
         * 어떤 메소드를 콜하는지 얻을 수 있음.
         * 축약해서 이쁘게 출력
         */
        System.out.println("START: "+joinPoint.toString());
        try{
            //다음 메서드로 진행
            // inline variable 됨
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: "+joinPoint.toString()+" "+timeMs+"ms");
        }
    }
}
