package kr.co.ch06.controller;

import kr.co.ch06.dto.User2DTO;
import kr.co.ch06.repository.User2Repository;
import kr.co.ch06.service.User2Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class User2Controller {

    private final User2Service service;

    @GetMapping("/user2/list")
    public String list(Model model){

        List<User2DTO> dtoList = service.getUsers();
        model.addAttribute("dtoList",dtoList);

        return "/user2/list";
    }

    @GetMapping("/user2/register")
    public String register(){
        return "/user2/register";
    }

    @PostMapping("/user2/register")
    public String register(User2DTO user2DTO){
        log.info(user2DTO.toString());

        service.save(user2DTO);

        return "redirect:/user2/list";
    }

    @GetMapping("/user2/modify")
    public String modify(String userid, Model model){

        User2DTO user2DTO = service.getUser(userid);
        model.addAttribute(user2DTO);

        return "/user2/modify";
    }
    @PostMapping("/user2/modify")
    public String modify(User2DTO user2DTO){

        service.modify(user2DTO);

        return "redirect:/user2/list";
    }




    @GetMapping("/user2/delete")
    public String delete(String userid){
        service.delete(userid);
        return "redirect:/user2/list";
    }
}