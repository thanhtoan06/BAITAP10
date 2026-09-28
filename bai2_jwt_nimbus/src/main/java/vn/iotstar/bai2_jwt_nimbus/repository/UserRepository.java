package vn.iotstar.bai2_jwt_nimbus.repository;

import vn.iotstar.bai2_jwt_nimbus.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
