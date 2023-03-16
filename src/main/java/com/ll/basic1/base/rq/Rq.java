package com.ll.basic1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.Enumeration;

@RequestScope
@AllArgsConstructor
@Component
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

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
    public boolean removeSession(String name){
        HttpSession session = req.getSession();

        if(session.getAttribute(name) == null) return false;

        session.removeAttribute(name);
        return true;
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
    public void setSession(String name,long value){
        HttpSession session = req.getSession();
        session.setAttribute(name,value);
    }

    public long getSessionAsLong(String name, long defaultValue){
        try {
            long value = (long)req.getSession().getAttribute(name);
            return value;
        }catch (Exception e){
            return defaultValue;
        }
    }

    public String getSessionAsStr(String name, String defaultValue){
        try{
            String value =(String)req.getSession().getAttribute(name);
            return value;
        }catch (Exception e){
            return defaultValue;
        }
    }

    public String getSessionDebugContents(){
        HttpSession session = req.getSession();
        StringBuilder sb = new StringBuilder("Session content:\n");

        Enumeration<String> attributeNames = session.getAttributeNames();
        while(attributeNames.hasMoreElements()){
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            sb.append(String.format("%s: %s\n",attributeName,attributeValue));
        }

        return sb.toString();
    }
}
