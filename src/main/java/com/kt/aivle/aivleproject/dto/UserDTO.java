package com.kt.aivle.aivleproject.dto;

import com.kt.aivle.aivleproject.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private Long id;
//    private String email;
    private String username;
    private String password;
    private String passwordCheck;
    private String name;
    private String role;

    public static UserDTO toUserDTO(UserEntity userEntity) {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
//        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
//        userDTO.setPasswordCheck(userEntity.getPasswordCheck());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setName(userEntity.getName());
        userDTO.setRole(userEntity.getRole());
        return userDTO;
    }
}

