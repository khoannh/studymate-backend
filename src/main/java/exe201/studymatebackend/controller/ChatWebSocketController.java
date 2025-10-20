package exe201.studymatebackend.controller;

import exe201.studymatebackend.config.JwtUtil;
import exe201.studymatebackend.dto.request.message.SendMessageRequest;
import exe201.studymatebackend.dto.response.message.SendMessageResponse;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketController.class);

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Xử lý tin nhắn gửi qua WebSocket - Lấy user từ token trong mỗi request
     */
    @MessageMapping("/chat.sendMessage")
    public void processMessage(@Payload SendMessageRequest request,
                               SimpMessageHeaderAccessor headerAccessor) {
        logger.info("=== PROCESS MESSAGE DEBUG ===");
        logger.info("Request: {}", request);

        try {
            // Lấy token từ headers
            String token = (String) headerAccessor.getHeader("JWT_TOKEN");
            logger.info("JWT_TOKEN from headers: {}", token != null ? token.substring(0, 10) + "..." : "null");

            // Nếu không có token trong headers, thử lấy từ Authorization header
            if (token == null) {
                String authHeader = (String) headerAccessor.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                    logger.info("Token from Authorization header: {}", token.substring(0, 10) + "...");
                }
            }

            // Nếu vẫn không có token, thử lấy từ nativeHeaders
            if (token == null) {
                Object nativeHeaders = headerAccessor.getHeader("nativeHeaders");
                if (nativeHeaders instanceof java.util.Map) {
                    java.util.Map<?, ?> headers = (java.util.Map<?, ?>) nativeHeaders;
                    for (Object key : headers.keySet()) {
                        if ("authorization".equalsIgnoreCase(key.toString())) {
                            Object value = headers.get(key);
                            if (value instanceof java.util.List) {
                                java.util.List<?> list = (java.util.List<?>) value;
                                if (!list.isEmpty()) {
                                    String authHeader = list.get(0).toString();
                                    if (authHeader.startsWith("Bearer ")) {
                                        token = authHeader.substring(7);
                                        logger.info("Token from nativeHeaders: {}", token.substring(0, 10) + "...");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (token == null) {
                logger.error("No token found in any headers");
                return;
            }

            // Lấy username từ token
            String username = jwtUtil.getUsernameFromToken(token);
            logger.info("Username from token: {}", username);

            if (username == null) {
                logger.error("Invalid token - cannot extract username");
                return;
            }

            // Lấy Account từ database
            Account account = accountRepository.findByUsername(username);
            if (account == null) {
                logger.error("Account not found for username: {}", username);
                return;
            }

            logger.info("Account found: {}", account.getUsername());

            // Gọi service với Account trực tiếp
            SendMessageResponse response = chatMessageService.sendMessageWithAccount(request, account);

            // Gửi message đến room
            messagingTemplate.convertAndSend("/topic/room/" + request.getRoomID(), response);

            logger.info("Message sent successfully: {}", response);

        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}