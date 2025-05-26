package es.upm.etsisi.fis.fisfleet;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.api.dto.responses.UserResponse;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import lombok.experimental.UtilityClass;
import servidor.UPMUsers;

import java.time.Instant;
import java.util.List;

@UtilityClass
public class DataProvider {

    public static List<UserRequest> userRequestListMock() {
        return List.of(
                UserRequest.builder()
                        .username("test1.user@alumnos.upm.es")
                        .alias("TestUser1")
                        .password("Password123!")
                        .build(),
                UserRequest.builder()
                        .username("test2.user@pas.upm.es")
                        .alias("TestUser2")
                        .password("Password456!")
                        .build(),
                UserRequest.builder()
                        .username("test3.user@pdi.upm.es")
                        .alias("TestUser3")
                        .password("Password789!")
                        .build(),
                UserRequest.builder()
                        .username("test4.user@upm.es")
                        .alias("TestUser4")
                        .password("PasswordNull!")
                        .build()
        );
    }

    public static List<UserResponse> userResponseListMock() {
        return List.of(
                UserResponse.builder()
                        .id(1L)
                        .usernameHash("hash1XYZ")
                        .alias("TestUser1")
                        .registrationDate(Instant.now())
                        .build(),
                UserResponse.builder()
                        .id(2L)
                        .usernameHash("hash2ABC")
                        .alias("TestUser2")
                        .registrationDate(Instant.now().minusSeconds(3600))
                        .build(),
                UserResponse.builder()
                        .id(3L)
                        .usernameHash("hash3DEF")
                        .alias("TestUser3")
                        .registrationDate(Instant.parse("2025-05-21T00:00:01Z"))
                        .build()
        );
    }

    public static List<UserEntity> userEntityListMock() {
        UserEntity user1 = new UserEntity("hash1XYZ", "TestUser1");
        user1.setRegistrationDate(Instant.now());
        user1.setUPMUserType(UPMUsers.PAS);

        UserEntity user2 = new UserEntity("hash2ABC", "TestUser2");
        user2.setRegistrationDate(Instant.now().minusSeconds(3600));

        UserEntity user3 = new UserEntity("hash3DEF", "TestUser3");
        user3.setRegistrationDate(Instant.parse("2025-05-21T03:00:00Z"));
        user3.setUPMUserType(UPMUsers.PDI);

        return List.of(user1, user2, user3);
    }

    public static UserEntity userEntityMock() {
        UserEntity user = new UserEntity("hash2ABC", "TestUser2");
        user.setRegistrationDate(Instant.now().minusSeconds(3600));
        user.setUPMUserType(UPMUsers.ALUMNO);
        return user;
    }

}
