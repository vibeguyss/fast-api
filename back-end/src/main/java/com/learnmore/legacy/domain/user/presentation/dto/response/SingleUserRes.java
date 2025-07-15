package com.learnmore.legacy.domain.user.presentation.dto.response;

import com.learnmore.legacy.domain.user.model.User;

public record SingleUserRes(
        Long userId,
        String imageUrl
) {
    public static SingleUserRes from(User user) {
        return new SingleUserRes(
                user.getUserId(),
                user.getImageUrl()
        );
    }
}


