package com.learnmore.legacy.domain.user.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("USER"),
    DOCTOR("DOCTOR"),
    ADMIN("ADMIN");

    private final String name;
}
