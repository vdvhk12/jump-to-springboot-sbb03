package org.example.backend.domain.Member.repository;

import java.util.Optional;
import org.example.backend.domain.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
