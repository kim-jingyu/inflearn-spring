package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

/**
 * 애플리케이션 전체 설정 및 구성 (기획자)
 */
public class AppConfig {

    /**
     * 내가 만든 MemberServiceImpl은 MemoryMemberRepository를 사용할 거라고 주입시켜줌.
     */
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    /**
     * 역할과 구현 클래스가 한눈에 들어오도록 리팩토링
     */
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    /**
     * OrderServiceImpl을 생성해서 반환
     * 생성자로 MemoryMemberRepository와 FixDiscountPolicy를 생성해서 주입함
     * 즉, OrderServiceImpl 이 객체들을 참조하도록 그림을 완성시키고, 완성된 그림을 반환
     */
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /**
     * 역할과 구현 클래스가 한눈에 들어오도록 리팩토링
     */
    private DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
