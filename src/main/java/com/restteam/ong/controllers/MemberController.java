package com.restteam.ong.controllers;


import com.restteam.ong.controllers.dto.MemberDTO;
import com.restteam.ong.models.Member;
import com.restteam.ong.services.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/members/")
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

    @PutMapping(path = ":{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String facebook,
                                       @RequestParam(required = false) String instagram,
                                       @RequestParam(required = false) String linkedin,
                                       @RequestParam(required = false) String image,
                                       @RequestParam(required = false) String description){
        return memberService.updateMember(memberId,name,facebook,instagram,linkedin,image,description);
    }

    @DeleteMapping(path = ":{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId){
        return memberService.deleteMember(memberId);
    }
}
