package com.learnmore.legacy.domain.daily.service;

import com.learnmore.legacy.domain.daily.model.Daily;
import com.learnmore.legacy.domain.daily.model.repo.DailyJpaRepo;
import com.learnmore.legacy.domain.daily.presentation.dto.req.DailyReq;
import com.learnmore.legacy.domain.daily.presentation.dto.res.DailyRes;
import com.learnmore.legacy.domain.user.model.User;
import com.learnmore.legacy.domain.user.model.repo.UserJpaRepo;
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

    public List<DailyRes> getAllDaily(Long userId) {
        List<Daily> dailyList = dailyJpaRepo.findAllByUser_UserId(userId);
        return dailyList.stream()
                .map(DailyRes::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public DailyRes addDaily(DailyReq dailyReq) {
        User user = userJpaRepo.findById(dailyReq.getUserId()).orElseThrow(EntityNotFoundException::new);
        Daily daily = Daily.builder()
                .title(dailyReq.getTitle())
                .content(dailyReq.getContent())
                .user(user)
                .build();
        dailyJpaRepo.save(daily);
        return DailyRes.from(daily);
    }

    public DailyRes getDailyById(Long userId, Long dailyId) {
        Daily daily = dailyJpaRepo.findByDailyIdAndUser_UserId(dailyId, userId)
                .orElseThrow(() -> new EntityNotFoundException("에러 발생"));
        return DailyRes.from(daily);
    }
}
