package br.com.gabezk.achadoseperdidos.services.interfaces;

import br.com.gabezk.achadoseperdidos.enuns.Role;
import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import br.com.gabezk.achadoseperdidos.models.dtos.UserRequestDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserUpdateDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    List<UserResponseDto> getAllUsersByRole(Role role);
    UserResponseDto getUserById(UUID id) throws ErrorException;
    UserResponseDto getUserByEmail(String email) throws ErrorException;
    UserResponseDto getUserByPhone(String phone) throws ErrorException;
    UserResponseDto createUser(UserRequestDto userRequest) throws ErrorException;
    UserResponseDto updateUser(UUID id, UserUpdateDto userUpdate) throws ErrorException;
    String updateRole(UUID id, Role role) throws ErrorException;
    String updatePassword(UUID id, String password) throws ErrorException;
    String deleteUser(UUID id) throws ErrorException;
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
