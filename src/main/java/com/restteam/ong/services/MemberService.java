package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;

import java.util.List;
import java.util.Optional;


public interface MemberService {
    Optional<Long> addMember(Member member);
    List<MemberDTO> getMembers(int pageId);
    Optional<Member> updateMember(Long memberId, String name, String facebook, String instagram, String linkedin, String image, String description);
    Optional<Long> deleteMember(Long memberId);
}