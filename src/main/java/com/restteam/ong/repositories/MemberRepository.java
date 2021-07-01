package com.restteam.ong.repositories;

import com.restteam.ong.models.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    public boolean existsByName(String name);
}
