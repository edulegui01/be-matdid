package com.app.bematdid.auth;


import com.app.bematdid.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    String token;
    Long idFuncionario;
    String nombreFuncionario;
    String apellidoFuncionario;
    Role role;
}
