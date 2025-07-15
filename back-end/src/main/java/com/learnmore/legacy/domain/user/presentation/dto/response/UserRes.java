package com.learnmore.legacy.domain.user.presentation.dto.response;

import com.learnmore.legacy.domain.user.model.User;

public record UserRes(
        Long userId,
        String name,
        String email,
        String imageUrl
) {
    public static UserRes from(User user) {
        return new UserRes(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getImageUrl()
        );
    }
}


