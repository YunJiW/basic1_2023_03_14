package com.ll.basic1.boundedContext.member.controller;


import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private  final Rq rq;


    @PostMapping("/member/login")
    @ResponseBody
    public RsData login(String username,String password){

        if(username == null || username.trim().length() == 0)
        {
            return RsData.of("F-3","username을 입력해주세요");
        }
        if(password == null || password.trim().length() == 0 ){
            return RsData.of("F-3","password을 입력해주세요");
        }


        RsData rsData = memberService.tryLogin2(username,password);
        //로그인이 되는경우 -> 쿠키를 넣어줌.
        if(rsData.isSucess()){
            Member member = (Member) rsData.getData();
            rq.setSession("loginedMemberId", member.getId());
        }
        return rsData;
    }
    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout() {
        boolean cookieRemoved = rq.removeSession("loginedMemberId");

        if(!cookieRemoved){
            return RsData.of("S-2", "이미 로그아웃 상태입니다.");
        }

        return RsData.of("S-1","로그아웃");

    }

    @GetMapping("/member/me")
    public String isYou(Model model){
        long loginedMemberId = rq.getSessionAsLong("loginedMemberId",0);

        Member member = memberService.findById(loginedMemberId);
        model.addAttribute("member",member);

        return "usr/member/me";

    }

    @GetMapping("/member/login")
    public String showLogin() {
        return "usr/member/login";
    }
}
