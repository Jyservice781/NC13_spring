package com.nc13.springBoard.controller;

import com.nc13.springBoard.model.UserDTO;
import com.nc13.springBoard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// 전부 다 URL 이 로컬호스트 8080/user 하면 다 한곳으로 보내고 싶다.
// requestMapping 으로 /user/ 라고 매핑해줌
@RequestMapping("/user/")
@RequiredArgsConstructor

public class UserController {
    // 실제 SQL 통신을 담당할 Service 객체
    @Autowired
    private UserService userService;

    // 사용자가 로그인을 할 시 실행할
    // auth 메소드
    @PostMapping("auth")
    // POST 혹은 GET 방식으로 웹 페이지의 값을 받아올때는
    // 파라미터에 해당 form 의 name 어트리뷰트와 같은 이름을 가진
    // 파라미터를 적어주면 된다.

    // 또한, 해당 name 어트리뷰트를 가진 클래스 객체를 파라미터로 잡아주면
    // 자동으로 데이터가 바인딩 된다.
    public String auth(UserDTO userDTO) {
        // System.out.println("username: " + username + ", " + "password: " + password);
        UserDTO result = userService.auth(userDTO);
        System.out.println(result);
        // 데이터 정제하기 페이지 연결해주기
        // 이동할 페이지의 이름을 리턴하기 때문에 String 으로 값을 지정해줌.
        // 만약 우리가 해당 메소드를 실행시키고 나서 특정 URL 로 이동시킬 때에는 다음과 같이 적어준다.

        return "redirect:/";
        // "/" 는 이동할 파일 위치를 표현한다.
        // redirect 는 URL 을 강제로 이동시킬때 사용한다.
    }
}
