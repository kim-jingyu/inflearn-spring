package practice.core2.discount;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import practice.core2.member.Grade;
import practice.core2.member.Member;

@Component
@Primary
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
