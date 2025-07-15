package com.learnmore.legacy.domain.auth.usecase;


import com.learnmore.legacy.domain.auth.dao.RefreshTokenDao;
import com.learnmore.legacy.domain.auth.presentation.dto.request.SignupReq;
import com.learnmore.legacy.domain.auth.presentation.dto.request.RefreshReq;
import com.learnmore.legacy.domain.auth.presentation.dto.request.SingInReq;
import com.learnmore.legacy.domain.auth.presentation.dto.response.TokenRes;
import com.learnmore.legacy.domain.user.error.UserError;
import com.learnmore.legacy.domain.user.model.User;
import com.learnmore.legacy.domain.user.model.enums.UserRole;
import com.learnmore.legacy.domain.user.service.UserService;
import com.learnmore.legacy.global.exception.CustomException;
import com.learnmore.legacy.global.security.jwt.JwtExtractor;
import com.learnmore.legacy.global.security.jwt.JwtProvider;
import com.learnmore.legacy.global.security.jwt.enums.JwtType;
import com.learnmore.legacy.global.security.jwt.error.JwtError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.learnmore.legacy.domain.user.model.enums.UserRole.DOCTOR;
import static com.learnmore.legacy.domain.user.model.enums.UserRole.USER;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final JwtExtractor jwtExtractor;
    private final RefreshTokenDao refreshTokenDao;

    public TokenRes signIn(SingInReq req) {
        Long id =req.id();
        if (userService.existsByUserId(id)){
            return jwtProvider.generateToken(id.toString());
        }else {
            throw new CustomException(UserError.USER_NOT_FOUND);
        }
    }

    public void signup(SignupReq req) {
        if (!userService.existsByUserId(req.id())){
            User user = User.builder()
                    .userId(req.id())
                    .role(req.userRole())
                    .imageUrl(null)
                    .build();
            userService.saveUser(user);

        }else {
            throw new CustomException(UserError.ID_DUPLICATED);
        }
    }

    public TokenRes refresh(RefreshReq req) {
        if (jwtExtractor.validateTokenType(req.refreshToken(), JwtType.ACCESS)){
            throw new CustomException(JwtError.INVALID_TOKEN_TYPE);
        }
        String id = jwtExtractor.getId(req.refreshToken());

        String savedRefreshToken = refreshTokenDao.getToken(id)
                .orElseThrow(() -> new CustomException(JwtError.REFRESH_TOKEN_NOT_FOUND));

        if (!savedRefreshToken.equals(req.refreshToken())){
            throw new CustomException(JwtError.INVALID_REFRESH_TOKEN);
        }

        refreshTokenDao.removeToken(id);

        return jwtProvider.generateToken(id);

    }
}
