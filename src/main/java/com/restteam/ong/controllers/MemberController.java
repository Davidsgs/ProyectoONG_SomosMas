package com.restteam.ong.controllers;


import com.restteam.ong.models.Member;
import com.restteam.ong.services.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/members/")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    public void addMember(@RequestBody Member member){
        memberService.addMember(member);
    }
}
