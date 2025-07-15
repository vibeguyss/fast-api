package com.learnmore.legacy.domain.WebSocket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 유저 A, B 조합으로 방을 찾음 (id가 작은 순)
    Optional<ChatRoom> findByUserAIdAndUserBId(Long userAId, Long userBId);
}


