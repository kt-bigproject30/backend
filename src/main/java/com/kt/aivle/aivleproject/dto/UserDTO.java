package com.kt.aivle.aivleproject.dto;

import com.kt.aivle.aivleproject.entity.UserEntity;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private Long id;
    private UUID uuid;
//    private String email;
    private String username;
    private String password;
    private String passwordCheck;
    private String name;
    private String role;

    public static UserDTO toUserDTO(Optional<UserEntity> userEntity) {

        UserDTO userDTO = new UserDTO();
//        userDTO.setId(userEntity.get().getId());
        userDTO.setUuid(userEntity.get().getUuid());
//        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.get().getPassword());
//        userDTO.setPasswordCheck(userEntity.getPasswordCheck());
        userDTO.setUsername(userEntity.get().getUsername());
        userDTO.setName(userEntity.get().getName());
        userDTO.setRole(userEntity.get().getRole());
        return userDTO;
    }
}

