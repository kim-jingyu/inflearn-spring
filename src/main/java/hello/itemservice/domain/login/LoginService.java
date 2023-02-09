package hello.itemservice.domain.login;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <로그인의 핵심 비즈니스 로직>
 * 회원을 조회한 다음에 파라미터로 넘어온 password 와
 *      같으면 회원을 반환
 *      다르면 null 을 반환
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }
}
