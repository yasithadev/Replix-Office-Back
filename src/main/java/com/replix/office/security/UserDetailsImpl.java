package com.replix.office.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.replix.office.models.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.*;
import java.util.stream.Collectors;

import com.replix.office.models.User;
@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String authoritiesJsonString;
    private boolean enabled;

    public static UserDetailsImpl build(User user,List<Permission> permissions) {
    /*
        StringJoiner authoritiesJsonStringJoiner = new StringJoiner(",", "[", "]");

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority("APP_USER");
            authorities.add(simpleGrantedAuthority);
            authoritiesJsonStringJoiner.add("\"APP_USER\"");

            //2nd authorities
            SimpleGrantedAuthority simpleGrantedAuthority2 = new SimpleGrantedAuthority("CREAT_USER");
            authorities.add(simpleGrantedAuthority2);
            authoritiesJsonStringJoiner.add("\"CREAT_USER\"");
        //End of the for loop
    */
        List<GrantedAuthority> authorities= permissions.stream()
                .map((permission)->new SimpleGrantedAuthority(permission.getPermissionName()))
                .collect(Collectors.toList());


        String authoritiesJsonString = permissions.stream()
                .map((permission)->"\""+permission.getPermissionName()+"\"")
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println("------------------------------authoritiesJsonString "+authoritiesJsonString+"-------------------------");//TODO:add logs
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                authoritiesJsonString,
                user.getActive());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    public Integer getUserId(){
        return this.id;
    }
}
