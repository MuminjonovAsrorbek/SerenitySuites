package uz.dev.serenitysuites.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.serenitysuites.dto.request.LoginDTO;
import uz.dev.serenitysuites.dto.response.TokenDTO;
import uz.dev.serenitysuites.service.security.AuthService;

/**
 * Created by: asrorbek
 * DateTime: 6/21/25 13:34
 **/

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO) {

        return authService.getToken(loginDTO);

    }

}
