package practice.core2.discount;

import practice.core2.member.Grade;
import practice.core2.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{
    private final int discountPercent = 10;
    @Override
    public int discount(Member member, int itemPrice) {
        if(member.getGrade() == Grade.VIP){
            return itemPrice * discountPercent / 100;
        }
        return 0;
    }
}
