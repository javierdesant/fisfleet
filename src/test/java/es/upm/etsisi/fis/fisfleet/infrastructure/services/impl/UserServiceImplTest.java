package es.upm.etsisi.fis.fisfleet.infrastructure.services.impl;

import es.upm.etsisi.fis.fisfleet.DataProvider;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.exceptions.RegistrationException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import servidor.ObtencionDeRol;
import servidor.UPMUsers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    // CREATE

    @Test
    void create_shouldSucceedWhenUserIsAlumno() {
        final UserRequest request = DataProvider.userRequestListMock().get(0); // test1.user@alumnos.upm.es

        try (MockedStatic<ObtencionDeRol> roleMock = mockStatic(ObtencionDeRol.class);
             MockedStatic<es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator> ldapMock = mockStatic(es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator.class)) {

            roleMock.when(() -> ObtencionDeRol.get_UPM_AccountRol(request.getUsername()))
                    .thenReturn(UPMUsers.ALUMNO);

            ldapMock.when(() -> es.upm.etsisi.fis.fisfleet.infrastructure.config.security.LDAPAuthenticator.authenticate(request.getUsername()))
                    .thenReturn("encryptedHash");

            when(userRepository.save(any(UserEntity.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            final UserEntity result = userService.create(request);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertEquals("TestUser1", result.getAlias()),
                    () -> assertEquals("encryptedHash", result.getUsernameHash()),
                    () -> assertEquals(UPMUsers.ALUMNO, result.getUPMUserType())
            );
        }
    }

    @Test
    void create_shouldThrowExceptionWhenNotAlumno() {
        final UserRequest request = DataProvider.userRequestListMock().get(1); // test2.user@pas.upm.es

        try (MockedStatic<ObtencionDeRol> roleMock = mockStatic(ObtencionDeRol.class)) {
            roleMock.when(() -> ObtencionDeRol.get_UPM_AccountRol(request.getUsername()))
                    .thenReturn(UPMUsers.PAS);

            final RegistrationException ex = assertThrows(
                    RegistrationException.class,
                    () -> userService.create(request)
            );

            assertEquals("Registration is restricted to students.", ex.getMessage());
        }
    }

    // READ

    @Test
    void read_shouldReturnUserWhenExists() {
        final Long userId = 1L;
        final UserEntity mockUser = DataProvider.userEntityMock();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        final UserEntity result = userService.read(userId);

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
        final Long userId = 2L;
        final UserRequest request = DataProvider.userRequestListMock().get(0); // alumno
        final UserEntity existingUser = DataProvider.userEntityMock();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        try (MockedStatic<ObtencionDeRol> roleMock = mockStatic(ObtencionDeRol.class)) {
            roleMock.when(() -> ObtencionDeRol.get_UPM_AccountRol(request.getUsername()))
                    .thenReturn(UPMUsers.ALUMNO);

            final UserEntity updated = userService.update(request, userId);

            assertAll(
                    () -> assertNotNull(updated),
                    () -> assertEquals("TestUser1", updated.getAlias()),
                    () -> assertEquals(UPMUsers.ALUMNO, updated.getUPMUserType())
            );
        }
    }
    @Test
    void update_shouldThrowExceptionWhenUserNotFound() {
        final Long userId = 999L;
        final UserRequest request = DataProvider.userRequestListMock().get(0);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> this.userService.update(request, userId));
    }

    // DELETE

    @Test
    void delete_shouldRemoveExistingUser() {
        final Long userId = 3L;
        final UserEntity existingUser = DataProvider.userEntityMock();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.delete(userId);

        verify(userRepository).delete(existingUser);
    }

    @Test
    void delete_shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(404L));
    }
}
