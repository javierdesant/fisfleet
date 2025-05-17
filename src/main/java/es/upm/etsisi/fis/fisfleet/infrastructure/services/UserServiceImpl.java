package es.upm.etsisi.fis.fisfleet.infrastructure.services;

import es.upm.etsisi.fis.fisfleet.api.dto.requests.UserRequest;
import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserEntity create(UserRequest request) {
        return null;    // FIXME
    }

    @Override
    public UserEntity read(String s) {
        return null;    // FIXME
    }

    @Override
    public UserEntity update(UserRequest request, String s) {
        return null;    // FIXME
    }

    @Override
    public void delete(String s) {
        // TODO
    }
}
