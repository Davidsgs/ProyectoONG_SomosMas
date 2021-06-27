package com.restteam.ong.services;

import com.restteam.ong.models.Member;
import com.restteam.ong.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;


    public ArrayList<Member> getMember(){
        return  (ArrayList<Member>) memberRepository.findAll();
    }
    public  Member saveMenber(Member member){
        Member mem= new Member();
        mem.setCreatedAt(System.currentTimeMillis()/1000);
        return  memberRepository.save(member);
    }
    public  String upDateMember(Member memberUpdate){
        try {
            Member member=memberRepository.findById(memberUpdate.getId()).orElse(null);
            member.setName(memberUpdate.getName());
            member.setImage(memberUpdate.getImage());
            member.setInstagramUrl(memberUpdate.getInstagramUrl());
            member.setFacebookUrl(memberUpdate.getFacebookUrl());
            member.setLinkedinUrl(memberUpdate.getLinkedinUrl());
            member.setDescription(memberUpdate.getDescription());
            member.setUpdatedAt(System.currentTimeMillis()/1000);

        }catch (Exception e){
            return ("i do not know update, Id does not exist");
        }
        return ("Member Update");
    }

    public  String deleteSoftDelete(Long id){
        try {
            memberRepository.deleteById(id);
        }catch (Exception e){
            return "Member does not exist";
        }
        return  "Member deleted";
    }


}