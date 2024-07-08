package com.kt.aivle.aivleproject.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="user")
public class UserEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true) // unique 제약조건
    private String email;

    @Column
    private String password;

    @Column(unique = true)
    private String userName;

}
