package vn.iotstar.bai1_jwt_basic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iotstar.bai1_jwt_basic.entity.User;
import vn.iotstar.bai1_jwt_basic.repository.UserRepository;
import java.util.List;

@Service @RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> allUsers() { return userRepository.findAll(); }
}
