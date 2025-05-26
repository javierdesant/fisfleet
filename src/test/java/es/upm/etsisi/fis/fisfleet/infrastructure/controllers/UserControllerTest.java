package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.DataProvider;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
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

    @TestConfiguration
    static class MockConfig {
        @Bean
        @Primary
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    // POST /api/users
    @WithMockUser
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

    @WithMockUser
    @Test
    void testCreateUser_validationError() throws Exception {
        UserRequest invalidRequest = UserRequest.builder()
                .username("invalid@upm.es")
                .alias("") // Alias vacío
                .password("password123")
                .build();

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // GET /api/users/{id}
    @WithMockUser
    @Test
    void testGetUserById_success() throws Exception {
        Long userId = 1L;
        UserEntity user = DataProvider.userEntityMock();
        user.setId(userId);

        Mockito.when(userService.read(userId)).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usernameHash").value(user.getUsernameHash()))
                .andExpect(jsonPath("$.alias").value(user.getAlias()));
    }

    @WithMockUser
    @Test
    void testGetUserById_notFound() throws Exception {
        Long userId = 999L;

        Mockito.when(userService.read(userId)).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    // PUT /api/users/{id}
    @WithMockUser
    @Test
    void testUpdateUser_success() throws Exception {
        Long userId = 1L;
        // Usa un usuario que pase validación
        UserRequest request = DataProvider.userRequestListMock().get(0);  // test1.user@alumnos.upm.es
        UserEntity updatedUser = DataProvider.userEntityMock();
        updatedUser.setId(userId);
        updatedUser.setAlias("UpdatedAlias");

        Mockito.when(userService.update(any(UserRequest.class), eq(userId))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usernameHash").value(updatedUser.getUsernameHash()))
                .andExpect(jsonPath("$.alias").value("UpdatedAlias"));
    }

    @WithMockUser
    @Test
    void testUpdateUser_notFound() throws Exception {
        Long userId = 999L;
        // Usa también un request válido
        UserRequest request = DataProvider.userRequestListMock().get(0);  // test1.user@alumnos.upm.es

        Mockito.when(userService.update(any(UserRequest.class), eq(userId)))
                .thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }


    // DELETE /api/users/{id}
    @WithMockUser
    @Test
    void testDeleteUser_success() throws Exception {
        Long userId = 1L;

        Mockito.doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @WithMockUser
    @Test
    void testDeleteUser_notFound() throws Exception {
        Long userId = 999L;

        Mockito.doThrow(new EntityNotFoundException("User not found")).when(userService).delete(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }
}
