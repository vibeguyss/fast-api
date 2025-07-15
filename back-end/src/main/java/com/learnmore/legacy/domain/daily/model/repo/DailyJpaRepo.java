package com.learnmore.legacy.domain.daily.model.repo;

import com.learnmore.legacy.domain.daily.model.Daily;
import com.learnmore.legacy.domain.daily.presentation.dto.res.DailyRes;
import com.learnmore.legacy.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyJpaRepo extends JpaRepository<Daily, Long> {
    List<Daily> findAllByUser(User user);

    Daily findByDailyIdAndUser(Long dailyId, User user);
}
