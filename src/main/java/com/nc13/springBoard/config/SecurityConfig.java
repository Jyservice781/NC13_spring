package com.nc13.springBoard.config;

import com.nc13.springBoard.service.UserAuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 회원가입이나 로그인등 페이지의 용도에 따라서
    // 접근 제한을 설정
    // FilterChain -> 웹에서 요청이 오면 순차적으로 처리되는 경우가 많음.
    // spring security 적용되는 부분 단순 파일만 되는 것이 아니라 전부다 적용됨.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserAuthService userAuthService) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)  // Cross Site Request Forgery -> 방지
                 // URL 별 권한 설정 -> 화살표 함수: 람다 함수
                .authorizeHttpRequests((authorize) -> authorize
                 // WEB-INF 폴더 안의 Views 안의 모든 JSP 파일은 누구든 접근 가능
                 .requestMatchers("/WEB-INF/**").permitAll()
                 // images 폴더와 해당 폴더 안의 모든 파일은 누구든 접근 가능
                 .requestMatchers("/images/**").permitAll()
                        // * - web-inf 안의 폴더 안의 직속 파일들만 가져옴
                        // ** - web-inf 안의 하위 폴더들과 그안의 파일들 까지 다 가져옴
                // localhost:8080/user/.... 이거나 localhost:8080/ 은 누구든 접근 가능을 설정
                .requestMatchers("/","/user/*").permitAll()
                .requestMatchers("/board/write").hasAnyRole("ADMIN")
                 //만약 여러개를 설정하려면 .requestMatchers("/board/write").hasAnyRole("ADMIN", "USER")
                // board/write 는 ADMIN 역할을 가진 사용자만 접근 가능
                // 위의 경우가 아닌 모든 URL 은 로그인한 사용자만 접근 가능하다
                .anyRequest().authenticated()
                )//-> anyRequest 의 기능이 모든 하위 링크들을 접근가능하게 하면서 에러를 나게 한다.
                // 내가 쓰고 싶은 폼을 등록시켜줌.
                .formLogin((formLogIn) -> formLogIn
                        // 로그인에서 사용할 페이지 설정
                        .loginPage("/")
                        // 로그인에서 username 을 어떤 name 어트리뷰트로 넘겨줄지 설정
                        .usernameParameter("username")
                        // 로그인 페이지에서 password 를 어떤 name 어트리뷰트로 넘겨줄지 설정
                        .passwordParameter("password")
                        // 로그인 성공시 이동할 페이지
                        .defaultSuccessUrl("/board/showAll/1")
                        // 로그인 처리 URl
                        .loginProcessingUrl("/user/auth"))
                // 내가 만든 userAuthService 등록
                .userDetailsService(userAuthService);

       return httpSecurity.build();
    }

    // 스프링 시큐리티를 사용할때 값을 복호화하기 위해서 사용되는 방법임.
    // 스프링 시큐리티(Spring Security) 프레임워크에서 제공하는 클래스
    // 중 하나로 비밀번호를 암호화하는 데 사용할 수 있는 메서드를 가진 클래스입니다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
