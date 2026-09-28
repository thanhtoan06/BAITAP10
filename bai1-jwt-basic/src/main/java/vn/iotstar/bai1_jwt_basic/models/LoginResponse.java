package vn.iotstar.bai1_jwt_basic.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String token;
    private long expiresIn;
}
