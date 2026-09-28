package vn.iotstar.bai2_jwt_nimbus.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterUserModel {
    private String fullName;
    private String email;
    private String password;
    private String images;
}
