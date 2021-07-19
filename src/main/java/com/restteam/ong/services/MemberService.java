package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.MemberPageableDTO;
import com.restteam.ong.models.Member;

import java.util.Optional;


public interface MemberService {
    Optional<Long> addMember(Member member);
    MemberPageableDTO getMembers(int pageId);
    Optional<Member> updateMember(Long memberId, String name, String facebook, String instagram, String linkedin, String image, String description);
    Optional<Long> deleteMember(Long memberId);
}