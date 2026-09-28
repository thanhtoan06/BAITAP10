package vn.iotstar.bai2_jwt_nimbus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.bai2_jwt_nimbus.entity.User;
import vn.iotstar.bai2_jwt_nimbus.models.*;
import vn.iotstar.bai2_jwt_nimbus.services.AuthenticationService;
import vn.iotstar.bai2_jwt_nimbus.services.JwtService;
import java.util.Map;

@RestController @RequestMapping("/auth") @RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/signup") @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> signup(@RequestBody RegisterUserModel input) {
        User user = authenticationService.signup(input);
        return Map.of("id", user.getId(), "fullName", user.getFullName(), "email", user.getEmail());
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginUserModel input) {
        User user = authenticationService.authenticate(input);
        return new LoginResponse(jwtService.generateToken(user), jwtService.getExpirationTime());
    }
}
