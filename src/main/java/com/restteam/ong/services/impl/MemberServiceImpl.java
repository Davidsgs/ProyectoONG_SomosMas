package com.restteam.ong.services.impl;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.controllers.dto.MemberPageableDTO;
import com.restteam.ong.models.Member;
import com.restteam.ong.repositories.MemberRepository;
import com.restteam.ong.services.MemberService;
import com.restteam.ong.util.PageableCreator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    private final PageableCreator pageableCreator;
    private final ModelMapper modelMapper = new ModelMapper();

    private MemberDTO mapToDTO(Member member) {
        return modelMapper.map(member, MemberDTO.class);
    }

    @Override
    public Optional<Long> addMember(Member member) {
        if (member.getName().isBlank()) {
            return Optional.empty();
        }
        member.setCreatedAt(currentTimeMillis() / 1000);
        memberRepository.save(member);
        return Optional.of(member.getId());
    }

    private boolean nameNotNull(Member member) {
        return !member.getName().isEmpty();
    }

    @Override
    public MemberPageableDTO getMembers(int pageId) {
        MemberPageableDTO memberPageableDTO = new MemberPageableDTO();
        Pageable page = pageableCreator.goToPage(pageId);
        Slice<Member> pagedList = memberRepository.findAll(page);
        List<MemberDTO> pagedMembersDTO = pagedList.getContent().stream().map(it -> modelMapper.map(it,MemberDTO.class)).collect(Collectors.toList());
        memberPageableDTO.setMemberList(pagedMembersDTO);
        if(pagedList.hasPrevious()){
            memberPageableDTO.setPreviousURL("http://localhost:8080/members/"+(pageId-1));
        }
        if(pagedList.hasNext()){
            memberPageableDTO.setNextUrl("http://localhost:8080/members/"+(pageId+1));
        }
        return memberPageableDTO;
    }

    @Override
    @Transactional
    public Optional<Member> updateMember(Long memberId, String name, String facebook, String instagram, String linkedin,
            String image, String description) {
        // Si anda ok devuelve al miembro, si no, devuelve null.
        try {
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("..."));
            if (name != null) {
                member.setName(name);
            }
            if (facebook != null) {
                member.setFacebookUrl(facebook);
            }
            if (instagram != null) {
                member.setInstagramUrl(instagram);
            }
            if (linkedin != null) {
                member.setLinkedinUrl(linkedin);
            }
            if (image != null) {
                member.setImage(image);
            }
            if (description != null) {
                member.setDescription(description);
            }
            return Optional.of(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> deleteMember(Long memberId) {
        boolean exists = memberRepository.existsById(memberId);
        if (exists) {
            memberRepository.deleteById(memberId);
            return Optional.of(memberId);
        }
        return Optional.empty();
    }

}
