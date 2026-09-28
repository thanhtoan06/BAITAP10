package vn.iotstar.bai2_jwt_nimbus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iotstar.bai2_jwt_nimbus.entity.User;
import vn.iotstar.bai2_jwt_nimbus.models.LoginUserModel;
import vn.iotstar.bai2_jwt_nimbus.models.RegisterUserModel;
import vn.iotstar.bai2_jwt_nimbus.repository.UserRepository;

@Service @RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signup(RegisterUserModel input) {
        String email = input.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email))
            throw new IllegalArgumentException("Email đã được sử dụng");
        User user = User.builder().fullName(input.getFullName()).email(email)
                .password(passwordEncoder.encode(input.getPassword())).images(input.getImages()).build();
        return userRepository.save(user);
    }

    public User authenticate(LoginUserModel input) {
        String email = input.getEmail().trim().toLowerCase();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, input.getPassword()));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tài khoản"));
    }
}
