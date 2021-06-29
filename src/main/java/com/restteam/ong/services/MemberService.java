package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface MemberService {
    void addMember(Member member);
    List<MemberDTO> getMembers();
    ResponseEntity<String> updateMember(Long memberId, String name, String facebook, String instagram, String linkedin, String image, String description);
    ResponseEntity<String> deleteMember(Long memberId);
}