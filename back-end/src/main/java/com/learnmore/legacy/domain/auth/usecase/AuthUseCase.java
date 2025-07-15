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
        String email =req.email();
        if (userService.existsByEmail(email) ){
            User user = userService.findByEmail(email);
            if (user.getPassword().equals(req.password())){
                Long id = user.getUserId();
                return jwtProvider.generateToken(id.toString());
            }else {
                throw new CustomException(UserError.PW);
            }
        }else {
            throw new CustomException(UserError.USER_NOT_FOUND);
        }
    }

    public void signup(SignupReq req) {
        if (!userService.existsByEmail(req.email())){
            User user = User.builder()
                    .email(req.email())
                    .name(req.name())
                    .password(req.password())
                    .role(req.userRole())
                    .imageUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fblog.naver.com%2Flattepain%2F221803111318%3FviewType%3Dpc&psig=AOvVaw2onxVOJsizeOzfFq3JvxTh&ust=1752685891535000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCIiZnvGtv44DFQAAAAAdAAAAABAE")
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
