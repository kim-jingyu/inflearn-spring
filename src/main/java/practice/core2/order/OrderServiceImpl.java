package practice.core2.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.core2.discount.DiscountPolicy;
import practice.core2.member.Member;
import practice.core2.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discount = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discount);
    }
}
