package com.pj.project.service;

import com.pj.project.domain.User.User;
import com.pj.project.repository.UserRepository;
import com.pj.project.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;


@Transactional
@RequiredArgsConstructor
@Service
public class UserService  {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    /* 회원가입 */
    public Long userJoin(UserRequestDto dto) {
        /* 암호화 */
        dto.encryptPassword(encoder.encode(dto.getPassword()));

        User user = dto.toEntity();
        userRepository.save(user);

        return user.getId();
    }
    //유효성
    public Map<String, String> validName(Errors errors){
        Map<String, String> validName = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validName.put(validKeyName, error.getDefaultMessage());
        }
        return validName;
    }

    /* 아이디 중복체크 */
    public void chkName(UserRequestDto dto) {
        boolean chkName = userRepository.existsByName(dto.toEntity().getName());
        if (chkName) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

}