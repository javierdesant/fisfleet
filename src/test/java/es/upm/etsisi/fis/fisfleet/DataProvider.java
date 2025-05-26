package es.upm.etsisi.fis.fisfleet;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import lombok.experimental.UtilityClass;
import servidor.UPMUsers;

import java.time.Instant;
import java.util.List;

@UtilityClass
public class DataProvider {

    public static List<UserRequest> userRequestValidListMock() {
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

    public static List<UserRequest> userRequestNotValidListMock() {
        return List.of(
                UserRequest.builder()
                        .username("invalid@urss.es")
                        .alias("ValidAlias")
                        .password("password123")
                        .build(),


                UserRequest.builder()
                        .username("valid@upm.es")
                        .alias("inv@lid")
                        .password("password123")
                        .build(),


                UserRequest.builder()
                        .username("valid@upm.es")
                        .alias("AliasTooLong123")
                        .password("password123")
                        .build(),


                UserRequest.builder()
                        .username("valid@upm.es")
                        .alias("admin")
                        .password("password123")
                        .build()
        );
    }

    public static UserEntity userEntityMock() {
        UserEntity user = new UserEntity("hash2ABC", "TestUser2");
        user.setRegistrationDate(Instant.now().minusSeconds(3600));
        user.setUPMUserType(UPMUsers.ALUMNO);
        return user;
    }

}
