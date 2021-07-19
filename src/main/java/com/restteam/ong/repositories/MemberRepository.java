package com.restteam.ong.repositories;

import com.restteam.ong.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public boolean existsByName(String name);
}
