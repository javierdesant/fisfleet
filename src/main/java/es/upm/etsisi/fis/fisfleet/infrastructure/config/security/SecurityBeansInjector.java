package es.upm.etsisi.fis.fisfleet.infrastructure.config.security;


import es.upm.etsisi.fis.fisfleet.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class SecurityBeansInjector {

    private final UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setPasswordEncoder(this.passwordEncoder());
        daoProvider.setUserDetailsService(this.userDetailsService());
        return daoProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this::loadUserByEmail;
    }

    private UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return null; // FIXME: implement... userRepository.findByEmail(email)
//                                          .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }
}
