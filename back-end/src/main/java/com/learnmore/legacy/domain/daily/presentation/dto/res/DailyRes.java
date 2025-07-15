package com.learnmore.legacy.domain.daily.presentation.dto.res;

import com.learnmore.legacy.domain.daily.model.Daily;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyRes {
    private Long dailyId;
    private String title;
    private String content;

    public static DailyRes from(Daily daily) {
        return DailyRes.builder()
                .dailyId(daily.getDailyId())
                .title(daily.getTitle())
                .content(daily.getContent())
                .build();
    }
}
