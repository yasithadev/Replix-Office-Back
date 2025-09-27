package com.replix.office.security;

import com.replix.office.models.Permission;
import com.replix.office.models.User;
import com.replix.office.repository.UserRepository;
import com.replix.office.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //@Autowired
    //UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {//ToDo:create new Exception type EmailNotFoundException(Should Extend Authentication Exception)
        User user= userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        System.out.println("/////////////////////users : " + user.getUsername() + " ////////////");
        List<Permission> permissions= userRepository.findPermissionsByUserId(1);
        return UserDetailsImpl.build(user,permissions);
    }

    @Override
    @Transactional//when signin ,spring use this method.so, cannot change the method name
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        System.out.println("/////////////////////users : " + user.getUsername() + " ////////////");//TODO:add proper logs
        //System.out.println("/////////////////////users roles name : " + user.getUserRoles().get(0).getRole().getRolePermissions().size() + "//////////////");//TODO:write unit testing
        //System.out.println("/////////////////////users permission sample name : " + user.getUserRoles().get(0).getRole().getRolePermissions().get(0).getPermission().getPermissionName() + "//////////////");//TODO:write unit testing
        List<Permission> permissions= userRepository.findPermissionsByUserId(1);
        System.out.println("/////////////////////permissions List Size : " + permissions.size() + "//////////////");//TODO:write unit testing
        //System.out.println("/////////////////////users permission sample name : " + permissions.get(0).getPermissionName() + "//////////////");//TODO:write unit testing
        return UserDetailsImpl.build(user,permissions);
    }

    public UserDetails getUserDetailsFromJwtToken(String jwt) {
        return jwtUtils.getUserDetailsFromJwtToken(jwt);
    }
}
