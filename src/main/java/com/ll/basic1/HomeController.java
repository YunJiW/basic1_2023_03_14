package com.ll.basic1;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showpeople(){
        return lists;
    }
}
@Getter
@ToString
@AllArgsConstructor
class Person{
    private int id;
    private int age;
    private String name;

}
