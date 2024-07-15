package com.kt.aivle.aivleproject.entity;

import com.kt.aivle.aivleproject.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;


@Entity
@Setter
@Getter
@Table(name = "user")
public class UserEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

//    @Column
//    private String email;



    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).*$", message = "비밀번호는 숫자와 알파벳을 포함하여야 합니다.")
    private String password;

    @Column
    private String passwordCheck;

    @Column(nullable = false, unique = true)
    @Size(min = 6, message = "ID는 6자 이상 구성되어야 합니다.")
    private String username;

    @Column
    private String name;

    @Column
    private String role;

    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setName(userDTO.getName());
        userEntity.setRole(userDTO.getRole());
        return userEntity;
    }

//    public static UserEntity toUpdateUserEntity(UserDTO userDTO) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(userDTO.getId());
//        userEntity.setEmail(userDTO.getEmail());
//        userEntity.setPassword(userDTO.getPassword());
//        userEntity.setUsername(userDTO.getUsername());
//        userEntity.setName(userDTO.getName());
//        userEntity.setRole(userDTO.getRole());
//        return userEntity;
//    }

}
