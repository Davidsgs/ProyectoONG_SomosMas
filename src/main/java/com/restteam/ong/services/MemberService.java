package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;

import java.util.ArrayList;
import java.util.List;


public interface MemberService {
    public ArrayList<Member> getMember();
    public  void addMember(Member member);
    public  String updateMember(Member memberUpdate);
    public  String deleteSoftDelete(Long id);
    List<MemberDTO> getMembers();
}