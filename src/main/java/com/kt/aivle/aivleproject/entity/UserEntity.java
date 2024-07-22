package com.kt.aivle.aivleproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kt.aivle.aivleproject.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "user")
//        , indexes = {
//        @Index(name = "idx_uuid", columnList = "uuid", unique = true)
//})
public class UserEntity {
//    @Id // pk 지정
//    @Column(nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
//    private Long id;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID uuid;
//    @Column
//    private String email;

    @Column(nullable = false, unique = true)
    @Size(min = 6, message = "ID는 6자 이상 구성되어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ID는 영문자와 숫자만 포함해야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).*$", message = "비밀번호는 숫자와 알파벳을 포함하여야 합니다.")
    private String password;

    @Column
    private String passwordCheck;

    @Column(nullable = false)
    private String name;

    @Column
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity") // mappedBy 속성 값은 PostEntity 클래스 내의 UserEntity 참조 변수명과 일치해야 합니다.
    private List<PostEntity> postEntities = new ArrayList<>();

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