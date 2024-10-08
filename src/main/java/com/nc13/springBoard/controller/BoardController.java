package com.nc13.springBoard.controller;

import com.nc13.springBoard.model.BoardDTO;
import com.nc13.springBoard.model.ReplyDTO;
import com.nc13.springBoard.model.UserDTO;
import com.nc13.springBoard.service.BoardService;
import com.nc13.springBoard.service.ReplyService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/board/")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private ReplyService replyService;

    @GetMapping("showAll")
    public String moveToFirstPage() {
        return "redirect:/board/showAll/1";
    }

    @GetMapping("showAll/{pageNo}")
    public String showAll(Model model, @PathVariable int pageNo) {
        //가장 마지막 페이지의 번호
        int maxPage = boardService.selectMaxPage();
        model.addAttribute("maxPage", maxPage);

        // 우리가 이제 pageNo 를 사용하여
        // 시작 페이지 번호와
        // 끝 페이지 번호
        // 를 계산해 주어야 한다.
        // 이때에는 크게 3가지가 있다.

        // 1. 현재 페이지가 3 이하일 경우
        // 시작: 1, 끝: 5

        // 2. 현재 페이지가 최대 페이지 -2 이상일 경우
        // 시작: 최대페이지 -4, 끝: 최대페이지
        // DB 한테 물어봐야함- > service, mapper

        // 3. 그외
        // 시작: 현재페이지-2 끝: 현재페이지 + 2

        // 시작 페이지
        int startPage;

        // 끝 페이지
        int endPage;


        if (maxPage < 5) {
            startPage = 1;
            endPage = maxPage;
        } else if (pageNo <= 3) {
            startPage = 1;
            endPage = 5;
        } else if (pageNo >= maxPage - 2) {
            startPage = maxPage - 4;
            endPage = maxPage;
        } else {
            startPage = pageNo - 2;
            endPage = pageNo + 2;
        }

        model.addAttribute("curPage", pageNo);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // List<BoardDTO> list = boardService.selectAll();
        List<BoardDTO> list = boardService.selectAll(pageNo);
        model.addAttribute("list", list);


        return "board/showAll";
    }


    @GetMapping("write")
    public String showWrite() {
        return "board/write";
    }

    //우리가 주소창에 있는 값을 매핑해줄 수 있다.

    // @ResponseBody
    // MultipartFile file 로 바인딩을 한다.
    @PostMapping("write")
    public String write(BoardDTO boardDTO, Authentication authentication) {
        UserDTO logIn = (UserDTO) authentication.getPrincipal();
        boardDTO.setWriterId(logIn.getId());
        boardService.insert(boardDTO);

        return "redirect:/board/showOne/" + boardDTO.getId();

        /*
        // 어디에 저장을 해줄지 지정을 해야함. c: 에 강제로 들어가게 처리를 해줌.
        String path = "c:\\uploads\\a\\bb\\cc";
        // mkdirs()는 만약 경로가 복잡하면 c:\\uploads\\a\\b\\c 라면 a> B> C 의 형가 됨.

        new File(path).mkdirs();
        File pathDir = new File(path);

        //만약 이미 path 에 지정한 폴더가 존재한다면
        // 만들지 못하도록 if 문으로 지정함.
        if(!pathDir.exists()){
            pathDir.mkdir();
        }*/

        // App data 는 사람이 직접 접근을 해서는 안되는 파일 중에 하나임.
        // file 을 다른 서버에 업로드하고 내가 원하는 경로를 새로 파서 다운해주는 방식
        // 사용자가 업로드할때의 해당 파일을 지칭하게 됨.
        // File f = new File(path, file.getOriginalFilename());

        // MultipartFile 라이브러리에서 지원하는 기능
        // transferTo을 사용하여 파일의 위치를 바꿔줌.
        /* try {
            for(MultipartFile mf : file){
                File f = new File(path, mf.getOriginalFilename());
                mf.transferTo(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @GetMapping("showOne/{id}")
    public String showOne(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {

        BoardDTO boardDTO = boardService.selectOne(id);

        if (boardDTO == null) {
            redirectAttributes.addFlashAttribute("message", "해당 글 번호는 유효하지 않습니다.");
            return "redirect:/showMessage";
        }

        List<ReplyDTO> replyList = replyService.selectAll(id);

        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("replyList", replyList);

        return "board/showOne";
    }

    @GetMapping("update/{id}")
    public String showUpdate(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        BoardDTO boardDTO = boardService.selectOne(id);

        if (boardDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 글 번호 입니다.");
            return "redirect:/showMessage";
        }

        if (boardDTO.getWriterId() != logIn.getId()) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }

        model.addAttribute("boardDTO", boardDTO);

        return "board/update";
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes, BoardDTO attempt) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        BoardDTO boardDTO = boardService.selectOne(id);

        if (boardDTO == null) {
            redirectAttributes.addFlashAttribute("message", "유효하지 않은 글 번호입니다.");
            return "redirect:/showMessage";
        }

        if (logIn.getId() != boardDTO.getWriterId()) {
            redirectAttributes.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/showMessage";
        }
        // form 에서 title content 만 가져오기 때문에 attempt 를 통해 동일한 id 값을 저장해줌 .attempt
        attempt.setId(id);
        boardService.update(attempt);

        return "redirect:/board/showOne/" + id;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        BoardDTO boardDTO = boardService.selectOne(id);
        if (boardDTO == null) {
            redirectAttributes.addFlashAttribute("message", "존재하지 않는 글번호입니다.");
            return "redirect:/showMessage";
        }

        if (boardDTO.getWriterId() != logIn.getId()) {
            redirectAttributes.addFlashAttribute("message", "권한 없음");
            return "redirect:/showMessage";
        }

        boardService.delete(id);

        return "redirect:/board/showAll";
    }

 /*
    @GetMapping("test")
    public String test(HttpSession session) {
        UserDTO logIn = (UserDTO) session.getAttribute("logIn");
        if (logIn == null) {
            return "redirect:/";
        }

        for (int i = 1; i <= 300; i++) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setTitle("테스트제목 " + i);
            boardDTO.setContent("테스트 " + i + "번 글의 내용입니다.");
            boardDTO.setWriterId(logIn.getId());
            boardService.insert(boardDTO);
        }

        return "redirect:/board/showAll";



        300 개의 게시글이 있을 경우 만약 총 15개의 페이지로 나눈다면 한페이지에 들어가는 글의 갯수 20개
        147개 있으면 나오는 총 페이지의 갯수는?
        8개의 페이지가 나와야 함.

        << < 1 2 [3] 4 5 > >>
        <<, >> : 맨앞, 뒤로
        <, > : 특정 단위 갯수만큼 앞, 뒤로
        []: 현재 보고 있는 페이지
    }
    */

    // 일반 컨트롤러 안에
    // Restful API 로써, JSON 의 결과값을 리턴해야하는 경우
    // 맵핑 어노테이션 위에 ResponseBody 어노테이션을 붙여준다.
    @ResponseBody
    @PostMapping("uploads")
    public Map<String, Object> uploads(MultipartHttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();

        String uploadPath = "";

        MultipartFile file = request.getFile("upload");
        // 덮어 씌워질 위험을 막는 방법 -> 업로드의 이름을 고유하게 랜덤으로 바꾸면 됨
        String fileName = file.getOriginalFilename();
        // extension == 파일 확장자 찾아내느 코드.
        String extension = fileName.substring(fileName.lastIndexOf("."));
        //UUID
        String uploadName = UUID.randomUUID() + extension;
        // 지금 현재 돌아가고 있는 톰켓의 리얼 주소를 찾는 매서드임.
        String realPath = request.getServletContext().getRealPath("/board/uploads/");
        Path realDir = Paths.get(realPath);

        if(!Files.exists(realDir)){
            try{
                Files.createDirectories(realDir);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        File uploadFile = new File(realPath + uploadName);
        try{
            file.transferTo(uploadFile);
        } catch (IOException e){
            System.out.println("파일 전송 중 에러");
            e.printStackTrace();
        }

        uploadPath = "/board/uploads/" + uploadName;

        resultMap.put("uploaded", true);
        resultMap.put("url", uploadPath);
        // string 이 그대로 리턴됨.
        return resultMap;
    }

}
