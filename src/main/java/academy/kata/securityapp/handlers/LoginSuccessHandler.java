package academy.kata.securityapp.handlers;

import academy.kata.securityapp.models.RoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        UserDetails user = (UserDetails) authentication.getPrincipal();

        if (authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority
                        .getAuthority().equals("ROLE_" + RoleEnum.ADMIN.name()))) {
            response.sendRedirect("/admin");
            return;
        }
        if (authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority
                        .getAuthority().equals("ROLE_" + RoleEnum.USER.name()))) {
            response.sendRedirect("/user");
            return;
        }
        response.sendRedirect("/");

    }
}
