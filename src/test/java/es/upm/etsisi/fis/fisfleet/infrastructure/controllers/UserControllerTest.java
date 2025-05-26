package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.DataProvider;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.UserSecurity;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurity userSecurity;

    @TestConfiguration
    static class MockConfig {
        @Bean
        @Primary
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }

        @Bean
        @Primary
        public UserSecurity userSecurity() {
            return Mockito.mock(UserSecurity.class);
        }
    }

    // POST /api/users - sin seguridad
    @Test
    void testCreateUser_success() throws Exception {
        UserRequest request = DataProvider.userRequestListMock().get(0);
        UserEntity createdUser = DataProvider.userEntityMock();
        createdUser.setId(1L);

        Mockito.when(userService.create(any(UserRequest.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usernameHash").value(createdUser.getUsernameHash()))
                .andExpect(jsonPath("$.alias").value(createdUser.getAlias()));
    }

    @Test
    void testCreateUser_validationError() throws Exception {
        UserRequest invalidRequest = UserRequest.builder()
                .username("invalid@upm.es")
                .alias("")
                .password("password123")
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // GET /api/users/{id}
    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testGetUserById_withAuthority_success() throws Exception {
        Long userId = 1L;
        UserEntity user = DataProvider.userEntityMock();
        user.setId(userId);

        Mockito.when(userService.read(userId)).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usernameHash").value(user.getUsernameHash()))
                .andExpect(jsonPath("$.alias").value(user.getAlias()));
    }

    @WithMockUser(username = "ownerHash")
    @Test
    void testGetUserById_asSelf_success() throws Exception {
        Long userId = 1L;
        UserEntity user = DataProvider.userEntityMock();
        user.setId(userId);
        user.setUsernameHash("ownerHash");

        Mockito.when(userService.read(userId)).thenReturn(user);
        Mockito.when(userSecurity.isSelf(userId)).thenReturn(true);

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usernameHash").value(user.getUsernameHash()))
                .andExpect(jsonPath("$.alias").value(user.getAlias()));
    }

    @WithMockUser(username = "intruder")
    @Test
    void testGetUserById_forbidden() throws Exception {
        Long userId = 1L;

        Mockito.when(userSecurity.isSelf(userId)).thenReturn(false);

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testGetUserById_notFound() throws Exception {
        Long userId = 999L;

        Mockito.when(userService.read(userId)).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    // PUT /api/users/{id}
    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testUpdateUser_withAuthority_success() throws Exception {
        Long userId = 1L;
        UserRequest request = DataProvider.userRequestListMock().get(0);
        UserEntity updatedUser = DataProvider.userEntityMock();
        updatedUser.setId(userId);
        updatedUser.setAlias("UpdatedAlias");

        Mockito.when(userService.update(any(UserRequest.class), eq(userId))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("UpdatedAlias"));
    }

    @WithMockUser(username = "ownerHash")
    @Test
    void testUpdateUser_asSelf_success() throws Exception {
        Long userId = 1L;
        UserRequest request = DataProvider.userRequestListMock().get(0);
        UserEntity updatedUser = DataProvider.userEntityMock();
        updatedUser.setId(userId);
        updatedUser.setAlias("UpdatedAlias");

        Mockito.when(userSecurity.isSelf(userId)).thenReturn(true);
        Mockito.when(userService.update(any(UserRequest.class), eq(userId))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("UpdatedAlias"));
    }

    @WithMockUser(username = "intruder")
    @Test
    void testUpdateUser_forbidden() throws Exception {
        Long userId = 1L;
        UserRequest request = DataProvider.userRequestListMock().get(0);

        Mockito.when(userSecurity.isSelf(userId)).thenReturn(false);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testUpdateUser_notFound() throws Exception {
        Long userId = 999L;
        UserRequest request = DataProvider.userRequestListMock().get(0);

        Mockito.when(userService.update(any(UserRequest.class), eq(userId)))
                .thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    // DELETE /api/users/{id}
    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testDeleteUser_withAuthority_success() throws Exception {
        Long userId = 1L;

        Mockito.doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "ownerHash")
    @Test
    void testDeleteUser_asSelf_success() throws Exception {
        Long userId = 1L;

        Mockito.when(userSecurity.isSelf(userId)).thenReturn(true);
        Mockito.doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "intruder")
    @Test
    void testDeleteUser_forbidden() throws Exception {
        Long userId = 1L;

        Mockito.when(userSecurity.isSelf(userId)).thenReturn(false);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testDeleteUser_notFound() throws Exception {
        Long userId = 999L;

        Mockito.doThrow(new EntityNotFoundException("User not found")).when(userService).delete(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }
}
