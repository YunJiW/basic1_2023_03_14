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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/login")
    @ResponseBody
    public RsData login(String username,String password,HttpServletResponse resp,HttpServletRequest req){
        Rq rq = new Rq(req,resp);

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
            rq.setCookie("loginedMemberId",member.getId());
        }
        return rsData;
    }
    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout(HttpServletRequest req,HttpServletResponse resp) {
        Rq rq = new Rq(req,resp);
        rq.removeCookie("loginedMemberId");

        return RsData.of("S-1","로그아웃");

    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData isYou(HttpServletRequest req,HttpServletResponse resp){

        Rq rq = new Rq(req,resp);
        long loginedMemberId = rq.getCookieAsLong("loginedMemberId",0);

        boolean isLogined = loginedMemberId > 0;

        if(!isLogined){
            return RsData.of("F-1","로그인 후 이용해 주세요");
        }
        Member member = memberService.findById(loginedMemberId);

        return RsData.of("S-1","당신의 username은 %s입니다.".formatted(member.getUsername()));
    }
}
