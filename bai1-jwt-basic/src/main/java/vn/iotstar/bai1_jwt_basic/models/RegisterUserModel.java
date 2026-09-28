package vn.iotstar.bai1_jwt_basic.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterUserModel {
    private String fullName;
    private String email;
    private String password;
    private String images;
}
