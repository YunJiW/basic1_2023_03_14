package com.ll.basic1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.Arrays;


public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    public Rq(HttpServletRequest req,HttpServletResponse resp){
        this.req = req;
        this.resp = resp;
    }

    public void removeCookie(String loginedMemberId) {
        if(req.getCookies() != null){
            Arrays.stream(req.getCookies())
                    .filter(c -> c.getName().equals(loginedMemberId))
                    .forEach(cookie ->
                    {cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }
    }

    public long getCookieAsLong(String name, int defaultValue) {
        String value = getCookie(name,null);
        
        if(value == null){
            return defaultValue;
        }
        
        //예외상황 발생시 디폴트값을 반환
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e){
            return defaultValue;
        } //

        
    }

    private String getCookie(String name, String defaultValue) {
        if(req.getCookies() ==null){
            return defaultValue;
        }

        //name의 이름을 가진 값이 존재하는 경우 그값을 반환하고 없는경우 default로 나감.
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(defaultValue);
    }

    public void setCookie(String name, long value) {
        setCookie(name,value + "");
    }
    public void setCookie(String name,String value){
        resp.addCookie(new Cookie(name,value));
    }
}
