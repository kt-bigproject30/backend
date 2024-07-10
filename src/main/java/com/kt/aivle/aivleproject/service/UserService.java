package com.kt.aivle.aivleproject.service;

import com.kt.aivle.aivleproject.dto.UserDTO;
import com.kt.aivle.aivleproject.entity.UserEntity;
import com.kt.aivle.aivleproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class UserService {
        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        private final UserRepository userRepository;

        public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
            this.userRepository = userRepository;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }

        public void joinProcess(UserDTO userDTO) {
//            String email = userDTO.getEmail();
            String username = userDTO.getUsername();
            String password = userDTO.getPassword();


            Boolean isExist = userRepository.existsByUsername(username);

            if (isExist) {
                return;
            }

            UserEntity data = new UserEntity();

//            data.setEmail(email);
            data.setUsername(username);
            data.setPassword(bCryptPasswordEncoder.encode(password));
            data.setRoll("ROLE_ADMIN");

            userRepository.save(data);
        }
        public void save(UserDTO userDTO) {
            // 1. dto -> entity 변환
            // 2. repository의 save 메서드 호출
            UserEntity userEntity = UserEntity.toUserEntity(userDTO);
            userRepository.save(userEntity);
            // repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)
        }

        public UserDTO login(UserDTO userDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
            Optional<UserEntity> byUsername = userRepository.findByUsername(userDTO.getUsername());
            if (byUsername.isPresent()) {
                // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
                UserEntity userEntity = byUsername.get();
                if (userEntity.getPassword().equals(userDTO.getPassword())) {
                    // 비밀번호 일치
                    // entity -> dto 변환 후 리턴
                    UserDTO dto = UserDTO.toUserDTO(userEntity);
                    return dto;
                } else {
                    // 비밀번호 불일치(로그인실패)
                    return null;
                }
            } else {
                // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
                return null;
            }
        }

        public List<UserDTO> findAll() {
            List<UserEntity> userEntityList = userRepository.findAll();
            List<UserDTO> userDTOList = new ArrayList<>();
            for (UserEntity userEntity: userEntityList) {
                userDTOList.add(UserDTO.toUserDTO(userEntity));
            }
            return userDTOList;
        }

    public UserDTO findById(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }

    }

    public UserDTO updateForm(String username) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    public void update(UserDTO userDTO) {
        userRepository.save(UserEntity.toUpdateUserEntity(userDTO));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

//    public String emailCheck(String username) {
//        Optional<UserEntity> byUserEmail = userRepository.findByUsername(username);
//        if (byUserEmail.isPresent()) {
//            // 조회결과가 있다 -> 사용할 수 없다.
//            return null;
//        } else {
//            // 조회결과가 없다 -> 사용할 수 있다.
//            return "ok";
//        }
//    }

    public String usernameCheck(String username) {
        Optional<UserEntity> byUserName = userRepository.findByUsername(username);
        if (byUserName.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }
}
