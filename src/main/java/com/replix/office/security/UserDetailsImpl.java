package com.replix.office.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.*;

import com.replix.office.models.User;
@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String authoritiesJsonString;
    private boolean enabled;

    public static UserDetailsImpl build(User user) {

        //TODO:Authorities need to extract from user model
        StringJoiner authoritiesJsonStringJoiner = new StringJoiner(",", "[", "]");

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        //TODO:should happen within for loops
            SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority("APP_USER");
            authorities.add(simpleGrantedAuthority);
            authoritiesJsonStringJoiner.add("\"APP_USER\"");
            //TODO:2nd iteration
            SimpleGrantedAuthority simpleGrantedAuthority2 = new SimpleGrantedAuthority("CREAT_USER");
            authorities.add(simpleGrantedAuthority2);
            authoritiesJsonStringJoiner.add("\"CREAT_USER\"");
        //TODO:End of the for loop

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                authoritiesJsonStringJoiner.toString(),
                user.isEnabled());
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
}
