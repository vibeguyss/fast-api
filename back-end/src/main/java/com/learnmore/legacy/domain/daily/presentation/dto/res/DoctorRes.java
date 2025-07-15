package com.learnmore.legacy.domain.daily.presentation.dto.res;

import com.learnmore.legacy.domain.user.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorRes {
    private Long userId;
    private String name;
    private String imageUrl;

    public static DoctorRes from(User user) {
        return new DoctorRes(user.getUserId(), user.getName(), user.getImageUrl());
    }
}
