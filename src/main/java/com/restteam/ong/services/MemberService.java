package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;

import java.util.ArrayList;
import java.util.List;


public interface MemberService {
    ArrayList<Member> getMember();
    void addMember(Member member);
    String deleteSoftDelete(Long id);
    List<MemberDTO> getMembers();
    void updateMember(Long memberId, String name, String facebook, String instagram, String linkedin, String image, String description);
}