package com.sporta.member.repository;

import com.sporta.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This interface is the repository for performing CRUD operations on the Member entity.

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
