package es.upm.etsisi.fis.fisfleet.infrastructure.config.security;

import es.upm.etsisi.fis.fisfleet.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    public boolean isSelf(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        authentication.getPrincipal();
        if (principal instanceof UserEntity user) {
            return user.getId().equals(id);
        }
        return false;
    }
}
