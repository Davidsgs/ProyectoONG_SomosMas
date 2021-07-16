package com.restteam.ong.services.impl;

import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;
import com.restteam.ong.repositories.MemberRepository;
import com.restteam.ong.services.MemberService;
import com.restteam.ong.util.PageableCreator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<MemberDTO> getMembers(int pageId) {
        /*Iterable<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member member : memberList) {
            memberDTOList.add(mapToDTO(member));
        }
        return memberDTOList;*/
        Pageable page = pageableCreator.goToPage(pageId);
        List<Member> pagedList = memberRepository.findAll(page).toList();
        Stream<MemberDTO> pagedDTOList = (pagedList.stream().map(this::mapToDTO));
        return pagedDTOList.collect(Collectors.toList());
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
