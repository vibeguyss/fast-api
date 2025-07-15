package com.learnmore.legacy.domain.daily.service;

import com.learnmore.legacy.domain.daily.model.Daily;
import com.learnmore.legacy.domain.daily.model.repo.DailyJpaRepo;
import com.learnmore.legacy.domain.daily.presentation.dto.req.DailyReq;
import com.learnmore.legacy.domain.daily.presentation.dto.res.DailyRes;
import com.learnmore.legacy.domain.daily.presentation.dto.res.DoctorRes;
import com.learnmore.legacy.domain.user.model.User;
import com.learnmore.legacy.domain.user.model.enums.UserRole;
import com.learnmore.legacy.domain.user.model.repo.UserJpaRepo;
import com.learnmore.legacy.global.common.repo.UserSessionHolder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyService {
    private final DailyJpaRepo dailyJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final UserSessionHolder userSessionHolder;

    public List<DailyRes> getAllDaily() {
        User user =  userSessionHolder.get();

        List<Daily> dailyList = dailyJpaRepo.findAllByUser(user);

        return dailyList.stream()
                .map(DailyRes::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public DailyRes addDaily(DailyReq dailyReq) {
        User user = userSessionHolder.get();

        Daily daily = Daily.builder()
                .title(dailyReq.getTitle())
                .content(dailyReq.getContent())
                .user(user)
                .build();
        dailyJpaRepo.save(daily);
        return DailyRes.from(daily);
    }

    public DailyRes getDailyById(Long dailyId) {
        User user = userSessionHolder.get();
        Daily daily = dailyJpaRepo.findByDailyIdAndUser(dailyId, user);
        return DailyRes.from(daily);
    }

    public List<DoctorRes> getDoctors() {
        List<User> doctors = userJpaRepo.findAllByRole(UserRole.DOCTOR);

        return doctors.stream()
                .map(DoctorRes::from)
                .collect(Collectors.toList());
    }
}
