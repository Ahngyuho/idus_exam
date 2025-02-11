package dev.agh.idus.member;

import dev.agh.idus.member.model.Member;
import dev.agh.idus.member.model.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String username);
    Optional<Member> findByIdx(Long idx);
}
