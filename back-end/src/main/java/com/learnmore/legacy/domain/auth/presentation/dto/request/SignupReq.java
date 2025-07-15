package com.learnmore.legacy.domain.auth.presentation.dto.request;

import com.learnmore.legacy.domain.user.model.enums.UserRole;

public record SignupReq(long id , String email, String password , UserRole userRole) {
}
