package vn.iotstar.bai1_jwt_basic.repository;

import vn.iotstar.bai1_jwt_basic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
