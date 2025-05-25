package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.DataProvider;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    void testReadUserSuccess() {
        Long userId = 1L;
        UserEntity mockUser = DataProvider.userEntityMock();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        UserEntity result = userService.read(userId);

        assertNotNull(result);
        assertEquals(mockUser.getAlias(), result.getAlias());
    }

    @Test
    void testReadUserNotFound() {
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.read(userId));
    }

    @Test
    void testUpdateUserSuccess() {
        Long userId = 2L;
        UserRequest request = DataProvider.userRequestListMock().get(0);
        UserEntity existingUser = DataProvider.userEntityMock();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserEntity updated = userService.update(request, userId);

        assertNotNull(updated);
        assertEquals(request.getAlias(), updated.getAlias());
        System.out.println(updated.getUPMUserType()+"------------"+DataProvider.userRequestListMock().get(2).getAlias());
        assertEquals(UPMUsers.ALUMNO, updated.getUPMUserType());
    }

    @Test
    void testDeleteUserSuccess() {
        Long userId = 3L;
        UserEntity existingUser = DataProvider.userEntityMock();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.delete(userId);

        verify(userRepository, times(1)).delete(existingUser);

    }

    @Test
    void testDeleteUserNotFound() {
        Long userId = 404L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(userId));
    }
}