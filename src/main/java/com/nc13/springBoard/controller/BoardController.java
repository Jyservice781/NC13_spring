package com.nc13.springBoard.controller;

import com.nc13.springBoard.model.BoardDTO;
import com.nc13.springBoard.model.UserDTO;
import com.nc13.springBoard.service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board/")
public class BoardController {
    @Autowired
    private BoardService boardService;


    @GetMapping("showAll")
    public String showAll(HttpSession session, Model model) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        List<BoardDTO> list = boardService.selectAll();
        model.addAttribute("list", list);

        return "board/showAll";
    }


    @GetMapping("write")
    public String showWrite(HttpSession session) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }
        return "board/write";
    }

    @PostMapping("write")
    public String write(HttpSession session, BoardDTO boardDTO) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }
        boardDTO.setWriterId(logIn.getId());

        boardService.insert(boardDTO);

        System.out.println(boardDTO);

        return "redirect:/board/showAll";
    }

    //우리가 주소창에 있는 값을 매핑해줄 수 있다.
    @GetMapping("showOne/{id}")
    public String showOne(HttpSession session, @PathVariable int id, Model model){
        UserDTO logIn = (UserDTO)session.getAttribute("logIn");
        if(logIn == null){
            return "redirect:/";
        }

        BoardDTO boardDTO = boardService.selectOne(id);
        model.addAttribute("boardDTO", boardDTO);

        return "/board/showOne";
    }

}
