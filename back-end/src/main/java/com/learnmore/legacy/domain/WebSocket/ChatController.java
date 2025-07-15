package com.learnmore.legacy.domain.WebSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    // 클라이언트가 /app/chat.send 로 보낸 메시지를 처리
    @MessageMapping("/chat.send")
    public void send(ChatMessageDto messageDto) {
        ChatMessage saved = chatMessageService.saveMessage(messageDto);
        // 저장 후 /topic/chat/{roomId} 구독자에게 전달
        messagingTemplate.convertAndSend("/topic/chat/" + saved.getChatRoom().getId(), saved);
    }
}