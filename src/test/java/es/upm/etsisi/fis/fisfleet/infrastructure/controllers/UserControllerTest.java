package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.TokenService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.UserService;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.impl.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateUser() throws Exception {
        UserRequest request = UserRequest.builder()
                .username("test@alumnos.upm.es")
                .alias("TestUser")
                .password("securePass123")
                .build();

        UserEntity createdUser = new UserEntity("hashed", "TestUser");
        createdUser.setId(1L);
        createdUser.setRegistrationDate(Instant.now());
        createdUser.setUPMUserType(servidor.UPMUsers.ALUMNO);

        Mockito.when(userService.create(any(UserRequest.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.alias").value("TestUser"));
    }

    @Test
    void testGetUserById() throws Exception {
        Long userId = 1L;

        UserEntity user = new UserEntity("hash123", "User1");
        user.setId(userId);
        user.setRegistrationDate(Instant.now());
        user.setUPMUserType(servidor.UPMUsers.ALUMNO);

        Mockito.when(userService.read(userId)).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("User1"));
    }

    @Test
    void testUpdateUser() throws Exception {
        Long userId = 2L;

        UserRequest request = UserRequest.builder()
                .username("new@alumnos.upm.es")
                .alias("UpdatedAlias")
                .password("newPass")
                .build();

        UserEntity updated = new UserEntity("hashUpdated", "UpdatedAlias");
        updated.setId(userId);
        updated.setRegistrationDate(Instant.now());
        updated.setUPMUserType(servidor.UPMUsers.ALUMNO);

        Mockito.when(userService.update(eq(request), eq(userId))).thenReturn(updated);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("UpdatedAlias"));
    }

    @Test
    void testDeleteUser() throws Exception {
        Long userId = 3L;

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).delete(userId);
    }
}