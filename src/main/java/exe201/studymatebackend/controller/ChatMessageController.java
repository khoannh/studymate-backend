package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.message.GetAllMessageOfRoomResponse;
import exe201.studymatebackend.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message-management")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

//    @PostMapping("/send")
//    public ApiResponse<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest sendMessageRequest) {
//        SendMessageResponse result = chatMessageService.sendMessage(sendMessageRequest);
//        return ApiResponse.<SendMessageResponse>builder()
//                .code(200)
//                .message("Message sent successfully")
//                .result(result)
//                .build();
//    }

    @GetMapping("/messages/{roomID}")
    public ApiResponse<GetAllMessageOfRoomResponse> getMessages(@PathVariable Integer roomID) {
        GetAllMessageOfRoomResponse result = chatMessageService.getAllMessageOfRoom(roomID);
        return ApiResponse.<GetAllMessageOfRoomResponse>builder()
                .code(200)
                .message("Get messages successfully")
                .result(result)
                .build();
    }


}
