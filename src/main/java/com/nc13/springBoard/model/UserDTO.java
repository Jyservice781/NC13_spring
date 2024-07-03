package com.nc13.springBoard.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO implements UserDetails {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String role; //?
    private List<GrantedAuthority> authorities;


    // 직책설정 방법
    // 권한설정 방법
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    //  추가적인 권한 설정 가능
    public void setAuthorities(String authority){
        if(this.authorities == null){
            this.authorities = new ArrayList<>();
        }
        this.authorities.add(new SimpleGrantedAuthority(authority));
    }

}
