package br.com.gabezk.achadoseperdidos.config;

import br.com.gabezk.achadoseperdidos.models.dtos.PostResponseDto;
import br.com.gabezk.achadoseperdidos.models.dtos.UserRequestDto;
import br.com.gabezk.achadoseperdidos.models.entity.PostEntity;
import br.com.gabezk.achadoseperdidos.models.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(UserRequestDto.class, UserEntity.class)
                .<String>addMapping(
                        UserRequestDto::getPassword,
                        UserEntity::setHashPassword
                );

        modelMapper.createTypeMap(UserEntity.class, UserRequestDto.class)
                .<String>addMapping(
                        UserEntity::getHashPassword,
                        UserRequestDto::setPassword
                );

        modelMapper.createTypeMap(PostEntity.class, PostResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getFullName(),
                    PostResponseDto::setUserFullName);
        });

        return modelMapper;
    }
}