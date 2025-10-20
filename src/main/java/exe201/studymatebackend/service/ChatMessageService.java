package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.message.SendMessageRequest;
import exe201.studymatebackend.dto.response.message.GetAllMessageOfRoomResponse;
import exe201.studymatebackend.dto.response.message.SendMessageResponse;
import exe201.studymatebackend.pojo.Account;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface ChatMessageService {
    SendMessageResponse sendMessage(SendMessageRequest request, java.security.Principal principal);

    // Method sử dụng UsernamePasswordAuthenticationToken
    SendMessageResponse sendMessageWithAuth(SendMessageRequest request, UsernamePasswordAuthenticationToken authentication);

    // Method mới sử dụng Account trực tiếp
    SendMessageResponse sendMessageWithAccount(SendMessageRequest request, Account account);

    GetAllMessageOfRoomResponse getAllMessageOfRoom(Integer roomID);
}