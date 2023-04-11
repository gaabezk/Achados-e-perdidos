package br.com.gabezk.achadoseperdidos.services;

import br.com.gabezk.achadoseperdidos.enuns.Role;
import br.com.gabezk.achadoseperdidos.exceptions.ErrorException;
import br.com.gabezk.achadoseperdidos.models.dtos.UserRequestDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserUpdateDto;
import br.com.gabezk.achadoseperdidos.models.entity.UserEntity;
import br.com.gabezk.achadoseperdidos.repositories.UserRepository;
import br.com.gabezk.achadoseperdidos.services.interfaces.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return modelMapper.map(userRepository.findAll(), new TypeToken<List<UserResponseDto>>() {}.getType());
    }

    @Override
    public List<UserResponseDto> getAllUsersByRole(Role role) {
        // Busca as entidades de usuário do repositório com base no papel fornecido
        var usersEntities = userRepository.findAllByRole(role);
        // Cria um TypeToken para mapear a lista de entidades de usuário para uma lista de objetos UserResponseDto
        var listType = new TypeToken<List<UserResponseDto>>() {}.getType();
        // Mapeia a lista de entidades de usuário para uma lista de objetos UserResponseDto
        List<UserResponseDto> usersDto = modelMapper.map(usersEntities, listType);
        // Retorna a lista de objetos UserResponseDto mapeados
        return usersDto;
    }

    @Override
    public UserResponseDto getUserById(UUID id) throws ErrorException {
        var user = userRepository.findById(id).orElseThrow(() -> new ErrorException("Usuário não encontrado com o id: " + id));;
        return modelMapper.map(user,UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) throws ErrorException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new ErrorException("Usuário não encontrado com o email: " + email));;
        return modelMapper.map(user,UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserByPhone(String phone) throws ErrorException {
        var user = userRepository.findByPhone(phone).orElseThrow(() -> new ErrorException("Usuário não encontrado com o telefone: " + phone));;
        return modelMapper.map(user,UserResponseDto.class);
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequest) throws ErrorException {

        if(existsByEmail(userRequest.getEmail())) throw new ErrorException("Usuário com email: " + userRequest.getEmail() + " já existe!");
        if(existsByPhone(userRequest.getPhone())) throw new ErrorException(String.format("Usuário com telefone: %s já existe!", userRequest.getPhone()));

        var hashPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(hashPassword);

        var user = modelMapper.map(userRequest,UserEntity.class); // criação
        var data = userRepository.save(user);
        return modelMapper.map(data,UserResponseDto.class);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UUID id, @Valid UserUpdateDto userUpdate) throws ErrorException {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorException("Usuário com id: " + id + " não existe!"));

        if (!userUpdate.getEmail().equals(user.getEmail())) {
            if(userRepository.existsByEmail(userUpdate.getEmail())){
                throw new ErrorException("Usuário com email: " + userUpdate.getEmail() + " já existe!");
            }
        }
        modelMapper.map(userUpdate, user);
        userRepository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    @Transactional
    public String updateRole(UUID id, Role role) throws ErrorException {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorException("Usuário com id: " + id + " não existe!"));
        user.setRole(role);
        userRepository.save(user);
        return "Role atualizada com sucesso!";
    }


    @Override
    @Transactional
    public String updatePassword(UUID id, String oldPass, String newPass) throws ErrorException {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorException("Usuário com id: " + id + " não existe!"));

        if(!passwordEncoder.matches(oldPass,user.getHashPassword())){
            throw new ErrorException("Senhas nao batem");
        }


        user.setHashPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);
        return "Senha atualizada com sucesso!";
    }

    @Override
    @Transactional
    public String deleteUser(UUID id) throws ErrorException {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ErrorException("Usuário com id: " + id + " não existe!"));

        try {
            userRepository.delete(user);
            return "Usuário deletado com sucesso!";
        }catch (Exception ex){
            throw new ErrorException(ex.getMessage());
        }
    }

    @Override
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}
