package com.pj.project.config.auth;

import com.pj.project.domain.User.User;
import com.pj.project.repository.UserRepository;
import com.pj.project.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class ValidCheck implements Validator {

    private final UserRepository userRepository;

    /* 인스턴스가 검증 대상 타입인지 확인 */
    @Override
    public boolean supports(Class<?> clazz) {

        return clazz.isAssignableFrom(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequestDto user = (UserRequestDto) target;

        if (userRepository.existsByName(user.getName())) {
            errors.rejectValue("name", "invalid.name",
                    new Object[]{user.getName()}, "이미 사용중인 아이디 입니다.");
        }

    }
}
