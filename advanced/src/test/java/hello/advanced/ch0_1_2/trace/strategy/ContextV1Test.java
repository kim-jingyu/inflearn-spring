package hello.advanced.ch0_1_2.trace.strategy;

import hello.advanced.ch0_1_2.trace.strategy.code.ContextV1;
import hello.advanced.ch0_1_2.trace.strategy.code.Strategy;
import hello.advanced.ch0_1_2.trace.strategy.code.StrategyLogic1;
import hello.advanced.ch0_1_2.trace.strategy.code.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {
    /**
     * 전략 패턴 적용
     */
    @Test
    void strategyV1() {
        Strategy strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    /**
     * 전략 패턴 익명 내부 클래스1
     */
    @Test
    void strategyV2() {
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("strategyLogic1 = {}", strategyLogic1.getClass());

        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        log.info("strategyLogic2 = {}", strategyLogic2.getClass());

        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }
}
