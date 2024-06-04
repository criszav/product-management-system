package com.czavala.productmanagementsystem.config.security.filters;

import com.czavala.productmanagementsystem.exceptions.ResourceNotFoundException;
import com.czavala.productmanagementsystem.persistance.entities.JwtToken;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.persistance.repository.JwtTokenRepository;
import com.czavala.productmanagementsystem.services.UserService;
import com.czavala.productmanagementsystem.services.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final JwtTokenRepository jwtTokenRepository;

    /*
    * Verifica si la solicitid HTTP enviada por un usuario, contiene un token de autenticacion (jwt) válido
    * Extraer el username desde el token y busca al usuario que realizó la solicitud, en el sistema
    * Crea un objeto de autenticación (UsernamePasswordAuthenticationToken) con la información del usuario
    * El objeto de autenticación creado, se utiliza para autenticar la solicitud HTTP enviada por el usuario
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Extrae token JWT desde el request enviado por el user
        String jwt = jwtService.extractJwtFromRequest(request);

        // Verifica si el token extraido previamente de la solicitud, contiene texto o si es nulo
        // Si es nulo o si no contiene texto (el token), entonces retorna el control a la cadena de filtros
        if (jwt == null || !StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si la solicitud (requst) efectivamente contiene un token, entonces buscamos ese token en la DB
        Optional<JwtToken> token = jwtTokenRepository.findByToken(jwt);

        boolean isValid = validateToken(token);

        // Verifica que token obtenido previamente sea valido
        // Si el token NO es valido, entonces retorno a la cadena de filtros de seguridad
        if (!isValid) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtiene el username desde el token jwt
        String username = jwtService.extractUsername(jwt);

        // Setea el objeto Authentication dentro del SecurityContextHolder
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Username: " + username));

        // Crea una implementacion del objeto 'Authentication', y se utiliza para representar la autenticacion del usuario
        // contiene el subject del user y los permisos otorgados
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null, // clave nula, ya que no se utiliza en este caso
                user.getAuthorities() // permisos otorgados al user
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Ejecuta el resto de filtros de la cadena
        filterChain.doFilter(request, response);

    }

    private boolean validateToken(Optional<JwtToken> token) {

        // Si no encuentra token obtenido desde DB, automaticamente retorna 'false' (token NO valido)
        // Lo anterior significa que el token o no existe o no fue generado por nuestro sistema
        if (!token.isPresent()) {
            return false;
        }

        JwtToken jwtToken = token.get(); // obtiene token
        Date currentDate = new Date(System.currentTimeMillis()); // obtiene fecha actual del sistema

        // Token será valido si su fecha de expiracion es posterior a la fecha actual del sistema
        // es decir, aún no ha expirado el token (es valido)
        boolean isValid = jwtToken.isValid() && jwtToken.getExpiration().after(currentDate);

        // Si por el contrario, el token NO es valido, entonces actualizamos su estatus (el del jwt) en la DB (lo hacemos invalido)
        if (!isValid) {
            updateTokenStatus(jwtToken);
        }
        return isValid; // retorna validez del token (true o false)
    }

    private void updateTokenStatus(JwtToken jwtToken) {
        jwtToken.setValid(false); // invalida el token en DB
        jwtTokenRepository.save(jwtToken);
    }
}
