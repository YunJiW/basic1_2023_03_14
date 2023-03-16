package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.rsData.RsData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberService {
    private  final MemberRepository memberRepository;


    public RsData tryLogin(String username, String password) {
        if(!password.equals("1234")){
            return RsData.of("F-1","비밀번호가 일치하지않습니다.");
        } else if(!username.equals("user1")){
            return RsData.of("F-2","%s 은(는) 존재하지 않습니다.".formatted(username));
        }

        return RsData.of("S-1","%s 님 환영합니다".formatted(username));
    }
    //Service에 몰빵
    public RsData tryLogin1(String username, String password) {
        List<Member> memberList = memberRepository.getMemberList();


        //아이디를 찾는다.
        for(Member member : memberList){
            //같으면 비밀번호까지 체크
            if(member.getUsername().equals(username)){
                if(member.getPassword().equals(password)){
                    return RsData.of("S-1","%s 님 환영합니다".formatted(username));
                }
                else
                    return RsData.of("F-1","비밀번호가 일치하지않습니다.");
            }
        }
        return RsData.of("F-2","%s 는 존재하지 않습니다.".formatted(username));
    }

    //findbyname으로 해보기
    public RsData tryLogin2(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        //없는경우 존재하지않음 리턴
        if (member == null) {
            return RsData.of("F-2", "%s 는 존재하지 않습니다.".formatted(username));
        }

        //있는데 비밀번호가 다르면 비밀번호 다르다 리턴
        if (!member.getPassword().equals(password)) {
            return RsData.of("F-1", "비밀번호가 일치하지않습니다.");
        }
        //성공시 성공출력
        return RsData.of("S-1", "%s 님 환영합니다".formatted(username),member.getId());
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member findById(long id) {
        return memberRepository.findById(id);
    }
}
