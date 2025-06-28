package uz.dev.serenitysuites.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.serenitysuites.dto.request.LoginDTO;
import uz.dev.serenitysuites.dto.response.TokenDTO;
import uz.dev.serenitysuites.entity.User;
import uz.dev.serenitysuites.exceptions.PasswordIncorrectException;
import uz.dev.serenitysuites.repository.UserRepository;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 15:14
 **/

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmailOrThrow(email);

    }

    public TokenDTO getToken(LoginDTO loginDTO) {

        User user = loadUserByUsername(loginDTO.getEmail());

        String encodedPassword = user.getPassword();

        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), encodedPassword);

        if (!matches) {

            throw new PasswordIncorrectException("Password incorrect", HttpStatus.BAD_REQUEST);

        }

        String token = jwtService.generateToken(loginDTO.getEmail());

        return new TokenDTO(token);
    }
}
