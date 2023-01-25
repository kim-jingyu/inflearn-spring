package practice.core2.discount;

import practice.core2.member.Grade;
import practice.core2.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{
    private final int discountFixAmount = 1000;
    @Override
    public int discount(Member member, int itemPrice) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        }
        return 0;
    }
}
