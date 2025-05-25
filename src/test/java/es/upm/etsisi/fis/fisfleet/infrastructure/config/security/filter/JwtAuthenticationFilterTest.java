package es.upm.etsisi.fis.fisfleet.infrastructure.config.security.filter;

import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtService jwtService;
    private UserRepository userRepository;
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userRepository = mock(UserRepository.class);
        filter = new JwtAuthenticationFilter(jwtService, userRepository);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_WithValidJwt_SetsAuthentication() throws Exception {
        String jwt = "valid.jwt.token";
        String usernameHash = "userHash";
        UserEntity userEntity = mock(UserEntity.class);

        when(userEntity.getUsername()).thenReturn(usernameHash);

        Collection<GrantedAuthority> auths =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(auths)
                .when(userEntity)
                .getAuthorities();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(usernameHash);
        when(userRepository.findByUsernameHash(usernameHash))
                .thenReturn(Optional.of(userEntity));

        filter.doFilterInternal(request, response, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth, "Authentication should have been established");
        // Ahora principal == el String usernameHash…
        assertEquals(usernameHash, auth.getPrincipal());
        // …y getName() también
        assertEquals(usernameHash, auth.getName());

        verify(chain).doFilter(request, response);
    }


    @Test
    void testDoFilterInternal_WithInvalidHeader_DoesNotAuthenticate() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_UserNotFound_ThrowsException() {
        String jwt = "jwt";
        String usernameHash = "userHash";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(usernameHash);
        when(userRepository.findByUsernameHash(usernameHash)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> filter.doFilterInternal(request, response, chain));
    }

    @Test
    void testDoFilterInternal_WithMalformedHeader_DoesNotAuthenticate() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("InvalidHeaderValue");

        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithEmptyBearerToken_ThrowsException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer ");

        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> filter.doFilterInternal(request, response, chain));
    }

    @Test
    void testDoFilterInternal_WithNullUserDetails_ThrowsException() {
        String jwt = "jwt";
        String usernameHash = "userHash";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(usernameHash);
        when(userRepository.findByUsernameHash(usernameHash)).thenReturn(null);

        NullPointerException thrown = assertThrows(NullPointerException.class,
                () -> filter.doFilterInternal(request, response, chain));
        assertNotNull(thrown);
    }

    @Test
    void testDoFilterInternal_WithAlreadyAuthenticatedContext_OverridesExistingAuthentication() throws Exception {
        String jwt = "valid.jwt.token";
        String usernameHash = "userHash";

        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getUsername()).thenReturn(usernameHash);
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        doReturn(authorities).when(userEntity).getAuthorities();

        // Set up auth to be replaced
        Authentication existingAuth = new UsernamePasswordAuthenticationToken("existingUser", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(existingAuth);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(usernameHash);
        when(userRepository.findByUsernameHash(usernameHash)).thenReturn(Optional.of(userEntity));

        filter.doFilterInternal(request, response, chain);

        Authentication updatedAuth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(updatedAuth, "Authentication should exist after the filter");

        assertNotSame(existingAuth, updatedAuth, "Authentication instance should have been replaced");
        assertNotEquals(existingAuth.getPrincipal(), updatedAuth.getPrincipal(), "Principal should be different");

        assertEquals(usernameHash, updatedAuth.getPrincipal(), "Principal should be the new usernameHash");
        verify(chain).doFilter(request, response);
    }

}
