package vn.iotstar.bai2_jwt_nimbus.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginUserModel {
    private String email;
    private String password;
}
