package com.nc13.springBoard.controller;

import com.nc13.springBoard.model.ReplyDTO;
import com.nc13.springBoard.model.UserDTO;
import com.nc13.springBoard.service.BoardService;
import com.nc13.springBoard.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reply/")
public class ReplyController {
    @Autowired
    private ReplyService replyService;
    @Autowired
    private BoardService boardService;

    @PostMapping("insert/{boardId}")
    public String insert(ReplyDTO replyDTO, HttpSession session, @PathVariable int boardId, RedirectAttributes redirectAttributes){
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if(logIn == null){
            return "redirect:/";
        }

        if(boardService.selectOne(boardId) == null){
            redirectAttributes.addFlashAttribute("message", "유효하지 않은 글 번호입니다.");
            return "redirect:/showMessage";
        }

        //logIn이 된 상태라면 로그인 값에 글쓴이 아이디 값을 넣어주면 됨 -> 유효함..
        replyDTO.setWriterId(logIn.getId());
        replyDTO.setBoardId(boardId);
        //유효한 값이기 때문에 insert 실행해줌.
        replyService.insert(replyDTO);


        return "redirect:/board/showOne/" + boardId;
    }

    @PostMapping("update/{id}")
    public String update(ReplyDTO replyDTO, @PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes){
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");

        if(logIn == null){
            return "redirect:/";
        }

        ReplyDTO origin = replyService.selectOne(id);
        //
        if(origin == null){
            redirectAttributes.addFlashAttribute("message", "유효하지 않은 댓글 번호입니다.");
            return "redirect:/showMessage";
        }

        //본인말고 수정불가
        if(origin.getWriterId() != logIn.getId()){
            redirectAttributes.addFlashAttribute("message","권한이 없습니다.");
            return "redirect:/showMessage";
        }

        //수정 글을 입력
        replyDTO.setId(id);

        replyService.update(replyDTO);

        return "redirect:/board/showOne/" + origin.getBoardId();
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes){
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");

        if(logIn == null){
            return "redirect:/";
        }

        ReplyDTO  selectID = replyService.selectOne(id);

        if(selectID == null){
            redirectAttributes.addFlashAttribute("message", "잘못된 번호입니다.");
            return "redirect:/showMessage";
        }

        if(selectID.getWriterId() != logIn.getId()){
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }

        replyService.delete(id);

        return "redirect:/board/showOne/" + selectID.getBoardId();
    }
}
