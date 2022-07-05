package com.pj.project.user;

import com.pj.project.domain.Role;
import com.pj.project.domain.User.User;
import com.pj.project.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    public void clear() {
        userRepository.deleteAll();
    }

    @Test
    public void 유저_생성_가져오기() {
        String name = "ted";
        String password = "ted";


        userRepository.save(User.builder().name(name).password(password).email("ssh595959@gmail.com").role(Role.ADMIN).build());

        List<User> userList = userRepository.findAll();

        User user = userList.get(0);

        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPassword()).isEqualTo(password);
    }
}