package com.pj.project.config.auth;

import com.pj.project.controller.UserController;
import com.pj.project.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService customUserDetailsService;
    //실패 컨트롤러
    private final AuthenticationFailureHandler customFailureHandler;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config.........");

        http
                .csrf().ignoringAntMatchers("/user/**","/posts/**")
                .and()
                .authorizeRequests()
                //.antMatchers("/posts/**").hasRole(Role.GUEST.name())  // 회원들만 이용가능 user/~
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")  //성공 시 이동
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/posts/list")
                .usernameParameter("name")
                .failureHandler(customFailureHandler) //로그인 실패 핸들
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/") // 인증이 필요한 URL에 접근하면 /으로 이동
                .defaultSuccessUrl("/posts/list")     // 로그인 성공하면 "/posts/list" 으로 이동
                .failureUrl("/")		// 로그인 실패 시 / 이동
                .userInfoEndpoint() // OAuth2 로그인 성공 후 가져올 설정
                .userService(customOAuth2UserService); // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
    }


    /* 시큐리티가 로그인 과정에서 password를 가로챌때 어떤 해쉬로 암호화 했는지 확인 */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
    /* AuthenticationManager Bean 등록 */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}