package com.kt.aivle.aivleproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor @Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String userName;
    public String PrettyPrint() {
        return "id: "+this.id+" email: "+this.email+" password: "+this.password;
    }
}
