package com.replix.office.security;

import com.replix.office.models.User;
import com.replix.office.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //@Autowired
    //UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    //@Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User user = userRepository.findByEmail(username)
        //        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        var user = new User();
        user.setId(1L);
        user.setUsername("Yasitha Bandara");
        user.setEmail("yasitha.dev@gmail.com");
        user.setPassword("$2a$12$Co1qHqSYZwmYw11CrgA51u6l8Le8072XR/Ft1ZC7EdbUJl0PXVF8.");//bycript hash for yasitha@123
        user.setEnabled(true);
        //user.setRoles();
        return UserDetailsImpl.build(user);
    }

    public UserDetails getUserDetailsFromJwtToken(String jwt) {
        return jwtUtils.getUserDetailsFromJwtToken(jwt);
    }
}
