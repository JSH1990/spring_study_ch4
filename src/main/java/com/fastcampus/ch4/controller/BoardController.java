package com.fastcampus.ch4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session) {
        String writer = (String)session.getAttribute("id");
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);

            int regCnt = boardService.remove(bno, writer);

            if(regCnt==1)
                throw new Exception("board remove error");

        m.addAttribute("msg", "DELETE_OK");

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "DELETE_ERR");

        }

        return "redirect:/board/list"; //여기에 page값과 pageSize 값을 담으면 주소가 지저분해지므로, model담았다.
}

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m){
        try {
            BoardDto boardDto = boardService.read(bno); //아래문장과 동일
//            m.addAttribute("boardDto", boardDto);
            m.addAttribute(boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "board";
    }

    @GetMapping("/list")
    public String list(Integer page, Integer pageSize, Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        if(page==null) page=1;
        if (pageSize==null) pageSize=10;

        try {
            int totalCnt = boardService.getCount();
            PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);

            Map map = new HashMap();
            map.put("offset", (page-1)*pageSize);
            map.put("pageSize", pageSize);

           List<BoardDto> list =  boardService.getPage(map);
           m.addAttribute("list", list);
           m.addAttribute("ph", pageHandler);
           m.addAttribute("page", page);
           m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}