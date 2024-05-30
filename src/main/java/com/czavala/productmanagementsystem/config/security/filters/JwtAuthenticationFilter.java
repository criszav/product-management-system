package com.czavala.productmanagementsystem.config.security.filters;

import com.czavala.productmanagementsystem.exceptions.ResourceNotFoundException;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.services.UserService;
import com.czavala.productmanagementsystem.services.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    /*
    * Verifica si la solicitid HTTP enviada por un usuario, contiene un token de autenticacion (jwt) válido
    * Extraer el username desde el token y busca al usuario que realizó la solicitud, en el sistema
    * Crea un objeto de autenticación (UsernamePasswordAuthenticationToken) con la información del usuario
    * El objeto de autenticación creado, se utiliza para autenticar la solicitud HTTP enviada por el usuario
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. obtener header que contiene el bearer token ("Authorization")

        // obtiene el header de autorizacion de la solicitud HTTP
        // este header contiene el token jwt
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 1.1 validar que header contenga texto y que comience con el prefijo 'Bearer '

        // Verifica que header contenga texto y que comience con el prefijo 'Bearer '
        // de lo contrario, retorna a la cadena de filtros, sin autenticacion
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        }

        // 2. obtener el token desde el authorization header

        // Extrae el token jwt del header de autorizacion
        // El header tiene formato 'Beaerer <token>', entonces divide la cadena en un espacio, y toma el seguno item (posicion 1), que es el token
        String jwt = authorizationHeader.split(" ")[1];

        // 3. obtener el subject (username) desde el jwt

        // Obtiene el username desde el token jwt
        String username = jwtService.extractUsername(jwt);

        // 4. setear el objeto Authentication dentro del SecurityContextHolder
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found. Username: " + username));

        // Crea una implementacion del objeto 'Authentication', y se utiliza para representar la autenticacion del usuario
        // contiene el subject del user y los permisos otorgados
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null, // clave nula, ya que no se utiliza en este caso
                user.getAuthorities() // permisos otorgados al user
        );

        // 5. ejecuta el resto de filtros de la cadena
        filterChain.doFilter(request, response);

    }
}
