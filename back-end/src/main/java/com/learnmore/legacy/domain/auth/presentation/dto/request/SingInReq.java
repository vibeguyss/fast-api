package com.learnmore.legacy.domain.auth.presentation.dto.request;

import lombok.Builder;

@Builder
public record SingInReq(String email, String password) {
}
