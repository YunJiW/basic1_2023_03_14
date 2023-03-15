package com.ll.basic1.boundedContext.member.controller;


import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username,String password){
        if(username == null || username.trim().length() == 0)
        {
            return RsData.of("F-3","username을 입력해주세요");
        }
        if(password == null || password.trim().length() == 0 ){
            return RsData.of("F-3","password을 입력해주세요");
        }
        return memberService.tryLogin2(username,password);
    }
}
