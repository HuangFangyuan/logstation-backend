//package com.hfy.logstation.filter;
//
//import com.auth0.jwt.interfaces.Claim;
//import com.hfy.logstation.exception.ResponseEnum;
//import com.hfy.logstation.util.JsonUtil;
//import com.hfy.logstation.util.JwtUtil;
//import com.hfy.logstation.util.ResponseUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//public class JwtFilter extends GenericFilterBean {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
//    public static final ThreadLocal<Integer> USER = new ThreadLocal<>();
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        LOGGER.info("request uri: {}", req.getRequestURI());
//        if (!req.getRequestURI().startsWith("/user")) {
//            String authHeader = req.getHeader("Authorization");
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                res.setContentType("application/json;charset=utf-8");
//                res.getWriter().write(JsonUtil.toJson(ResponseUtil.error(ResponseEnum.MISS_TOKEN)));
//                return;
//            }
//            String token = authHeader.substring(7);
//            Map<String, Claim> map = JwtUtil.verifyToken(token);
//            USER.set(map.get("uid").asInt());
//        }
//        filterChain.doFilter(req, res);
//    }
//}
