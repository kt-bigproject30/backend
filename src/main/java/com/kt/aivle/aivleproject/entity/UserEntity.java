package com.kt.aivle.aivleproject.entity;

import com.kt.aivle.aivleproject.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user")
public class UserEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String username;

    private String role;

    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setRole(userDTO.getRole());
        return userEntity;
    }

    public static UserEntity toUpdateUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setRole(userDTO.getRole());
        return userEntity;
    }

}
