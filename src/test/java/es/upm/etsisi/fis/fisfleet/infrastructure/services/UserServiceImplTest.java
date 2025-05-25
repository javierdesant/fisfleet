package es.upm.etsisi.fis.fisfleet.infrastructure.services;
/*
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockStatic(ObtencionDeRol.class);
        mockStatic(LDAPAuthenticator.class);
    }

    @Test
    void testCreateUserSuccess() {
        UserRequest request = UserRequest.builder()
                .username("student@alumnos.upm.es")
                .alias("student123")
                .password("1234")
                .build();

        when(ObtencionDeRol.get_UPM_AccountRol(request.getUsername()))
                .thenReturn(UPMUsers.ALUMNO);

        when(LDAPAuthenticator.authenticate(request.getUsername()))
                .thenReturn("hashedUsername");

        UserEntity savedUser = UserEntity.builder()
                .id(1L)
                .usernameHash("hashedUsername")
                .alias("student123")
                .registrationDate(Instant.now())
                .UPMUserType(UPMUsers.ALUMNO)
                .build();

        when(userRepository.save(any())).thenReturn(savedUser);

        UserEntity result = userService.create(request);

        assertNotNull(result);
        assertEquals("hashedUsername", result.getUsernameHash());
        verify(userRepository).save(any());
    }

    @Test
    void testCreateUserFailsIfNotStudent() {
        UserRequest request = UserRequest.builder()
                .username("admin@upm.es")
                .alias("admin")
                .password("1234")
                .build();

        when(ObtencionDeRol.get_UPM_AccountRol(request.getUsername()))
                .thenReturn(UPMUsers.PDI);

        assertThrows(IllegalArgumentException.class, () -> userService.create(request));
    }

    @Test
    void testReadUserFound() {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .alias("readuser")
                .usernameHash("hash")
                .UPMUserType(UPMUsers.ALUMNO)
                .registrationDate(Instant.now())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.read(1L);

        assertNotNull(result);
        assertEquals("readuser", result.getAlias());
    }

    @Test
    void testReadUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.read(99L));
    }

    @Test
    void testUpdateUser() {
        Long id = 10L;
        UserRequest request = UserRequest.builder()
                .username("update@alumnos.upm.es")
                .alias("newAlias")
                .password("pass")
                .build();

        UserEntity existing = UserEntity.builder()
                .id(id)
                .alias("oldAlias")
                .usernameHash("hash")
                .registrationDate(Instant.now())
                .UPMUserType(UPMUsers.ALUMNO)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(ObtencionDeRol.get_UPM_AccountRol(request.getUsername()))
                .thenReturn(UPMUsers.ALUMNO);
        when(userRepository.save(any())).thenReturn(existing);

        UserEntity updated = userService.update(request, id);

        assertEquals("newAlias", updated.getAlias());
        verify(userRepository).save(existing);
    }

    @Test
    void testDeleteUser() {
        Long id = 20L;
        UserEntity user = new UserEntity();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.delete(id);

        verify(userRepository).delete(user);
    }
}*/

import es.upm.etsisi.fis.fisfleet.DataProvider;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Test
    public void readTest(){
        Long id = 2L;
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserServiceImpl(userRepository);

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(DataProvider.userEntityMock()));
        UserEntity user = userService.read(id);

        Assertions.assertNotNull(user);
    }
}
