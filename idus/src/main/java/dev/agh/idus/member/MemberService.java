package dev.agh.idus.member;

import dev.agh.idus.member.model.Member;
import dev.agh.idus.member.model.MemberDto;
import dev.agh.idus.order.model.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByEmail(username);

        if (result.isPresent()) {
            // 7번 로직
            Member user = result.get();
            return user;
        }

        return null;
    }

    @Transactional(readOnly = true)
    public MemberDto.DetailResponse getDetail(Long idx) {
        Member member = memberRepository.findByIdx(idx).orElseThrow();
        return MemberDto.DetailResponse.fromEntity(member);
    }

    public List<OrderDto.OrderResponse> getMemberWithOrders(Long idx) {
        Member member = memberRepository.findByIdx(idx).orElseThrow();
        return member.getOrders().stream().map(OrderDto.OrderResponse::fromEntity).collect(Collectors.toList());
    }
}
