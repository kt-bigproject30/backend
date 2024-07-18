package com.kt.aivle.aivleproject.service;

import com.kt.aivle.aivleproject.dto.LoginRequest;
import com.kt.aivle.aivleproject.dto.UserDTO;
import com.kt.aivle.aivleproject.entity.UserEntity;
import com.kt.aivle.aivleproject.repository.PostRepository;
import com.kt.aivle.aivleproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean checkUsernameDuplicate(String username){

        return memberRepository.existsByUsername(username);
    }

    public void securityJoin(UserDTO userDTO){
        if(memberRepository.existsByUsername(userDTO.getUsername())){
            return;
        }

        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        memberRepository.save(UserEntity.toUserEntity(userDTO));
    }

    public UserDTO login(LoginRequest loginRequest) {
        UserDTO findMember = UserDTO.toUserDTO(Optional.ofNullable(memberRepository.findByUsername(loginRequest.getUsername())));

        if(findMember == null){
            return null;
        }

        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), findMember.getPassword())) {
            return null;
        }

        return findMember;
    }

    public UserDTO getLoginByUsername(String username){
        if(username == null) return null;

        return UserDTO.toUserDTO(Optional.ofNullable(memberRepository.findByUsername(username)));

    }
}