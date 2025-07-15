package com.learnmore.legacy.domain.user.model.repo;

import com.learnmore.legacy.domain.user.model.User;
import com.learnmore.legacy.domain.user.model.enums.UserRole;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {
    User findByUserId(Long id);


    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);;

    List<User> findAllByRole(UserRole userRole);
}
