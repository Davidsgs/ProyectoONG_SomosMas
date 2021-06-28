package com.restteam.ong.services;

import com.restteam.ong.models.Member;

import java.util.ArrayList;


public interface MemberService {
    public ArrayList<Member> getMember();
    public  void addMember(Member member);
    public  String updateMember(Member memberUpdate);
    public  String deleteSoftDelete(Long id);
}