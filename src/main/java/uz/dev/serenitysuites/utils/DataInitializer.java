package uz.dev.serenitysuites.utils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.dev.serenitysuites.entity.User;
import uz.dev.serenitysuites.enums.Role;
import uz.dev.serenitysuites.repository.UserRepository;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 16:57
 **/

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        if (!userRepository.existsByEmail("admin@gmail.com")) {

            User admin = new User();
            admin.setFullName("Administrator");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin")); // 🔐 use strong default
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }
}
