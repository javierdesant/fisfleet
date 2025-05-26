package es.upm.etsisi.fis.fisfleet.infrastructure.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.upm.etsisi.fis.fisfleet.DataProvider;
import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.UserSecurity;
import es.upm.etsisi.fis.fisfleet.infrastructure.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
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

    private UserRequest validRequest;
    private UserEntity validUser;

    @BeforeEach
    void init() {
        validRequest = DataProvider.userRequestListMock().get(0);
        validUser = DataProvider.userEntityMock();
        validUser.setId(1L);
    }

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

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

    @Test
    void testCreateUser_success() throws Exception {
        Mockito.when(userService.create(any(UserRequest.class))).thenReturn(validUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usernameHash").value(validUser.getUsernameHash()))
                .andExpect(jsonPath("$.alias").value(validUser.getAlias()));
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
                        .content(toJson(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testGetUserById_withAuthority_success() throws Exception {
        Mockito.when(userService.read(1L)).thenReturn(validUser);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usernameHash").value(validUser.getUsernameHash()))
                .andExpect(jsonPath("$.alias").value(validUser.getAlias()));
    }

    @WithMockUser(username = "ownerHash")
    @Test
    void testGetUserById_asSelf_success() throws Exception {
        validUser.setUsernameHash("ownerHash");
        Mockito.when(userService.read(1L)).thenReturn(validUser);
        Mockito.when(userSecurity.isSelf(1L)).thenReturn(true);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usernameHash").value("ownerHash"))
                .andExpect(jsonPath("$.alias").value(validUser.getAlias()));
    }

    @WithMockUser(username = "intruder")
    @Test
    void testGetUserById_forbidden() throws Exception {
        Mockito.when(userSecurity.isSelf(1L)).thenReturn(false);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testGetUserById_notFound() throws Exception {
        Mockito.when(userService.read(999L)).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testUpdateUser_withAuthority_success() throws Exception {
        validUser.setAlias("UpdatedAlias");
        Mockito.when(userService.update(any(UserRequest.class), eq(1L))).thenReturn(validUser);

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("UpdatedAlias"));
    }

    @WithMockUser(username = "ownerHash")
    @Test
    void testUpdateUser_asSelf_success() throws Exception {
        validUser.setAlias("UpdatedAlias");
        Mockito.when(userSecurity.isSelf(1L)).thenReturn(true);
        Mockito.when(userService.update(any(UserRequest.class), eq(1L))).thenReturn(validUser);

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("UpdatedAlias"));
    }

    @WithMockUser(username = "intruder")
    @Test
    void testUpdateUser_forbidden() throws Exception {
        Mockito.when(userSecurity.isSelf(1L)).thenReturn(false);

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(validRequest)))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testUpdateUser_notFound() throws Exception {
        Mockito.when(userService.update(any(UserRequest.class), eq(999L)))
                .thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(put("/api/users/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(validRequest)))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testDeleteUser_withAuthority_success() throws Exception {
        Mockito.doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "ownerHash")
    @Test
    void testDeleteUser_asSelf_success() throws Exception {
        Mockito.when(userSecurity.isSelf(1L)).thenReturn(true);
        Mockito.doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "intruder")
    @Test
    void testDeleteUser_forbidden() throws Exception {
        Mockito.when(userSecurity.isSelf(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "MANAGE_USERS")
    @Test
    void testDeleteUser_notFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("User not found")).when(userService).delete(999L);

        mockMvc.perform(delete("/api/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
