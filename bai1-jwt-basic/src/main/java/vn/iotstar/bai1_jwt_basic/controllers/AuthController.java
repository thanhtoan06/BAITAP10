package vn.iotstar.bai1_jwt_basic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping({"/", "/login"}) public String loginPage() { return "login"; }
    @GetMapping("/user/profile") public String profilePage() { return "profile"; }
}
