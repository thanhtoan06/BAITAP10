package vn.iotstar.bai1_jwt_basic.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginUserModel {
    private String email;
    private String password;
}
