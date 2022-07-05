package com.pj.project.domain.User;

import com.pj.project.domain.Role;
import com.pj.project.domain.TimeEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;



    /* 회원정보 수정 sns */
    public User update(String name) {
        this.name = name;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }


}