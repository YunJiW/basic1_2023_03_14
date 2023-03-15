package com.ll.basic1.boundedContext.home.controller;


import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    private int count;
    private int id;

    private final MemberService memberService;
    private final List<Person> lists;

    @Autowired
    public HomeController(MemberService memberService) {
        count = -1;
        id = 0;
        lists = new ArrayList<>();
        this.memberService = memberService;
    }
    @GetMapping("/home/user1")
    @ResponseBody
    public Member showUser1(){
        return memberService.findByUsername("user1");
    }

    @GetMapping("/home/main")
    @ResponseBody
    public String showMain() {
        return "안녕하세요";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2() {
        return "반갑습니다.";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3() {
        return "바꿈";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public int showMain4() {
        count++;
        return count;
    }

    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "1") int b) {
        return a + b;
    }


    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(@RequestParam(defaultValue = "0") int age, @RequestParam(defaultValue = "이름없음") String name) {
        Person p = new Person(age, name);
        lists.add(p);

        return p.getId() + "번째 추가";
    }

    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(int id) {

        //Stream 형식
        //조건에 맞는 게 있다면 삭제된다. 삭제된다면 true값을 가짐
        boolean removed = lists.removeIf(Person -> Person.getId() == id);
        //강사님 코드
        if (removed == false) {
            return id + "번사람이 존재하지않습니다.";
        }
        /* for문으로 내가 짠거 => 기본
        for(int idx = 0; idx <lists.size();idx++){
            if(lists.get(idx).getId() == id){
                lists.remove(idx);
                return id + "번 사람 제거";
            }
        }
        return id + "번사람이 존재하지않습니다.";
         */
        return id + "번 사람이 삭제되었습니다.";
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPerson(int id, String name, int age) {

        for (int idx = 0; idx < lists.size(); idx++) {
            if (lists.get(idx).getId() == id) {
                lists.get(idx).setAge(age);
                lists.get(idx).setName(name);
                return id + "번 사람이 수정되었습니다.";
            }
        }
        return id + "번 사람이 존재 하지 않습니다.";
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showpeople() {
        return lists;
    }


    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int countInCookie = 0;

        if (req.getCookies() != null) {
            countInCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }

        int newCountInCookie = countInCookie + 1;

        resp.addCookie(new Cookie("count", newCountInCookie + ""));
        return newCountInCookie;
    }

    //stream안쓴버전
    @GetMapping("/home/cookies/increase")
    @ResponseBody
    public int showCookieIncrease2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int countInCookie = 0;

        Cookie[] cookies = req.getCookies();
        //쿠키가 있다면
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                countInCookie = Integer.valueOf(cookie.getValue());
            }

        }
        int newCookieCount = countInCookie + 1;

        resp.addCookie(new Cookie("count", newCookieCount + ""));

        return newCookieCount;
    }
}



@Getter
@Setter
@ToString
@AllArgsConstructor
class Person{
    private static int lastid;
    private int id;
    private int age;
    private String name;

    static {
        lastid = 0;
    }
    Person(int age, String name){
        this(++lastid,age,name);
    }

}
