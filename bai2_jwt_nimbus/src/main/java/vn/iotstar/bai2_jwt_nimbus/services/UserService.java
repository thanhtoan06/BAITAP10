package vn.iotstar.bai2_jwt_nimbus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.iotstar.bai2_jwt_nimbus.entity.User;
import vn.iotstar.bai2_jwt_nimbus.repository.UserRepository;
import java.util.List;

@Service @RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> allUsers() { return userRepository.findAll(); }
}
