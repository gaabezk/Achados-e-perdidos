package br.com.gabezk.achadoseperdidos.config;

import br.com.gabezk.achadoseperdidos.models.dtos.UserRequestDto;
import br.com.gabezk.achadoseperdidos.models.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(UserRequestDto.class, UserEntity.class)
                .<String>addMapping(
                        src -> src.getPassword(),
                        (dest, value) -> dest.setHashPassword(value)
                );
        return modelMapper;
    }
}
