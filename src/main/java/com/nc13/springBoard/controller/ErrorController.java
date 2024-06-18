package com.nc13.springBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ErrorController {
    // showMapping 이라는 direct 를 부여함.
    @GetMapping("/showMessage")
    public String showError(@ModelAttribute("message") String message, Model model) {

        model.addAttribute("message",message);
        // 우리가 jsp 로 값을 보낼때에는 model 이 필요하다
//        System.out.println("message: " + message);

        return "showMessage";
    }
}
