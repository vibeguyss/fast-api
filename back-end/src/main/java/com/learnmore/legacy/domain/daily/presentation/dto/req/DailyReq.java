package com.learnmore.legacy.domain.daily.presentation.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyReq {
    private Long userId;
    private String title;
    private String content;
}
