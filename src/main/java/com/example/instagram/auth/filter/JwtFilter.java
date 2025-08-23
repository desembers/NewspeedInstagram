package com.example.instagram.auth.filter;

import com.example.instagram.auth.JwtTokenProvider;
import com.example.instagram.auth.service.TokenValidCheckService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class JwtFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/auth/signup", "/auth/login"};

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenValidCheckService tokenValidCheckService;

    public JwtFilter(
            JwtTokenProvider jwtTokenProvider,
            TokenValidCheckService tokenValidCheckService
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenValidCheckService = tokenValidCheckService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 다양한 기능을 사용하기 위해 다운 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        // 다양한 기능을 사용하기 위해 다운 캐스팅
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // 로그인을 체크 해야하는 URL인지 검사
        // whiteListURL에 포함된 경우 true 반환 -> !true = false
        if (!isWhiteList(requestURI)) {

            // 로그인 확인 -> 로그인하면 session에 값이 저장되어 있다는 가정.
            // 세션이 존재하면 가져온다. 세션이 없으면 session = null
            String authorization = httpRequest.getHeader("Authorization");
            //"Bearer ..."


//            if (authorization == null || !authorization.startsWith("Bearer ")) {
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 해주세요.");
//            }

            if (authorization == null || !authorization.startsWith("Bearer ") || authorization.length() <= 7) {
                httpResponse.setContentType("application/json; charset=UTF-8");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("로그인 해주세요.");
                return; // 여기서 바로 종료
            }

            // 인증을 처리해서 token이 있는 경우
            String accessToken = authorization.substring(7);

            // 토큰 유효성 체크 (db에 저장된 만료된 토큰인지 확인)
//            if (!tokenValidCheckService.isValid(accessToken)) {
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "만료되었거나 유효하지 않은 토큰입니다.");
//            }

            if (accessToken.isEmpty() || !tokenValidCheckService.isValid(accessToken)) {
                httpResponse.setContentType("application/json; charset=UTF-8");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("만료되었거나 유효하지 않은 토큰입니다.");
                return; // 여기서 바로 종료
            }

            Claims claims = jwtTokenProvider.getClaims(accessToken);
            Long userId = ((Number)claims.get("userId")).longValue();

            httpRequest.setAttribute("userId", userId);

        }

        // 1번경우 : whiteListURL에 등록된 URL 요청이면 바로 chain.doFilter()
        // 2번경우 : 필터 로직 통과 후 다음 필터 호출 chain.doFilter()
        // 다음 필터 없으면 Servlet -> Controller 호출
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 로그인 여부를 확인하는 URL인지 체크하는 메서드
    private boolean isWhiteList(String requestURI) {
        // request URI가 whiteListURL에 포함되는지 확인
        // 포함되면 true 반환
        // 포함되지 않으면 false 반환
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
