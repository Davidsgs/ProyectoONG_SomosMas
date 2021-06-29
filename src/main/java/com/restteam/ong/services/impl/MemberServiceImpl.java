package com.restteam.ong.services.impl;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;
import com.restteam.ong.repositories.MemberRepository;
import com.restteam.ong.services.MemberService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    private MemberDTO mapToDTO(Member member){
        return modelMapper.map(member,MemberDTO.class);
    }

    public ArrayList<Member> getMember() {
        return null;
    }

    @Override
    public  void addMember(Member member){
        if(!memberAlreadyExists(member) && nameNotNull(member)){
            member.setCreatedAt(currentTimeMillis()/1000);
            memberRepository.save(member);
        }
    }
    private boolean memberAlreadyExists(Member member){ return memberRepository.existsByName(member.getName()); }
    private boolean nameNotNull(Member member){ return !member.getName().isEmpty(); }

    @Override
    public String updateMember(Member memberUpdate) {
        return null;
    }

    @Override
    public String deleteSoftDelete(Long id) {
        return null;
    }

    @Override
    public List<MemberDTO> getMembers() {
        Iterable<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member member : memberList) {
            memberDTOList.add(mapToDTO(member));
        }
        return memberDTOList;
    }
}
