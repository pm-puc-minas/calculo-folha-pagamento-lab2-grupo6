package io.github.progmodular.gestaorh.infra.security;

import io.github.progmodular.gestaorh.infra.web.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if(CookieService.getCookie(request,"usuarioId") != null)
        {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
