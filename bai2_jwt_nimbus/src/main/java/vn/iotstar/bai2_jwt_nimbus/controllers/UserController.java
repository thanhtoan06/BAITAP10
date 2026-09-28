package vn.iotstar.bai2_jwt_nimbus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.bai2_jwt_nimbus.entity.User;
import vn.iotstar.bai2_jwt_nimbus.services.UserService;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/users") @RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public Map<String, Object> authenticatedUser(@AuthenticationPrincipal User user) {
        return Map.of("id", user.getId(), "fullName", user.getFullName(), "email", user.getEmail(),
                "images", user.getImages() == null ? "" : user.getImages());
    }
    @GetMapping("/")
    public List<Map<String, Object>> allUsers() {
        return userService.allUsers().stream().map(user -> Map.<String, Object>of("id", user.getId(),
                "fullName", user.getFullName(), "email", user.getEmail(),
                "images", user.getImages() == null ? "" : user.getImages())).toList();
    }
}
