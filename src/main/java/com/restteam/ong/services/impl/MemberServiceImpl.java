package com.restteam.ong.services.impl;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;
import com.restteam.ong.repositories.MemberRepository;
import com.restteam.ong.services.MemberService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<MemberDTO> getMembers() {
        Iterable<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member member : memberList) {
            memberDTOList.add(mapToDTO(member));
        }
        return memberDTOList;
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateMember(Long memberId, String name, String facebook, String instagram, String linkedin, String image, String description) {
        try{
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("..."));
            if(name != null){member.setName(name);}
            if(facebook != null){member.setFacebookUrl(facebook);}
            if(instagram != null){member.setInstagramUrl(instagram);}
            if(linkedin != null){member.setLinkedinUrl(linkedin);}
            if(image != null){member.setImage(image);}
            if(description != null){member.setDescription(description);}
            return new ResponseEntity<>("El miembro ha sido actualizado!", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("ERROR: No se encontro al miembro",HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteMember(Long memberId) {
        boolean exists = memberRepository.existsById(memberId);
        try{
            if(!exists){throw new IllegalStateException();}
            memberRepository.deleteById(memberId);
            return new ResponseEntity<>("El miembro ha sido eliminado!",HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("ERROR: No se encontro al miembro.",HttpStatus.BAD_REQUEST);
        }
    }
}
