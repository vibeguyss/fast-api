package com.learnmore.legacy.domain.daily.presentation;

import com.learnmore.legacy.domain.daily.presentation.dto.req.DailyReq;
import com.learnmore.legacy.domain.daily.presentation.dto.res.DailyRes;
import com.learnmore.legacy.domain.daily.service.DailyService;
import com.learnmore.legacy.global.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily")
public class DailyController {
    private final DailyService dailyService;

    @Operation(summary = "일기 모두 조회", description = "일기를 모두 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<List<DailyRes>>> getAllDaily(@PathVariable Long userId) {
        return BaseResponse.of(dailyService.getAllDaily(userId));
    }

    @Operation(summary = "일기 모두 조회", description = "일기를 모두 조회합니다.")
    @PostMapping("")
    public ResponseEntity<BaseResponse<DailyRes>> addDaily(@RequestBody DailyReq daily) {
        return BaseResponse.of(dailyService.addDaily(daily));
    }

    @Operation(summary = "일기 상세 조회", description = "일기를 상세 조회합니다.")
    @GetMapping("/{userId}/{dailyId}")
    public ResponseEntity<BaseResponse<DailyRes>> getDaily(@PathVariable Long userId, @PathVariable Long dailyId) {
        return BaseResponse.of(dailyService.getDailyById(userId, dailyId));
    }

}
