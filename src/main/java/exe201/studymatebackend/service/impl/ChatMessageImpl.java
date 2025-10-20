package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.message.SendMessageRequest;
import exe201.studymatebackend.dto.response.message.GetAllMessageOfRoomResponse;
import exe201.studymatebackend.dto.response.message.SendMessageResponse;
import exe201.studymatebackend.enums.MessageType;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.AccountRoom;
import exe201.studymatebackend.pojo.ChatMessage;
import exe201.studymatebackend.pojo.Room;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.repository.AccountRoomRepository;
import exe201.studymatebackend.repository.ChatMessageRepository;
import exe201.studymatebackend.repository.RoomRepository;
import exe201.studymatebackend.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageImpl implements ChatMessageService {


    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRoomRepository accountRoomRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ChatMessageImpl.class);

    @Override
    @Transactional
    public SendMessageResponse sendMessage(SendMessageRequest request, Principal principal) {
        logger.info("=== SEND MESSAGE DEBUG ===");
        logger.info("Request: content={}, roomID={}", request.getContent(), request.getRoomID());
        logger.info("Principal: {}", principal);
        logger.info("Principal type: {}", principal != null ? principal.getClass().getName() : "null");
        logger.info("SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication());

        if (principal == null) {
            logger.error("Principal is null - trying fallback authentication");

            // Thử lấy authentication từ SecurityContext
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
                logger.info("Using authentication from SecurityContext instead");
                principal = auth;
            } else {
                logger.error("No valid authentication found");
                throw new AppException(ErrorCode.UNCATEGORIZED);
            }
        }

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) principal;
        Account sender = (Account) authentication.getPrincipal();
        logger.info("Message from user: {}", sender.getUsername());
        Room room = roomRepository.findByRoomID(request.getRoomID());
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        Optional<AccountRoom> accountRoomOpt = accountRoomRepository.findByAccountAndRoom(sender, room);
        if (accountRoomOpt.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_IN_ROOM);
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(request.getContent());
        chatMessage.setRoom(room);
        chatMessage.setAccount(sender);
        chatMessage.setMessageType(MessageType.CHAT);
        chatMessage.setSentAt(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        SendMessageResponse response = SendMessageResponse.builder()
                .messageID(chatMessage.getMessageID())
                .content(chatMessage.getContent())
                .accountID(sender.getAccountID())
                .sender(sender.getUsername())
                .roomID(room.getRoomID())
                .messageType(chatMessage.getMessageType())
                .sentAt(chatMessage.getSentAt())
                .build();

        messagingTemplate.convertAndSend("/topic/room/" + room.getRoomID(), response);

        return response;
    }

    // Thêm method mới
    @Override
    @Transactional
    public SendMessageResponse sendMessageWithAuth(SendMessageRequest request, UsernamePasswordAuthenticationToken authentication) {
        logger.info("=== SEND MESSAGE DEBUG (UsernamePasswordAuthenticationToken) ===");
        logger.info("Request: content={}, roomID={}", request.getContent(), request.getRoomID());
        logger.info("Authentication: {}", authentication);

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("Authentication is null or not authenticated");
            throw new AppException(ErrorCode.UNCATEGORIZED);
        }

        Account sender = (Account) authentication.getPrincipal();
        logger.info("Message from user: {}", sender.getUsername());

        return processMessage(request, sender);
    }

    @Override
    @Transactional
    public SendMessageResponse sendMessageWithAccount(SendMessageRequest request, Account sender) {
        logger.info("=== SEND MESSAGE DEBUG (Account) ===");
        logger.info("Request: content={}, roomID={}", request.getContent(), request.getRoomID());
        logger.info("Sender: {}", sender.getUsername());

        if (sender == null) {
            logger.error("Sender is null");
            throw new AppException(ErrorCode.UNCATEGORIZED);
        }

        return processMessage(request, sender);
    }

    // Method processMessage (tách logic chung)
    private SendMessageResponse processMessage(SendMessageRequest request, Account sender) {
        Room room = roomRepository.findByRoomID(request.getRoomID());
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }

        Optional<AccountRoom> accountRoomOpt = accountRoomRepository.findByAccountAndRoom(sender, room);
        if (accountRoomOpt.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_IN_ROOM);
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(request.getContent());
        chatMessage.setRoom(room);
        chatMessage.setAccount(sender);
        chatMessage.setMessageType(MessageType.CHAT);
        chatMessage.setSentAt(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);

        SendMessageResponse response = SendMessageResponse.builder()
                .messageID(chatMessage.getMessageID())
                .content(chatMessage.getContent())
                .accountID(sender.getAccountID())
                .sender(sender.getUsername())
                .roomID(room.getRoomID())
                .messageType(chatMessage.getMessageType())
                .sentAt(chatMessage.getSentAt())
                .build();

        return response;
    }

    @Override
    public GetAllMessageOfRoomResponse getAllMessageOfRoom(Integer roomID) {
        Room room = roomRepository.findByRoomID(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        List<ChatMessage> messages = chatMessageRepository.findByRoomOrderBySentAtAsc(room);
        List<GetAllMessageOfRoomResponse.MessageResponse> messageResponses = messages.stream().map(msg -> GetAllMessageOfRoomResponse.MessageResponse.builder()
                .messageID(msg.getMessageID())
                .content(msg.getContent())
                .accountID(msg.getAccount().getAccountID())
                .sender(msg.getAccount().getUsername())
                .messageType(msg.getMessageType())
                .sentAt(msg.getSentAt())
                .build()).toList();
        return GetAllMessageOfRoomResponse.builder()
                .roomID(roomID)
                .messages(messageResponses)
                .build();
    }


}
