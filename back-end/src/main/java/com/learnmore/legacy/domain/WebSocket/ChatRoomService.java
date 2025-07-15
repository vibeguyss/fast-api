package com.learnmore.legacy.domain.WebSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    // 두 유저 ID로 방을 찾거나 없으면 새로 생성
    public ChatRoom getOrCreateRoom(Long senderId, Long receiverId) {
        Long min = Math.min(senderId, receiverId);
        Long max = Math.max(senderId, receiverId);
        return chatRoomRepository.findByUserAIdAndUserBId(min, max)
                .orElseGet(() -> chatRoomRepository.save(new ChatRoom(min, max)));
    }

    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id).orElseThrow();
    }
}