package com.learnmore.legacy.domain.WebSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatMessageApi {
    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 내역 조회 API
    @GetMapping("/room/{roomId}")
    public List<ChatMessage> getMessages(@PathVariable Long roomId) {
        ChatRoom room = chatRoomService.findById(roomId);
        return chatMessageRepository.findByChatRoomOrderByCreatedAtAsc(room);
    }
}
