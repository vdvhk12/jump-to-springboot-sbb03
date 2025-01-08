package org.example.backend.domain.Member.repository;

import org.example.backend.domain.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
