package io.github.progmodular.gestaorh.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;

public class CookieService {

    public static void setCookie(HttpServletResponse response, String key, String value, int seconds) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(key, URLEncoder.encode(value, "UTF-8"));
        cookie.setMaxAge(seconds);
        cookie.setPath("/"); // ← ADICIONE ESTA LINHA
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException {
        // Verificação mais robusta
        if (request.getCookies() == null) {
            return null;
        }

        // Stream mais explícito para debugging
        Optional<Cookie> cookieOptional = Arrays.stream(request.getCookies())
                .filter(cookie -> {
                    System.out.println("Cookie encontrado: " + cookie.getName()); // Para debug
                    return key.equals(cookie.getName());
                })
                .findFirst();

        if (cookieOptional.isPresent()) {
            String valor = cookieOptional.get().getValue();
            return URLDecoder.decode(valor, "UTF-8");
        }

        return null;
    }
}