package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if(tokenJWT != null){//Se estiver recebendo um token
            var subject = tokenService.getSubject(tokenJWT);//Pega o login de usuario desse token
            var usuario = usuarioRepository.findByLogin(subject);//Acha o usuario pelo login

            var authenticationToken = new UsernamePasswordAuthenticationToken
                    (usuario, null, usuario.getAuthorities());//Pega o DTO que representa o usuário

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);//Força autenticação
        }
        filterChain.doFilter(request, response);


    }

    private String recuperarToken(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");//Tira o nome do cabeçalho para ter apenas o Token
        }
        return null;
    }
}
