package com.replix.office.service;

import com.replix.office.dtos.UserDTO;
import com.replix.office.exception.UserCreationException;
import com.replix.office.models.User;
import com.replix.office.repository.UserRepository;
import com.replix.office.security.AuthTokenFilterJwtUserDetails;
import com.replix.office.security.UserDetailsImpl;
import com.replix.office.security.UserDetailsServiceImpl;
import com.replix.office.util.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UserManagementService {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilterJwtUserDetails.class);

    public void createUser(UserDTO userDTO) {
        try {
            User user = new User();
            user.setUsername(userDTO.getUserName());
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getSecondName());
            user.setActive(userDTO.isActive());
            user.setPassword("$2a$12$Co1qHqSYZwmYw11CrgA51u6l8Le8072XR/Ft1ZC7EdbUJl0PXVF8.");//yasitha@123 is default password
            user.setCreatedBy(new User(CurrentUser.getCurrentUserId()));
            user.setLastModifiedBy(new User(CurrentUser.getCurrentUserId()));
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "";
            logger.warn("Constraint violation during user creation: {}", rootMessage);
            if (rootMessage.contains("Duplicate entry")) {
                if (rootMessage.contains("username")) {
                    throw new UserCreationException(40901,"USERNAME_TAKEN","Username already exists.","Username already exists. Please choose a different one.");//TODO use enum
                } else if (rootMessage.contains("email")) {
                    throw new UserCreationException(40902,"EMAIL_REGISTERED","Email is already registered.","Email is already registered. Try logging in or use a different email.");//
                } else {
                    throw new UserCreationException(40900,"DATA_CONFLICT","Duplicate data detected.","");//this will be a bug.
                }
            }//TODO Delete UserCreationException create DataConflictException.use DATA_CONFLICT errorcode .delete USERNAME_TAKEN,EMAIL_REGISTERED.add property "conflict field" .i.e conflictField:"email"
            throw new UserCreationException(40900,"DATA_CONFLICT","Duplicate data detected.","");//this will be a bug.
        } catch (Exception ex) {
            logger.error("Unexpected error while saving user", ex);
            throw new RuntimeException("Internal server error while saving user");//TODO Cmplete with ControllerAdvice
        }
    }
}
