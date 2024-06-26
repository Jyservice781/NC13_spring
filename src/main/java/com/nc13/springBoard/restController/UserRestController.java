package com.nc13.springBoard.restController;

import com.nc13.springBoard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserRestController {

    @Autowired
    UserService userService;

    @GetMapping("validateUsername")
    public Map<String, Object> validateUsername(String username){
        Map<String, Object> resultMap = new HashMap<>();
        // userService 로 String 으로 변경해서 업그레이드 시켜주기
        boolean result = userService.validateUsername(username);

        if(result){
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        // resultMap.put("result", "success");
        // resultMap.put("message", "Message From Rest Controller");
        // JSON 형식으로 처리하면 간편하게 중복아이디 확인할 수 있음.

        return resultMap;
    }
}
