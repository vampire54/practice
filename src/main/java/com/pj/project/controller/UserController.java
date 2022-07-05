package com.pj.project.controller;

import com.pj.project.config.auth.ValidCheck;
import com.pj.project.dto.SessionUser;
import com.pj.project.service.UserService;
import com.pj.project.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final ValidCheck validCheck;
    private final HttpSession session;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/join")
    public String join(){

        return "user/joinForm";
    }
    @PostMapping("/user/join")
    public String userJoin(@Valid UserRequestDto dto, Errors errors, Model model) {
        validCheck.validate(dto, errors);

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
        }

        if (errors.hasErrors()) {
            /* 회원가입 실패시 입력 데이터 값을 유지 */
            model.addAttribute("dto", dto);

            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = userService.validName(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            /* 회원가입 페이지로 다시 리턴 */
            return "/user/joinForm";
        }
        /* 중복검사 */
        userService.chkName(dto);
        /* 가입 */
        userService.userJoin(dto);

        return "index";

    }
    @GetMapping("/")
    public String login(@RequestParam(value = "error", required = false)String error,
                        @RequestParam(value = "exception", required = false)String exception,
                        Model model){

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "index";
    }
    //회원정보
    @GetMapping("/user/detail")
    public String detail(Model model) {

        SessionUser user = (SessionUser) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);

        }
        return "user/userDetail";
    }
}