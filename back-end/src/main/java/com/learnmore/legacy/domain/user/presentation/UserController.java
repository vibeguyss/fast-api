package com.learnmore.legacy.domain.user.presentation;

import com.learnmore.legacy.domain.user.model.User;
import com.learnmore.legacy.domain.user.presentation.dto.request.ProfileImageReq;
import com.learnmore.legacy.domain.user.presentation.dto.response.SingleUserRes;
import com.learnmore.legacy.domain.user.presentation.dto.response.UserRes;
import com.learnmore.legacy.domain.user.usecase.UserUseCase;
import com.learnmore.legacy.global.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @Operation(summary = "유저 단일 정보 조회", description = "유저 id 로 유저 정보 조회 (로그인 불필요)")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<SingleUserRes>> getUserById(@PathVariable Long id) {
        return BaseResponse.of(userUseCase.getUser(id));
    }

    @Operation(summary = "내 정보 조회", description = "로그인된 사용자의 프로필 정보를 반환합니다.")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserRes>> getMe(){
        return BaseResponse.of(userUseCase.getMe());
    }

    @Operation(summary = "프로필 사진 변경", description = "로그인된 유저의 프로필 사진을 변경합니다")
    @PatchMapping("/image")
    public  ResponseEntity<BaseResponse<User>> updateProfileImage(@RequestBody ProfileImageReq req){
        return BaseResponse.of(userUseCase.updateProfileImage(req));
    }
}
