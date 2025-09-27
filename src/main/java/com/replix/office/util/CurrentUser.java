package com.replix.office.util;

import com.replix.office.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public  class CurrentUser {
    public static Integer getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal).getUserId();
        }

        throw new IllegalStateException("User is not authenticated or principal is invalid");
    }
}
