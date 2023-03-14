package com.ll.basic1;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private int count;
    private int id;

    List<Person> lists;

    public HomeController(){
        count = -1;
        id = 0;
        lists = new ArrayList<>();
    }

    @GetMapping("/home/main")
    @ResponseBody
    public String showMain(){
        return "안녕하세요";
    }
    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2(){
        return "반갑습니다.";
    }
    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3(){
        return "바꿈";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public int showMain4(){
        count++;
        return count;
    }

    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a,@RequestParam(defaultValue = "1") int b){
        return a+b;
    }


    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPerson(@RequestParam(defaultValue = "0") int age,@RequestParam(defaultValue = "이름없음") String name){
        id +=1;
        lists.add(new Person(id,age,name));

        return id + "번째 추가";
    }
    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePerson(int id){

        //Stream 형식
        //조건에 맞는 게 있다면 삭제된다. 삭제된다면 true값을 가짐
        boolean removed = lists.removeIf(Person -> Person.getId() == id);
        //강사님 코드
        if(removed == false){
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
    public String modifyPerson(int id,String name,int age){

        for(int idx = 0; idx <lists.size();idx++){
            if(lists.get(idx).getId() == id){
                lists.get(idx).setAge(age);
                lists.get(idx).setName(name);
                return id + "번 사람이 수정되었습니다.";
            }
        }
        return id + "번 사람이 존재 하지 않습니다.";
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showpeople(){
        return lists;
    }
}
@Getter
@Setter
@ToString
@AllArgsConstructor
class Person{
    private int id;
    private int age;
    private String name;

}
