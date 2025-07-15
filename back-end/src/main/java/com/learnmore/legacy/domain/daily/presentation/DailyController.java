package com.learnmore.legacy.domain.daily.presentation;

import com.learnmore.legacy.domain.daily.presentation.dto.req.DailyReq;
import com.learnmore.legacy.domain.daily.presentation.dto.res.DailyRes;
import com.learnmore.legacy.domain.daily.presentation.dto.res.DoctorRes;
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
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<DailyRes>>> getAllDaily() {
        return BaseResponse.of(dailyService.getAllDaily());
    }

    @Operation(summary = "일기 등록", description = "일기를 등록합니다.")
    @PostMapping("")
    public ResponseEntity<BaseResponse<DailyRes>> addDaily(@RequestBody DailyReq daily) {
        return BaseResponse.of(dailyService.addDaily(daily));
    }

    @Operation(summary = "일기 상세 조회", description = "일기를 상세 조회합니다.")
    @GetMapping("/{dailyId}")
    public ResponseEntity<BaseResponse<DailyRes>> getDaily(@PathVariable Long dailyId) {
        return BaseResponse.of(dailyService.getDailyById(dailyId));
    }

    @Operation(summary = "의사 목록 모두 조회", description = "의사 목록을 모두 조회합니다.")
    @GetMapping("/doctor")
    public ResponseEntity<BaseResponse<List<DoctorRes>>> getAllDoctor() {
        return BaseResponse.of(dailyService.getDoctors());
    }


}
