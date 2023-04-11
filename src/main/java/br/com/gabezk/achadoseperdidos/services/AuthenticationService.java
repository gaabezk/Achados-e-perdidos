package br.com.gabezk.achadoseperdidos.services;

import br.com.gabezk.achadoseperdidos.config.JwtService;
import br.com.gabezk.achadoseperdidos.enuns.Role;
import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import br.com.gabezk.achadoseperdidos.models.dtos.AuthResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.LoginDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserRequestDto;
import br.com.gabezk.achadoseperdidos.models.entity.UserEntity;
import br.com.gabezk.achadoseperdidos.models.entity.Token;
import br.com.gabezk.achadoseperdidos.repositories.TokenRepository;
import br.com.gabezk.achadoseperdidos.enuns.TokenType;
import br.com.gabezk.achadoseperdidos.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Transactional
  public AuthResponseDto register(UserRequestDto request) throws ErrorException {

    if(repository.existsByEmail(request.getEmail())) throw new ErrorException("Usuário com email: " + request.getEmail() + " já existe!");
    if(repository.existsByPhone(request.getPhone())) throw new ErrorException(String.format("Usuário com telefone: %s já existe!", request.getPhone()));

    UserEntity user = UserEntity.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .phone(request.getPhone())
        .hashPassword(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthResponseDto.builder()
        .token(jwtToken)
        .build();
  }

  @Transactional
  public AuthResponseDto authenticate(LoginDto loginDto) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
                loginDto.email(),
                loginDto.password()
        )
    );
    var user = repository.findByEmail(loginDto.email())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthResponseDto.builder()
        .token(jwtToken)
        .build();
  }

  private void saveUserToken(UserEntity user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserEntity user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
