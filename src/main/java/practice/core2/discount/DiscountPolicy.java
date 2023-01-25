package practice.core2.discount;

import practice.core2.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int itemPrice);
}
