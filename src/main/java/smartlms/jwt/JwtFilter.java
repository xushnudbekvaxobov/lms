package smartlms.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import smartlms.dto.response.JwtResponseDto;
import smartlms.exception.UnauthorizedException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.equals("/api/auth/login")
                || path.equals("/api/auth/register/student")
                || path.equals("/api/auth/register/teacher")
                || path.equals("/api/groups/create")
                || path.startsWith("/api/auth/refresh-token/")
                || path.startsWith("/swagger-ui/")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            handlerExceptionResolver.resolveException(request, response, null, new UnauthorizedException("Authorization header is missing or invalid"));
            return;
        }
        final String token = header.substring(7).trim();
        try {
            JwtResponseDto jwtResponseDto = jwtService.extractClaims(token);
            String username = jwtResponseDto.getUsername();
            String role = jwtResponseDto.getRole();
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                java.util.List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            handlerExceptionResolver.resolveException(request, response, null, new UnauthorizedException("Authentication token has expired"));
            return;
        } catch (JwtException ex) {
            handlerExceptionResolver.resolveException(request, response, null, new UnauthorizedException("Authentication token is invalid"));
            return;
        }
    }
}
