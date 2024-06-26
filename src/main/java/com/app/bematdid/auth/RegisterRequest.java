package com.app.bematdid.auth;


import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    String username;
    String password;
    Funcionario funcionario;
    Role role;
}
