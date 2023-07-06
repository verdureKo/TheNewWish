package com.sparta.wish.jwtUtil;

import com.sparta.wish.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter { //세션 방식이 아니라 jwt 방식을 사용하기 때문에 상속받은 후 구현

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/users/login/info");
        //상속받으면 사용할 수 있는 메소드 : 로그인 페이지를 직접 설정함

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 로그인을 시도하는 메소드
        log.info("로그인 시도");

        Enumeration params = request.getParameterNames();
        while(params.hasMoreElements()) {
            String name = (String) params.nextElement();
            System.out.print(name + " : " + request.getParameter(name) + "     ");
        }


        log.info("request={}", request);
        try {
            // json 형식을 ObjectMapper 읽어서 객체로 변환함 첫번째는 받는 값, 두번째는 변환한 타입
//            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            log.info("아이디랑 비밀번호 출력={}", username + password);

            return getAuthenticationManager().authenticate( // 매니저를 사용해서, authenticate 메소드를 사용함 검증 역할 수행
                    new UsernamePasswordAuthenticationToken( //토큰을 만들고, 유저네임, 비밀번호, 권한은 없음으로 넣음
                            username,
                            password,
                            null
                    )
            );

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    // 로그인이 성공했을 때 나오는 메시지
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //@Authentication authResult 에 객체가 들어 있다.
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        //역할은 생략함
        log.info("로그인 성공, 토큰 생성");
        String token = jwtUtil.createToken(username); // 토근 생성
        jwtUtil.addJwtToCookie(token, response); // 쿠키에 토근 넣기
        response.sendRedirect(request.getContextPath() + "/challenges"); // Redirect to "/challenges"
    }

    // 로그인 실패했을 때 실행
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}
