package dev.agh.idus.member;

import dev.agh.idus.member.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String username);
    Optional<Member> findByIdx(Long idx);

    Page<Member> findAllByEmail(String email, Pageable pageable);

    Page<Member> findAllByUsername(String username, Pageable pageable);

    Page<Member> findAllByUsernameAndEmail(String username, String email, PageRequest pageRequest);
}
