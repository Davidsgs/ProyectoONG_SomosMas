package com.restteam.ong.controllers;


import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;
import com.restteam.ong.services.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    public void addMember(@RequestBody Member member){
        memberService.addMember(member);
    }

    @GetMapping()
    public List<MemberDTO> getMembers(){
        return memberService.getMembers();
    }
}
