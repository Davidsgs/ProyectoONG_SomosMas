package com.restteam.ong.controllers.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberPageableDTO {
    String previousURL;
    List<MemberDTO> memberList;
    String nextUrl;
}
