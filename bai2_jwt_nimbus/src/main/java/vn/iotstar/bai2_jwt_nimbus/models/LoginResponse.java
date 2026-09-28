package vn.iotstar.bai2_jwt_nimbus.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String token;
    private long expiresIn;
}
