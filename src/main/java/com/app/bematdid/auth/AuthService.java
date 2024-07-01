package com.app.bematdid.auth;


import com.app.bematdid.jwt.JwtService;
import com.app.bematdid.model.Funcionario;
import com.app.bematdid.model.Role;
import com.app.bematdid.model.User;
import com.app.bematdid.repository.FuncionarioRepository;
import com.app.bematdid.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        User userTotal = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        Funcionario userFuncionarioRegistrado = funcionarioRepository.findFuncionarioByIdFuncionario(userTotal.getFuncionario().getIdFuncionario());

        return AuthResponse.builder()
                .token(token)
                .idFuncionario(userTotal.getFuncionario().getIdFuncionario())
                .nombreFuncionario(userFuncionarioRegistrado.getNombre())
                .apellidoFuncionario(userFuncionarioRegistrado.getApellido())
                .role(userTotal.getRole())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .funcionario(request.getFuncionario())
                .role(request.getRole())
                .build();


        userRepository.save(user);

        Funcionario userFuncionarioRegistrado = funcionarioRepository.findFuncionarioByIdFuncionario(request.getFuncionario().getIdFuncionario());

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .idFuncionario(request.getFuncionario().getIdFuncionario())
                .nombreFuncionario(userFuncionarioRegistrado.getNombre())
                .apellidoFuncionario(userFuncionarioRegistrado.getApellido())
                .build();
    }
}
