package com.learnmore.legacy.domain.WebSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    // 메시지를 DB에 저장
    public ChatMessage saveMessage(ChatMessageDto dto) {
        ChatRoom room = chatRoomService.getOrCreateRoom(dto.senderId, dto.receiverId);

        ChatMessage msg = new ChatMessage();
        msg.setChatRoom(room);
        msg.setSenderId(dto.senderId);
        msg.setMessage(dto.message);
        return chatMessageRepository.save(msg);
    }
}