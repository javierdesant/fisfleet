package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.DataProvider;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.exceptions.RegistrationException;
import es.upm.etsisi.fis.fisfleet.utils.RolePermission;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final UserRequest alumnoRequest = DataProvider.userRequestValidListMock().get(0);
    private final UserRequest pasRequest = DataProvider.userRequestValidListMock().get(1);
    private final UserEntity mockUser = DataProvider.userEntityMock();
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private MockedStatic<ObtencionDeRol> roleMock;
    private MockedStatic<es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator> ldapMock;

    @BeforeEach
    void setup() {
        roleMock = mockStatic(ObtencionDeRol.class);
        ldapMock = mockStatic(es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator.class);
    }

    @AfterEach
    void tearDown() {
        roleMock.close();
        ldapMock.close();
    }

    private void mockAlumno(String username) {
        roleMock.when(() -> ObtencionDeRol.get_UPM_AccountRol(username)).thenReturn(UPMUsers.ALUMNO);
    }

    private void mockPas(String username) {
        roleMock.when(() -> ObtencionDeRol.get_UPM_AccountRol(username)).thenReturn(UPMUsers.PAS);
    }

    private void mockLdap(String username, String hash) {
        ldapMock.when(() -> es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator.authenticate(username)).thenReturn(hash);
    }

    // CREATE
    @Test
    void create_shouldSucceedWhenUserIsAlumno() {
        mockAlumno(alumnoRequest.getUsername());
        mockLdap(alumnoRequest.getUsername(), "encryptedHash");

        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UserEntity result = userService.create(alumnoRequest);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("TestUser1", result.getAlias()),
                () -> assertEquals("encryptedHash", result.getUsernameHash()),
                () -> assertEquals(UPMUsers.ALUMNO, result.getUPMUserType()),
                () -> assertTrue(result.getAuthorities().contains(
                                new SimpleGrantedAuthority(RolePermission.REGISTER.name())),
                        "User should have REGISTER permission")
        );
    }

    @Test
    void create_shouldThrowExceptionWhenNotAlumno() {
        mockPas(pasRequest.getUsername());
        RegistrationException ex = assertThrows(RegistrationException.class, () -> userService.create(pasRequest));
        assertEquals("Registration is restricted to students.", ex.getMessage());
    }

    // READ
    @Test
    void read_shouldReturnUserWhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        UserEntity result = userService.read(1L);
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(mockUser.getAlias(), result.getAlias())
        );
    }

    @Test
    void read_shouldThrowExceptionWhenNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.read(999L));
    }

    // UPDATE
    @Test
    void update_shouldModifyAliasAndUserType() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        mockAlumno(alumnoRequest.getUsername());

        UserEntity updated = userService.update(alumnoRequest, 2L);

        assertAll(
                () -> assertNotNull(updated),
                () -> assertEquals("TestUser1", updated.getAlias()),
                () -> assertEquals(UPMUsers.ALUMNO, updated.getUPMUserType())
        );
    }

    @Test
    void update_shouldThrowExceptionWhenUserIsNotAlumno() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(mockUser));
        mockPas(pasRequest.getUsername());

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> userService.update(pasRequest, 2L));

        assertEquals("Registration is restricted to students.", ex.getMessage());
    }

    // DELETE
    @Test
    void delete_shouldRemoveExistingUser() {
        when(userRepository.findById(3L)).thenReturn(Optional.of(mockUser));
        userService.delete(3L);
        verify(userRepository).delete(mockUser);
    }

    @Test
    void delete_shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.delete(404L));
    }
}
