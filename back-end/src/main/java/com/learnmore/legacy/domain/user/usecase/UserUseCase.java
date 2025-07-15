package com.learnmore.legacy.domain.user.usecase;

import com.learnmore.legacy.domain.user.model.User;
import com.learnmore.legacy.domain.user.presentation.dto.request.ProfileImageReq;
import com.learnmore.legacy.domain.user.presentation.dto.response.SingleUserRes;
import com.learnmore.legacy.domain.user.presentation.dto.response.UserRes;
import com.learnmore.legacy.domain.user.service.UserService;
import com.learnmore.legacy.global.common.repo.UserSessionHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserUseCase {
    private final UserSessionHolder userSessionHolder;
    private final UserService userService;

    @Transactional(readOnly = true)
    public UserRes getMe(){
        User user = userSessionHolder.get();
        return UserRes.from(user);
    }

    @Transactional
    public User updateProfileImage(ProfileImageReq req) {
        User sessionUser = userSessionHolder.get();
        User userEntity = userService.findByUserId(sessionUser.getUserId());
        userEntity.updateImageUrl(req.profileImageUrl());
        return userEntity;
    }

    @Transactional(readOnly = true)
    public SingleUserRes getUser(Long userId) {
        User user=userService.findByUserId(userId);
        return SingleUserRes.from(user);
    }

}
