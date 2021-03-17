package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!!!?!");
        return "hello"; // resources/templates/hello.html 을 실행시켜라는
    }

    /*
    웹 브라우저에서 `localhost:8080/hello` 를 검색하면 스프링 서버에서 일어나는 일

    1. tomcat이 받음
    2. spring 컨테이너에 hello 맵핑되어있는 친구를 찾음
    3. GetMapping("hello") 인 HelloController.hello() 를 실행
        - 이때, 스프링에서 model을 주입
    4. model에 data: "hello!!!!?!" 를 입력
    5. return "hello"
    6. viewResolver가 "hello" 리턴값을 받아 resoureces/templates/hello.html을 렌더링
        - 렌더링 시 model에 담겨져 있던 data 값을 th 문법에 맞게 렌더링
     */

}